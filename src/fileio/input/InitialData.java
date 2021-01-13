package fileio.input;

import java.util.ArrayList;

public final class InitialData {
    private ArrayList<ConsumerData> consumers;
    private ArrayList<DistributorData> distributors;
    private ArrayList<ProducerData> producers;

    public ArrayList<ConsumerData> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<ConsumerData> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<DistributorData> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorData> distributors) {
        this.distributors = distributors;
    }

    public ArrayList<ProducerData> getProducers() {
        return producers;
    }

    public void setProducers(final ArrayList<ProducerData> producers) {
        this.producers = producers;
    }
}
