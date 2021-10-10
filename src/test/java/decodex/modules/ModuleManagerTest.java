package decodex.modules;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import decodex.data.exception.UnknownModuleException;
import decodex.modules.base64.Base64Encoder;
import org.junit.jupiter.api.Test;

public class ModuleManagerTest {

    @Test
    public void selectModule_base64encode_success() {
        String moduleName = "base64encode";
        ModuleManager moduleManager = new ModuleManager();
        try {
            BaseModule module = moduleManager.selectModule(moduleName);
            assertTrue(module instanceof Base64Encoder);
        } catch (UnknownModuleException e) {
            fail();
        }
    }

    @Test
    public void selectModule_unknownModuleName_unknownModuleExceptionThrown() {
        String moduleName = "unknownModule";
        ModuleManager moduleManager = new ModuleManager();
        assertThrows(UnknownModuleException.class, () -> moduleManager.selectModule(moduleName));
    }

    @Test
    public void selectModule_blankModuleName_unknownModuleExceptionThrown() {
        String moduleName = "";
        ModuleManager moduleManager = new ModuleManager();
        assertThrows(UnknownModuleException.class, () -> moduleManager.selectModule(moduleName));
    }
}
