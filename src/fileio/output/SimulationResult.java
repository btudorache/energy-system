package fileio.output;

import models.Consumer;
import models.Distributor;
import models.Producer;

import java.util.ArrayList;

/**
 * Class holding the simulation results to print to output.
 * All other classes in output package are only used for json writing.
 */
public final class SimulationResult {
    private ArrayList<ConsumerResults> consumers;
    private ArrayList<DistributorResults> distributors;
    private ArrayList<ProducerResults> energyProducers;

    public SimulationResult(final ArrayList<Consumer> consumers,
                            final ArrayList<Distributor> distributors,
                            final ArrayList<Producer> producers) {
        this.consumers = new ArrayList<>();
        for (Consumer consumer : consumers) {
            this.consumers.add(new ConsumerResults(consumer.getId(),
                    consumer.isBankrupt(),
                    consumer.getBudget()));
        }

        this.distributors = new ArrayList<>();
        for (Distributor distributor : distributors) {
            this.distributors.add(new DistributorResults(distributor.getId(),
                    distributor.getEnergyNeeded(),
                    distributor.getContractPrice(),
                    distributor.getBudget(),
                    distributor.getStrategyType(),
                    distributor.isBankrupt(),
                    distributor.getContracts()));
        }

        this.energyProducers = new ArrayList<>();
        for (Producer producer : producers) {
            this.energyProducers.add(new ProducerResults(producer.getId(),
                    producer.getMaxDistributors(),
                    producer.getPriceKW(),
                    producer.getEnergyType().getLabel(),
                    producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats()));
        }
    }

    public ArrayList<ConsumerResults> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<ConsumerResults> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<DistributorResults> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorResults> distributors) {
        this.distributors = distributors;
    }

    public ArrayList<ProducerResults> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(final ArrayList<ProducerResults> energyProducers) {
        this.energyProducers = energyProducers;
    }
}
