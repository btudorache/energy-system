package fileio.input;

import java.util.ArrayList;

/**
 * Class used to model the input data.
 * All classes in the input package are only used for json reading.
 */
public final class InputData {
    private int numberOfTurns;
    private InitialData initialData;
    private ArrayList<MonthlyUpdateData> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public ArrayList<MonthlyUpdateData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final ArrayList<MonthlyUpdateData> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
