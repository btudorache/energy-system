package fileio.input;

public final class ProducerData {
    private int id;
    private String energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
