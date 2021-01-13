package strategies;

import models.Distributor;

public final class EnergyChoiceStrategyFactory {
    private EnergyChoiceStrategyFactory() { }

    /**
     *
     * @param strategyType
     * @param distributor
     * @return
     */
    public static EnergyChoiceStrategy createStrategy(final EnergyChoiceStrategyType strategyType,
                                                      final Distributor distributor) {
        if (strategyType.equals(EnergyChoiceStrategyType.GREEN)) {
            return new GreenEnergyChoiceStrategy(distributor);
        } else if (strategyType.equals(EnergyChoiceStrategyType.PRICE)) {
            return new PriceEnergyChoiceStrategy(distributor);
        } else if (strategyType.equals(EnergyChoiceStrategyType.QUANTITY)) {
            return new QuantityEnergyChoiceStrategy(distributor);
        } else {
            return null;
        }
    }
}
