package decodex.modules.hex;

import decodex.data.Data;
import decodex.modules.BaseModule;

/**
 * The HexEncoder class converts the input string to hexadecimal bytes.
 */
public class HexEncoder extends BaseModule {

    public static final String MODULE_NAME = "hexencode";
    public static final String MODULE_DESCRIPTION = "Converts the input string to hexadecimal bytes";

    public HexEncoder() {
        super(MODULE_NAME, MODULE_DESCRIPTION);
    }

    /**
     * Encodes the given Data object to Hexadecimal.
     *
     * @param data Data object to be encoded.
     * @return new Data object representing the encoded input.
     */
    @Override
    public Data run(Data data) {
        StringBuilder encodedStringBuilder = new StringBuilder();

        for (byte b : data.getRawBytes()) {
            encodedStringBuilder.append(String.format("%02x", b));
        }

        String encodedString = encodedStringBuilder.toString().trim();

        assert !encodedString.isEmpty() : "encodedString is not supposed to be empty.";

        return new Data(encodedString);
    }

}
