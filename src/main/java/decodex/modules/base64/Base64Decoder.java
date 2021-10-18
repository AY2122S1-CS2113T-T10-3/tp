package decodex.modules.base64;

import decodex.data.Data;
import decodex.data.exception.ModuleException;
import decodex.modules.Module;
import decodex.ui.messages.ErrorMessages;
import java.util.Base64;

/**
 * Base64Encoder serves to carry out the Base64 decoding operations.
 */
public class Base64Decoder extends Module {

    private final Base64.Decoder base64Decoder;

    public static final String MODULE_NAME = "base64decode";
    public static final String MODULE_DESCRIPTION = "Decodes the data using Base64 format.";

    public Base64Decoder() {
        super(MODULE_NAME, MODULE_DESCRIPTION);
        base64Decoder = Base64.getDecoder();
    }

    /**
     * Performs Base64 Decoding on the provided Data object.
     *
     * @param data The Data object of the encoded data.
     * @return The Data object of the Base64-decoded data.
     */
    @Override
    public Data run(Data data) throws ModuleException {
        try {
            Data decodedData;
            byte[] originalBytes = data.getRawBytes();
            byte[] base64DecodedBytes = base64Decoder.decode(originalBytes);
            decodedData = new Data(base64DecodedBytes);
            return decodedData;
        } catch (IllegalArgumentException err) {
            throw new ModuleException(ErrorMessages.BASE64_DECODING_FAILED_MESSAGE);
        }
    }
}
