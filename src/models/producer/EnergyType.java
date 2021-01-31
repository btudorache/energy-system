package models.producer;

/**
 * Types of energy produced by EnergyProducers
 */
public enum EnergyType {
    WIND("WIND", true),
    SOLAR("SOLAR", true),
    HYDRO("HYDRO", true),
    COAL("COAL", false),
    NUCLEAR("NUCLEAR", false);

    private final String label;

    private final boolean renewable;

    EnergyType(final String label, final boolean renewable) {
        this.label = label;
        this.renewable = renewable;
    }

    /**
     *
     * @param type
     * @return
     */
    public static EnergyType getEnergyType(final String type) {
        return switch (type) {
            case "WIND" -> EnergyType.WIND;
            case "SOLAR" -> EnergyType.SOLAR;
            case "HYDRO" -> EnergyType.HYDRO;
            case "COAL" -> EnergyType.COAL;
            case "NUCLEAR" -> EnergyType.NUCLEAR;
            default -> null;
        };
    }

    public String getLabel() {
        return label;
    }

    public boolean isRenewable() {
        return renewable;
    }
}
