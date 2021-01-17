import fileio.JsonFileIO;
import fileio.output.SimulationResult;

public final class Main {
    private Main() { }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        JsonFileIO fileIO = new JsonFileIO(args[0], args[1]);
        Simulation simulation = new Simulation(fileIO.getData());
        SimulationResult results = simulation.runSimulation();
        fileIO.writeData(results);
    }
}
