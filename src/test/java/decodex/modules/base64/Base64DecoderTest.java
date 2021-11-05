package decodex.modules.base64;

import decodex.data.Data;
import decodex.data.exception.ModuleException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Base64DecoderTest {

    @Test
    void run_encodedString_base64DecodedData() throws ModuleException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String encodedString = "dGVzdA==";
        String expectedString = "test";

        Data encodedData = new Data(encodedString);
        Data decodedData = base64Decoder.run(encodedData);
        assertEquals(expectedString, decodedData.toString());
    }

    @Test
    void run_emptyString_emptyDecodedData() throws ModuleException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String encodedString = "";
        String expectedString = "";

        Data encodedData = new Data(encodedString);
        Data decodedData = base64Decoder.run(encodedData);
        assertEquals(expectedString, decodedData.toString());
    }

    @Test
    void run_nullByteBase64EncodedInput_expectNullByte() throws ModuleException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String encodedString = "AA==";
        String expectedString = "\00";

        Data encodedData = new Data(encodedString);
        Data decodedData = base64Decoder.run(encodedData);
        assertEquals(expectedString, decodedData.toString());
    }

    @Test
    void run_stringWithOneSpace_throwsModuleException() {
        Base64Decoder base64Decoder = new Base64Decoder();
        String encodedString = " ";

        Data encodedData = new Data(encodedString);
        assertThrows(ModuleException.class, () -> base64Decoder.run(encodedData));
    }

    @Test
    void run_nullByteString_throwsModuleException() {
        Base64Decoder base64Decoder = new Base64Decoder();
        String encodedString = "\00";

        Data encodedData = new Data(encodedString);
        assertThrows(ModuleException.class, () -> base64Decoder.run(encodedData));
    }
}
