package decodex.modules.binary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import decodex.data.Data;
import decodex.data.exception.ModuleException;
import decodex.modules.Module;
import decodex.ui.messages.ErrorMessages;

// @@author arraysius
/**
 * The BinaryDecoder class handles the binary decoding operations.
 */
public class BinaryDecoder extends Module {

    public static final String MODULE_NAME = "bindecode";
    public static final String MODULE_DESCRIPTION = "Decodes the data using binary format.";

    private static final int BINARY_RADIX = 2;
    private static final String VALID_BINARY_REGEX = "^([01]{8})*$";
    private static final int BYTE_LENGTH = 8;

    public BinaryDecoder() {
        super(MODULE_NAME, MODULE_DESCRIPTION);
    }

    /**
     * Performs binary decoding on the provided Data object.
     *
     * @param data The Data to be decoded.
     * @return A Data object containing the binary decoded data.
     * @throws ModuleException If the Data is not in binary format.
     */
    @Override
    public Data run(Data data) throws ModuleException {
        String binString = data.toString();

        if (!isValidBinary(binString)) {
            throw new ModuleException(ErrorMessages.BINARY_DECODING_FAILED_MESSAGE);
        }

        String[] binaryTokens = splitBinaryString(binString);

        byte[] newBytes = new byte[binaryTokens.length];
        for (int i = 0; i < binaryTokens.length; i++) {
            newBytes[i] = Byte.parseByte(binaryTokens[i], BINARY_RADIX);
        }

        return new Data(newBytes);
    }

    /**
     * Checks if the provided binary string is in valid format.
     *
     * @param binString Binary string to check if it is valid.
     * @return True if provided binary string is valid.
     */
    private boolean isValidBinary(String binString) {
        Pattern validBinaryPattern = Pattern.compile(VALID_BINARY_REGEX);
        Matcher validBinaryMatcher = validBinaryPattern.matcher(binString);
        return validBinaryMatcher.find();
    }

    /**
     * Splits the binary string into a String array of 8 characters each.
     *
     * @param binString Binary string to be split.
     * @return A String array of 8 character binary strings.
     */
    private String[] splitBinaryString(String binString) {
        assert binString.length() % BYTE_LENGTH == 0 : "Binary string should be valid";

        String[] binaryTokens = new String[binString.length() / BYTE_LENGTH];

        for (int i = 0; i < binString.length(); i += BYTE_LENGTH) {
            binaryTokens[i / BYTE_LENGTH] = binString.substring(i, i + BYTE_LENGTH);
        }

        return binaryTokens;
    }
}
