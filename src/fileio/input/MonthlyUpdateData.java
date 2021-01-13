package fileio.input;

import java.util.ArrayList;

public final class MonthlyUpdateData {
    private ArrayList<ConsumerData> newConsumers;
    private ArrayList<DistributorChangeData> distributorChanges;
    private ArrayList<ProducerChangeData> producerChanges;

    public ArrayList<ConsumerData> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final ArrayList<ConsumerData> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public ArrayList<DistributorChangeData> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final ArrayList<DistributorChangeData> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public ArrayList<ProducerChangeData> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(final ArrayList<ProducerChangeData> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
