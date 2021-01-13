package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    private final String label;

    EnergyChoiceStrategyType(final String label) {
        this.label = label;
    }

    /**
     *
     * @param strategy
     * @return
     */
    public static EnergyChoiceStrategyType getStrategyType(final String strategy) {
        return switch (strategy) {
            case "GREEN" -> EnergyChoiceStrategyType.GREEN;
            case "PRICE" -> EnergyChoiceStrategyType.PRICE;
            case "QUANTITY" -> EnergyChoiceStrategyType.QUANTITY;
            default -> null;
        };
    }

    public String getLabel() {
        return label;
    }
}
