package strategies;

import models.Distributor;
import models.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class QuantityEnergyChoiceStrategy extends EnergyChoiceStrategy {
    public QuantityEnergyChoiceStrategy(final Distributor distributor) {
        super(distributor);
    }

    @Override
    public ArrayList<Producer> chooseProducers(final ArrayList<Producer> producers) {
        ArrayList<Producer> producerList = new ArrayList<>(producers);
        producerList.sort(Collections.reverseOrder(
                Comparator.comparing(Producer::getEnergyPerDistributor))
            .thenComparing(Producer::getId));

        return filterFirstProducers(producerList);
    }
}
