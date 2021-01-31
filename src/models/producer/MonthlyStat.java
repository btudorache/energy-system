package models.producer;

import models.Distributor;

import java.util.ArrayList;
import java.util.Collections;

public final class MonthlyStat {
    private int month;
    private ArrayList<Integer> distributorsIds;

    public MonthlyStat(final int month, final ArrayList<Distributor> distributors) {
        this.month = month;

        this.distributorsIds = new ArrayList<>();
        for (Distributor distributor : distributors) {
            this.distributorsIds.add(distributor.getId());
        }
        Collections.sort(this.distributorsIds);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public ArrayList<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final ArrayList<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
