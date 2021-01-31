package strategies;

import models.Distributor;
import models.producer.Producer;

import java.util.ArrayList;

/**
 * Abstract Choice Strategy used for choosing producers
 */
public abstract class EnergyChoiceStrategy {
    private final Distributor distributor;

    /**
     * Strategy Constructor
     * @param distributor distributor that uses strategy
     */
    public EnergyChoiceStrategy(final Distributor distributor) {
        this.distributor = distributor;
    }

    /**
     * Producer choice strategy method that needs to be implemented
     * @param producers list of producers to choose from
     * @return list of sorted producers by strategy
     */
    public abstract ArrayList<Producer> chooseProducers(ArrayList<Producer> producers);

    /**
     * Selects the first N producers needed to generate energy
     * @param sortedProducers list of sorted producers by specific strategy
     * @return list of producers chosen
     */
    public ArrayList<Producer> filterFirstProducers(final ArrayList<Producer> sortedProducers) {
        ArrayList<Producer> chosenProducers = new ArrayList<>();
        int distributorTargetQuantity = this.distributor.getEnergyNeeded();
        for (Producer producer : sortedProducers) {
            if (producer.getDistributors().size() < producer.getMaxDistributors()) {
                chosenProducers.add(producer);
                distributorTargetQuantity -= producer.getEnergyPerDistributor();
                producer.addDistributor(this.distributor);
            }

            if (distributorTargetQuantity <= 0) {
                break;
            }
        }

        return chosenProducers;
    }
}
