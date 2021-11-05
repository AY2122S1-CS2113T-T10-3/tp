package decodex.modules.rot;

import decodex.data.Data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RotEncoderTest {

    @Test
    void run_smallPositiveOffset_rotatedData() {
        Data testData = new Data("abc");
        Data expectedData = new Data("bcd");

        RotEncoder rotEncoder = new RotEncoder(1);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_bigPositiveOffset_rotatedAlphabets() {
        Data testData = new Data("xyz");
        Data expectedData = new Data("qrs");

        RotEncoder rotEncoder = new RotEncoder(45);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_smallNegativeOffset_rotatedAlphabets() {
        Data testData = new Data("abc");
        Data expectedData = new Data("zab");

        RotEncoder rotEncoder = new RotEncoder(-1);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_bigNegativeOffset_rotatedAlphabets() {
        Data testData = new Data("xyz");
        Data expectedData = new Data("efg");

        RotEncoder rotEncoder = new RotEncoder(-45);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_onlyNonAlphabets_noChange() {
        Data testData = new Data("123==");
        Data expectedData = new Data("123==");

        RotEncoder rotEncoder = new RotEncoder(1);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_emptyData_noChange() {
        Data testData = new Data("");
        Data expectedData = new Data("");

        RotEncoder rotEncoder = new RotEncoder(1);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }

    @Test
    void run_nullBytes_noChange() {
        Data testData = new Data("\00");
        Data expectedData = new Data("\00");

        RotEncoder rotEncoder = new RotEncoder(1);
        Data resultData = rotEncoder.run(testData);

        assertEquals(resultData.toString(), expectedData.toString());
    }
}
