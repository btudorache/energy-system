package models;

import entities.EnergyType;
import fileio.input.ProducerData;
import fileio.output.data.MonthlyStat;

import java.util.ArrayList;
import java.util.Observable;

public final class Producer extends Observable {
    private int id;
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    private ArrayList<Distributor> distributors;
    private ArrayList<MonthlyStat> monthlyStats;

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
     *
     * @param distributor
     */
    public void addDistributor(final Distributor distributor) {
        this.distributors.add(distributor);
    }

    /**
     *
     * @param distributor
     */
    public void removeDistributor(final Distributor distributor) {
        this.distributors.remove(distributor);
    }

    /**
     *
     * @param status
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
     *
     * @param energyPerDistributor
     */
    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        setChanged();
        notifyObservers();
    }
}
