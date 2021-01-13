package fileio.output;

import models.Contract;
import strategies.EnergyChoiceStrategyType;

import java.util.ArrayList;

public final class DistributorResults {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private String producerStrategy;
    private boolean isBankrupt;
    private ArrayList<ContractResult> contracts;

    public DistributorResults(final int id,
                              final int energyNeededKW,
                              final int contractCost,
                              final int budget,
                              final EnergyChoiceStrategyType producerStrategy,
                              final boolean isBankrupt,
                              final ArrayList<Contract> contracts) {
        this.id = id;
        this.budget = budget;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.isBankrupt = isBankrupt;
        this.producerStrategy = producerStrategy.getLabel();

        this.contracts = new ArrayList<>();
        for (Contract contract : contracts) {
            this.contracts.add(new ContractResult(contract.getContractor().getId(),
                                                  contract.getMonthlyPay(),
                                                  contract.getDuration()));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(final int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(final int contractCost) {
        this.contractCost = contractCost;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(final String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public ArrayList<ContractResult> getContracts() {
        return contracts;
    }

    public void setContracts(final ArrayList<ContractResult> contracts) {
        this.contracts = contracts;
    }
}
