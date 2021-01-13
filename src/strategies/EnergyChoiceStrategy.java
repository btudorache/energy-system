package strategies;

import models.Distributor;
import models.Producer;

import java.util.ArrayList;

/**
 *
 */
public abstract class EnergyChoiceStrategy {
    private Distributor distributor;

    /**
     *
     * @param distributor
     */
    public EnergyChoiceStrategy(final Distributor distributor) {
        this.distributor = distributor;
    }

    /**
     *
     * @param producers
     * @return
     */
    public abstract ArrayList<Producer> chooseProducers(ArrayList<Producer> producers);

    /**
     *
     * @param sortedProducers
     * @return
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
