package models;

import fileio.input.ConsumerData;

/**
 * Abstract class used to model a consumer
 */
public final class Consumer extends Entity {
    private static final double PENALTY_FACTOR = 1.2;

    private boolean continuingContract;
    private Distributor distributorOwed;
    private boolean owesPay;
    private int payOwed;

    private final int monthlyIncome;

    private boolean holdingContract;
    private Contract contract;

    /**
     * Consumer constructor. Uses ConsumerData object from input to build Consumer.
     * @param consumerData input data used for Consumer
     */
    public Consumer(final ConsumerData consumerData) {
           super(consumerData.getId(), consumerData.getInitialBudget());
           this.monthlyIncome = consumerData.getMonthlyIncome();

           this.continuingContract = false;
           this.holdingContract = false;
           this.contract = null;

           this.distributorOwed = null;
           this.owesPay = false;
           this.payOwed = -1;
    }

    public void setContinuingContract(final boolean continuingContract) {
        this.continuingContract = continuingContract;
    }

    public boolean isHoldingContract() {
        return holdingContract;
    }

    public Contract getContract() {
        return contract;
    }

    public void setHoldingContract(final boolean holdingContract) {
        this.holdingContract = holdingContract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public int getMonthlyIncome() {
        return this.monthlyIncome;
    }

    /**
     * Method used to earn monthly income
     */
    public void earnMonthlyIncome() {
        this.setBudget(this.getBudget() + this.getMonthlyIncome());
    }

    /**
     * Method used to pay contract price
     */
    public void payContract() {
        // if consumer owes pay
        if (this.owesPay) {
            // if consumer is continuing contract
            if (this.continuingContract) {
                int valueToPay = (int) (Math.round(Math.floor(PENALTY_FACTOR * this.payOwed)));
                if (this.getBudget() - valueToPay < 0) {
                    this.setBankrupt(true);
                } else {
                    this.setBudget(this.getBudget() - valueToPay);
                    this.distributorOwed.getContractPay(valueToPay);
                    removeOwesPay();
                }
                // if consumer is not continuing contract
            } else {
                int valueToPay = (int) (Math.round(Math.floor(PENALTY_FACTOR * this.payOwed))
                        + this.getContract().getMonthlyPay());
                if (this.getBudget() - valueToPay < 0) {
                    this.setBankrupt(true);
                } else {
                    this.setBudget(this.getBudget() - valueToPay);

                    this.distributorOwed.getContractPay(
                            (int) (Math.round(Math.floor(PENALTY_FACTOR * this.payOwed))));
                    this.getContract().getDistributorContracted()
                            .getContractPay(this.getContract().getMonthlyPay());
                    removeOwesPay();
                }
            }
        // if consumer doesn't owe pay
        } else {
            if (this.getBudget() - this.getContract().getMonthlyPay() < 0) {
                this.setOwesPay(this.getContract().getMonthlyPay(),
                        this.getContract().getDistributorContracted());
            } else {
                this.setBudget(this.getBudget() - this.getContract().getMonthlyPay());
                this.getContract().getDistributorContracted()
                        .getContractPay(this.getContract().getMonthlyPay());
            }
        }
        this.getContract().updateDuration();
    }

    /**
     * Method used to set a new contract
     * @param newContract contract to be set
     */
    public void setNewContract(final Contract newContract) {
        this.setHoldingContract(true);
        this.setContract(newContract);
    }

    /**
     * Method used to remove old contract
     */
    public void removeContract() {
        this.setHoldingContract(false);
        this.setContract(null);
    }

    /**
     * Method used to set the pay owed
     * @param payOwedValue pay owed
     * @param distributorOwedValue distributor owed
     */
    public void setOwesPay(final int payOwedValue, final Distributor distributorOwedValue) {
        this.owesPay = true;
        this.payOwed = payOwedValue;
        this.distributorOwed = distributorOwedValue;
    }

    /**
     * Method used to remove the pay owed
     */
    public void removeOwesPay() {
        this.owesPay = false;
        this.payOwed = -1;
        this.distributorOwed = null;
    }
}
