package models;

/**
 * class used the model and hold contract information
 */
public final class Contract {
    private final Distributor distributorContracted;
    private final Consumer contractor;
    private final int monthlyPay;
    private int duration;

    public Contract(final Distributor distributorContracted,
                    final Consumer contractor,
                    final int monthlyPay,
                    final int duration) {
        this.distributorContracted = distributorContracted;
        this.contractor = contractor;
        this.monthlyPay = monthlyPay;
        this.duration = duration;
    }

    public Consumer getContractor() {
        return contractor;
    }

    public Distributor getDistributorContracted() {
        return distributorContracted;
    }

    public int getMonthlyPay() {
        return monthlyPay;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * decrements the contract duration at the end of the month
     */
    public void updateDuration() {
        this.duration--;
    }
}
