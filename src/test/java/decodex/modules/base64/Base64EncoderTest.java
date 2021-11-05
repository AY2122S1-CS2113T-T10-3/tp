package decodex.modules.base64;

import decodex.data.Data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Base64EncoderTest {

    @Test
    void run_originalString_base64EncodedData() {
        Base64Encoder base64Encoder = new Base64Encoder();
        String testString = "test";
        String expectedString = "dGVzdA==";

        Data originalData = new Data(testString);
        Data encodedData = base64Encoder.run(originalData);
        assertEquals(expectedString, encodedData.toString());
    }
}
