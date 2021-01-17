package strategies;

import models.Distributor;

public final class EnergyChoiceStrategyFactory {
    private static EnergyChoiceStrategyFactory instance;

    /**
     * Gets the instance of the singleton
     * @return singleton EnergyChoiceStrategyFactory instance
     */
    public static EnergyChoiceStrategyFactory getInstance() {
        if (instance == null) {
            instance = new EnergyChoiceStrategyFactory();
        }
        return instance;
    }


    /**
     * EnergyChoiceStrategy factory method
     * @param strategyType strategy type wanted
     * @param distributor distributor that uses strategy
     * @return specific strategy type
     */
    public EnergyChoiceStrategy createStrategy(final EnergyChoiceStrategyType strategyType,
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
