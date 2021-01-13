package models;

import fileio.input.DistributorData;
import strategies.EnergyChoiceStrategy;
import strategies.EnergyChoiceStrategyFactory;
import strategies.EnergyChoiceStrategyType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Distributor class used to model distributors
 */
public final class Distributor extends Entity implements Observer {
    private static final double PROFIT_PERCENTAGE = 0.2;
    private static final int PRODUCTION_COST_CONSTANT = 10;

    private final int energyNeeded;
    private final EnergyChoiceStrategyType strategyType;
    private final EnergyChoiceStrategy strategy;
    private ArrayList<Producer> contractedProducers;

    private int infrastructureCost;
    private int productionCost;
    private boolean updateCost;

    private final int contractLength;
    private ArrayList<Contract> contracts;
    private int contractPrice;

    /**
     * Distributor constructor. Uses DistributorData to construct object
     * @param distributorData object holding distributor data from input
     */
    public Distributor(final DistributorData distributorData) {
        super(distributorData.getId(), distributorData.getInitialBudget());
        this.energyNeeded = distributorData.getEnergyNeededKW();
        this.strategyType = EnergyChoiceStrategyType
                .getStrategyType(distributorData.getProducerStrategy());
        this.strategy = EnergyChoiceStrategyFactory.createStrategy(this.strategyType, this);
        this.contractedProducers = null;

        this.infrastructureCost = distributorData.getInitialInfrastructureCost();
        this.productionCost = -1;
        this.updateCost = false;

        this.contractLength = distributorData.getContractLength();
        this.contracts = new ArrayList<>();
        this.calculateNewContractPrice();
    }

    public EnergyChoiceStrategyType getStrategyType() {
        return strategyType;
    }

    public boolean isUpdateCost() {
        return updateCost;
    }

    public void setUpdateCost(final boolean updateCost) {
        this.updateCost = updateCost;
    }

    /**
     * Updates the producers list if there is a monthly change
     * @param producerList producer list to search from
     */
    public void updateProducers(final ArrayList<Producer> producerList) {
        for (Producer producer : contractedProducers) {
            producer.removeDistributor(this);
            producer.deleteObserver(this);
        }

        findProducers(producerList);
        calculateProductionCost();

        this.setUpdateCost(false);
    }

    public int getEnergyNeeded() {
        return energyNeeded;
    }

    /**
     * Finds new producers
     * @param producerList producer list to search from
     */
    public void findProducers(final ArrayList<Producer> producerList) {
        this.contractedProducers = this.strategy.chooseProducers(producerList);
        for (Producer producer : this.contractedProducers) {
            producer.addObserver(this);
        }
    }

    /**
     *
     */
    public void calculateProductionCost() {
        int cost = 0;
        for (Producer producer : this.contractedProducers) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        cost /= PRODUCTION_COST_CONSTANT;
        this.productionCost = cost;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * gets total production costs a distributor has to pay at the end of the month
     * @return total production cost
     */
    public int getTotalProductionCost() {
        if (this.contracts.size() == 0) {
            return 0;
        } else {
            return this.contracts.size() * this.productionCost;
        }
    }

    private int getProfit() {
        return (int) Math.round(Math.floor(PROFIT_PERCENTAGE * this.productionCost));
    }

    public int getContractPrice() {
        return contractPrice;
    }

    /**
     * calculates the contract price
     */
    public void calculateNewContractPrice() {
        if (this.contracts.size() == 0) {
            this.contractPrice = this.getProfit() + this.productionCost + this.infrastructureCost;
        } else {
            this.contractPrice = (int)
                    Math.round(Math.floor((double) this.infrastructureCost / this.contracts.size())
                    + this.productionCost + this.getProfit());
        }
    }

    public int getContractLength() {
        return contractLength;
    }

    /**
     * adds contract to contract list
     * @param contract contract to be added
     */
    public void addContract(final Contract contract) {
        this.contracts.add(contract);
    }

    /**
     * removes contract from contract list
     * @param contract contract to be removed
     */
    public void removeContract(final Contract contract) {
        this.contracts.remove(contract);
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * adds payment from the contract
     * @param monthlyPay sum to be added to budget
     */
    public void getContractPay(final int monthlyPay) {
        this.setBudget(this.getBudget() + monthlyPay);
    }

    /**
     * pays the distributor costs at the end of the month
     */
    public void payDistributionCost() {
        this.setBudget(this.getBudget() - this.infrastructureCost - this.getTotalProductionCost());
        if (this.getBudget() < 0) {
            this.setBankrupt(true);
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        setUpdateCost(true);
    }
}
