package strategies;

import models.Distributor;
import models.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class GreenEnergyChoiceStrategy extends EnergyChoiceStrategy {
    public GreenEnergyChoiceStrategy(final Distributor distributor) {
        super(distributor);
    }

    @Override
    public ArrayList<Producer> chooseProducers(final ArrayList<Producer> producers) {
        ArrayList<Producer> renewableEnergyProducers = producers.stream()
                .filter(producer -> producer.getEnergyType().isRenewable())
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Producer> notRenewableEnergyProducers = new ArrayList<>(producers);
        notRenewableEnergyProducers.removeAll(renewableEnergyProducers);

        Comparator<Producer> greenEnergyComparator = Comparator.comparing(Producer::getPriceKW)
                .thenComparing(Collections.reverseOrder(
                        Comparator.comparing(Producer::getEnergyPerDistributor)))
                .thenComparing(Producer::getId);

        renewableEnergyProducers.sort(greenEnergyComparator);
        notRenewableEnergyProducers.sort(greenEnergyComparator);
        renewableEnergyProducers.addAll(notRenewableEnergyProducers);

        return filterFirstProducers(renewableEnergyProducers);
    }
}
