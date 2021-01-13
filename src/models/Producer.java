package models;

import entities.EnergyType;
import fileio.input.ProducerData;
import fileio.output.data.MonthlyStat;

import java.util.ArrayList;
import java.util.Observable;

public final class Producer extends Observable {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    private final ArrayList<Distributor> distributors;
    private final ArrayList<MonthlyStat> monthlyStats;

    public Producer(final ProducerData producerData) {
        this.id = producerData.getId();
        this.energyType = EnergyType.getEnergyType(producerData.getEnergyType());
        this.maxDistributors = producerData.getMaxDistributors();
        this.priceKW = producerData.getPriceKW();
        this.energyPerDistributor = producerData.getEnergyPerDistributor();

        this.distributors = new ArrayList<>();
        this.monthlyStats = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    /**
     * Adds a new distributor to the list
     * @param distributor distributor to be added
     */
    public void addDistributor(final Distributor distributor) {
        this.distributors.add(distributor);
    }

    /**
     * removes a distributor from the list
     * @param distributor distributor to be removed
     */
    public void removeDistributor(final Distributor distributor) {
        this.distributors.remove(distributor);
    }

    /**
     * Adds a monthly status for the producer
     * @param status status to be added
     */
    public void addMonthlyStat(final MonthlyStat status) {
        this.monthlyStats.add(status);
    }

    public ArrayList<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
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

    /**
     * Sets the new monthly energy change and notifies the observers for the change
     * @param energyPerDistributor new monthly energy value
     */
    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        setChanged();
        notifyObservers();
    }
}
