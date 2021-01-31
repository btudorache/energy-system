package strategies;

import models.Distributor;
import models.producer.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Price strategy implementation
 */
public final class PriceEnergyChoiceStrategy extends EnergyChoiceStrategy {
    public PriceEnergyChoiceStrategy(final Distributor distributor) {
        super(distributor);
    }

    @Override
    public ArrayList<Producer> chooseProducers(final ArrayList<Producer> producers) {
        ArrayList<Producer> producerList = new ArrayList<>(producers);
        producerList.sort(Comparator.comparing(Producer::getPriceKW)
            .thenComparing(Collections.reverseOrder(
                    Comparator.comparing(Producer::getEnergyPerDistributor)))
            .thenComparing(Producer::getId));

        return filterFirstProducers(producerList);
    }
}
