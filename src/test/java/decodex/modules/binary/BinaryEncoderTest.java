package decodex.modules.binary;

import decodex.data.Data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryEncoderTest {

    BinaryEncoder binaryEncoder = new BinaryEncoder();

    @Test
    public void run_asciiString_binaryString() {
        Data data = new Data("test");
        Data newData = binaryEncoder.run(data);
        assertEquals(newData.toString(), "01110100011001010111001101110100");
    }

    @Test
    public void run_blankString_blankBinaryString() {
        Data data = new Data("");
        Data newData = binaryEncoder.run(data);
        assertEquals(newData.toString(), "");
    }

    @Test
    public void run_nullByteString_success() {
        Data data = new Data("\00");
        Data newData = binaryEncoder.run(data);
        assertEquals(newData.toString(), "00000000");
    }
}
