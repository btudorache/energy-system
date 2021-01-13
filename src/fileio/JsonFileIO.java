package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fileio.input.InputData;
import fileio.output.SimulationResult;

import java.io.File;
import java.io.IOException;

/**
 * Class used to manage the input and the output data
 */
public final class JsonFileIO {
    private final String outputFileName;

    private final InputData data;
    /**
     * ObjectMapper object used to write and read data
     */
    private final ObjectMapper mapper;

    /**
     * Constructor for JsonFileIO
     * @param inputFileName input file to read from
     * @param outputFileName output file to write to
     * @throws IOException exception thrown
     */
    public JsonFileIO(final String inputFileName, final String outputFileName) throws IOException {
        this.outputFileName = outputFileName;

        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // reading data from the input file
        this.data = this.mapper.readValue(new File(inputFileName), InputData.class);
    }

    public InputData getData() {
        return data;
    }

    /**
     * Writes data to output file
     * @param results results to be written
     * @throws IOException exception thrown
     */
    public void writeData(final SimulationResult results) throws IOException {
        this.mapper.writeValue(new File(this.outputFileName), results);
    }
}
