package decodex.modules.hex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import decodex.data.Data;

class HexEncoderTest {

    @Test
    void run_allAsciiChars_expectHexEncoding() {
        String inputString = " !\"#$%&'()*+,-./0123456789:;"
                + "<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        HexEncoder hexEncoder = new HexEncoder();
        Data inputData = new Data(inputString);

        Data result = hexEncoder.run(inputData);
        String expectedString = "202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f40"
                + "4142434445464748494a4b4c4d4e4f505152535455565758595a5b5c5d5e5f606162636465666768"
                + "696a6b6c6d6e6f707172737475767778797a7b7c7d7e";

        assertEquals(expectedString, result.toString());
    }

}
