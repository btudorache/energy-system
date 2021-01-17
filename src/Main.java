import fileio.JsonFileIO;
import fileio.output.SimulationResult;

public final class Main {
    private Main() { }

    /**
     * Main entrypoint of the program
     * @param args command line arguments
     * @throws Exception compile-time exception type
     */
    public static void main(final String[] args) throws Exception {
        JsonFileIO fileIO = new JsonFileIO(args[0], args[1]);
        Simulation simulation = new Simulation(fileIO.getData());
        SimulationResult results = simulation.runSimulation();
        fileIO.writeData(results);
    }
}
