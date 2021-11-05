package decodex.modules.binary;

import decodex.data.Data;
import decodex.data.exception.ModuleException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BinaryDecoderTest {

    BinaryDecoder binaryDecoder = new BinaryDecoder();

    @Test
    public void run_validBinaryString_asciiString() throws ModuleException {
        Data data = new Data("01110100011001010111001101110100");
        Data newData = binaryDecoder.run(data);
        assertEquals(newData.toString(), "test");
    }

    @Test
    public void run_blankString_blankBinaryString() throws ModuleException {
        Data data = new Data("");
        Data newData = binaryDecoder.run(data);
        assertEquals(newData.toString(), "");
    }

    @Test
    public void run_nullByteBinaryString_success() throws ModuleException {
        Data data = new Data("00000000");
        Data newData = binaryDecoder.run(data);
        assertEquals(newData.toString(), "\00");
    }

    @Test
    public void run_binaryStringWithSpaces_moduleExceptionThrown() {
        Data data = new Data("01110100 01100101 01110011 01110100");
        assertThrows(ModuleException.class, () -> binaryDecoder.run(data));
    }

    @Test
    public void run_invalidBinaryString_moduleExceptionThrown() {
        Data data = new Data("1110100");
        assertThrows(ModuleException.class, () -> binaryDecoder.run(data));
    }
}
