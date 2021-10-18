package decodex.data;

import decodex.data.exception.DataManagerException;
import decodex.ui.messages.ErrorMessages;
import java.util.Arrays;

/**
 * The DataManager class handles the management of the current state of the data.
 */
public class DataManager {

    /* Data provided by the user */
    private Data originalData;

    /* Data to perform the encoding/decoding operations on */
    private Data currentData;

    /**
     * Instantiates a new DataManager.
     */
    public DataManager() {
        originalData = null;
        currentData = null;
    }

    /**
     * Returns the original Data object.
     *
     * @return The original Data object.
     * @throws DataManagerException If the original Data object does not exist.
     */
    public Data getOriginalData() throws DataManagerException {
        if (originalData == null) {
            throw new DataManagerException(ErrorMessages.NO_DATA_FOUND);
        }
        return originalData;
    }

    /**
     * Returns the current Data object.
     *
     * @return The current Data object.
     * @throws DataManagerException If the current Data object does not exist.
     */
    public Data getCurrentData() throws DataManagerException {
        if (currentData == null) {
            throw new DataManagerException(ErrorMessages.NO_DATA_FOUND);
        }
        return currentData;
    }

    /**
     * Sets the original Data.
     *
     * @param originalData The original Data object.
     */
    public void setOriginalData(Data originalData) {
        this.originalData = originalData;
        resetToOriginalData();
    }

    /**
     * Sets the current Data.
     *
     * @param currentData The new current Data Object.
     * @throws DataManagerException If the original Data object does not exist.
     */
    public void setCurrentData(Data currentData) throws DataManagerException {
        if (originalData == null) {
            throw new DataManagerException(ErrorMessages.NO_DATA_FOUND);
        }
        this.currentData = currentData;
    }

    /**
     * Reverts the current data to its original data.
     */
    public void resetToOriginalData() {
        currentData = originalData;
        assert Arrays.equals(currentData.getRawBytes(), originalData.getRawBytes()) : "Current data is same as "
                + "original data";
    }
}
