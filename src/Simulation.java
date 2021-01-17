import fileio.input.*;
import fileio.output.SimulationResult;

import fileio.output.data.MonthlyStat;
import models.*;

import java.util.ArrayList;

/**
 * Singleton class used to model the simulation.
 */
public final class Simulation {
    private final ArrayList<Consumer> consumerList;
    private final ArrayList<Distributor> distributorList;
    private final ArrayList<Producer> producerList;
    private int distributorsBankrupt;

    private final ArrayList<MonthlyUpdateData> monthlyUpdates;


    /**
     * Constructor for the Simulation class. Holds all the information necessary.
     * @param data input data used to construct the simulation
     */
    public Simulation(final InputData data) {
        this.consumerList = new ArrayList<>();
        for (ConsumerData consumerData : data.getInitialData().getConsumers()) {
            this.consumerList.add(new Consumer(consumerData));
        }

        this.distributorList = new ArrayList<>();
        for (DistributorData distributorData : data.getInitialData().getDistributors()) {
            this.distributorList.add(new Distributor(distributorData));
        }

        this.producerList = new ArrayList<>();
        for (ProducerData producerData : data.getInitialData().getProducers()) {
            this.producerList.add(new Producer(producerData));
        }

        this.distributorsBankrupt = 0;
        this.monthlyUpdates = data.getMonthlyUpdates();
    }

    /**
     * Updates the monthly changes and updates the new distributor prices
     * @param monthlyUpdateData data used to update the simulation
     */
    private void updateInitialMonthlyChanges(final MonthlyUpdateData monthlyUpdateData) {
        if (!monthlyUpdateData.getNewConsumers().isEmpty()) {
            for (ConsumerData consumerData : monthlyUpdateData.getNewConsumers()) {
                this.consumerList.add(new Consumer(consumerData));
            }
        }

        if (!monthlyUpdateData.getDistributorChanges().isEmpty()) {
            for (DistributorChangeData data : monthlyUpdateData.getDistributorChanges()) {
                Distributor distributorChanged = this.distributorList.get(data.getId());

                distributorChanged.setInfrastructureCost(data.getInfrastructureCost());
            }
        }

        for (Distributor distributor : distributorList) {
            if (!distributor.isBankrupt()) {
                distributor.calculateNewContractPrice();
            }
        }
    }

    /**
     * Updates the producers in the middle of the turn
     * @param monthData data used to update the simulation
     */
    private void updateProducerChanges(final MonthlyUpdateData monthData) {
        if (!monthData.getProducerChanges().isEmpty()) {
            for (ProducerChangeData producerData : monthData.getProducerChanges()) {
                Producer producerChanged = this.producerList.get(producerData.getId());

                producerChanged.setEnergyPerDistributor(producerData.getEnergyPerDistributor());
            }
        }
    }

    /**
     * finds the cheapest contract for a consumer
     * @param consumer consumer to which to add the contract
     */
    private void findCheapestContract(final Consumer consumer) {
        Distributor cheapestDistributor = null;
        for (Distributor distributor : this.distributorList) {
            if (!distributor.isBankrupt()) {
                cheapestDistributor = distributor;
                break;
            }
        }
        if (cheapestDistributor == null) {
            return;
        }

        for (Distributor distributor : this.distributorList) {
            if (distributor.getContractPrice() < cheapestDistributor.getContractPrice()
                    && !distributor.isBankrupt()) {
                cheapestDistributor = distributor;
            }
        }
        // create the new contract
        Contract contract = new Contract(cheapestDistributor,
                                         consumer,
                                         cheapestDistributor.getContractPrice(),
                                         cheapestDistributor.getContractLength());

        // remove old contract
        if (consumer.isHoldingContract()) {
            Distributor distributor = consumer.getContract().getDistributorContracted();

            // check if consumes is continuing the contract with the previous distributor
            consumer.setContinuingContract(distributor.equals(contract.getDistributorContracted()));

            distributor.removeContract(consumer.getContract());
            consumer.removeContract();
        }

        // add the new contract
        cheapestDistributor.addContract(contract);
        consumer.setNewContract(contract);
    }

    /**
     * Adds changes to producers in the first turn
     */
    private void initialUpdateDistributors() {
        for (Distributor distributor : this.distributorList) {
            if (!distributor.isBankrupt()) {
                distributor.findProducers(this.producerList);
                distributor.calculateProductionCost();
                distributor.calculateNewContractPrice();
            }
        }
    }

    /**
     * updates the consumers in a turn
     */
    private void updateConsumers() {
        for (Consumer consumer : this.consumerList) {
            if (!consumer.isBankrupt()) {
                consumer.earnMonthlyIncome();
                if (!consumer.isHoldingContract() || consumer.getContract().getDuration() == 0) {
                    findCheapestContract(consumer);
                }
                consumer.payContract();
            }
        }
    }

    /**
     * pays the distributors in a turn
     */
    private void payDistributorCosts() {
        for (Distributor distributor : this.distributorList) {
            if (!distributor.isBankrupt()) {
                distributor.payDistributionCost();
            }
        }
    }

    /**
     * check if any entity has become bankrupt at the end of the month
     */
    private void checkMonthEnd(final int monthNumber) {
        for (Consumer consumer : consumerList) {
            if (consumer.isBankrupt() && consumer.isHoldingContract()) {
                Distributor oldDistributor = consumer.getContract().getDistributorContracted();
                oldDistributor.removeContract(consumer.getContract());
                consumer.removeOwesPay();
                consumer.removeContract();
            }
        }

        for (Distributor distributor : distributorList) {
            // check for bankruptcy
            if (distributor.isBankrupt() && distributor.getContracts().size() != 0) {
                this.distributorsBankrupt++;
                for (Contract contract : distributor.getContracts()) {
                    contract.getContractor().removeContract();
                }
                distributor.setContracts(new ArrayList<>());
            // update the production cost and producer list if needed
            } else if (!distributor.isBankrupt() && distributor.isUpdateCost()) {
                distributor.updateProducers(this.producerList);
            }
        }

        // adds monthly stats to the producers
        for (Producer producer : producerList) {
            producer.addMonthlyStat(new MonthlyStat(monthNumber, producer.getDistributors()));
        }
    }

    /**
     * runs the simulation logic
     * @return SimulationResult object with the simulation results
     */
    public SimulationResult runSimulation() {
        initialUpdateDistributors();
        updateConsumers();
        payDistributorCosts();

        for (MonthlyUpdateData monthData : this.monthlyUpdates) {
            updateInitialMonthlyChanges(monthData);
            updateConsumers();
            payDistributorCosts();

            updateProducerChanges(monthData);

            checkMonthEnd(this.monthlyUpdates.indexOf(monthData) + 1);

            if (this.distributorsBankrupt == this.distributorList.size()) {
                break;
            }
        }
        return new SimulationResult(this.consumerList, this.distributorList, this.producerList);
    }
}
