package decodex.data;

import java.util.Arrays;

import decodex.data.exception.DataManagerException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataManagerTest {

    @Test
    public void getOriginalData_noData_expectException() {
        DataManager testManager = new DataManager();
        assertThrows(DataManagerException.class, () -> testManager.getOriginalData());
    }

    @Test
    public void getCurrentData_noData_expectException() {
        DataManager testManager = new DataManager();
        assertThrows(DataManagerException.class, () -> testManager.getCurrentData());
    }

    @Test
    public void getCurrentData_withOriginalData_originalData() throws DataManagerException {
        DataManager testManager = new DataManager();
        Data originalData = new Data("hi");
        testManager.setOriginalData(originalData);
        assertTrue(Arrays.equals(testManager.getCurrentData().getRawBytes(), originalData.getRawBytes()));
    }

    @Test
    public void setOriginalData_withOriginalData_originalData() throws DataManagerException {
        DataManager testManager = new DataManager();
        Data originalData = new Data("hi");
        testManager.setOriginalData(originalData);
        assertTrue(Arrays.equals(testManager.getOriginalData().getRawBytes(), originalData.getRawBytes()));
    }

    @Test
    public void setCurrentData_noData_expectException() {
        DataManager testManager = new DataManager();
        Data newData = new Data("bye");
        assertThrows(DataManagerException.class, () -> testManager.setCurrentData(newData));
    }

    @Test
    public void setCurrentData_newData_newData() throws DataManagerException {
        DataManager testManager = new DataManager();
        Data originalData = new Data("hi");
        testManager.setOriginalData(originalData);
        Data newData = new Data("bye");
        testManager.setCurrentData(newData);
        assertTrue(Arrays.equals(testManager.getCurrentData().getRawBytes(), newData.getRawBytes()));
    }

    @Test
    public void resetToOriginalData_noUpdates_originalData() throws DataManagerException {
        DataManager testManager = new DataManager();
        Data originalData = new Data("hi");
        testManager.setOriginalData(originalData);
        testManager.resetToOriginalData();
        assertTrue(Arrays.equals(testManager.getCurrentData().getRawBytes(), originalData.getRawBytes()));
    }

    @Test
    public void resetToOriginalData_newData_originalData() throws DataManagerException {
        DataManager testManager = new DataManager();
        Data originalData = new Data("hi");
        testManager.setOriginalData(originalData);
        Data newData = new Data("bye");
        testManager.setCurrentData(newData);
        testManager.resetToOriginalData();
        assertTrue(Arrays.equals(testManager.getCurrentData().getRawBytes(), originalData.getRawBytes()));
    }
}
