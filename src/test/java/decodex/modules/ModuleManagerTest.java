package decodex.modules;

import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.modules.base64.Base64Encoder;
import decodex.modules.rot.RotEncoder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleManagerTest {

    @Test
    public void selectModule_base64encode_success() throws ModuleManagerException, ModuleException {
        String moduleName = "base64encode";
        String[] parameters = {};
        ModuleManager moduleManager = new ModuleManager();

        Module module = moduleManager.selectModule(moduleName, parameters);
        assertTrue(module instanceof Base64Encoder);
    }

    @Test
    public void selectModule_rotEncodeWithValidParameter_success() throws ModuleManagerException, ModuleException {
        String moduleName = "rotencode";
        String[] parameters = {"13"};
        ModuleManager moduleManager = new ModuleManager();

        Module module = moduleManager.selectModule(moduleName, parameters);
        assertTrue(module instanceof RotEncoder);
    }

    @Test
    public void selectModule_unknownModuleName_unknownModuleExceptionThrown() {
        String moduleName = "unknownModule";
        String[] parameters = {};
        ModuleManager moduleManager = new ModuleManager();
        assertThrows(ModuleManagerException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }

    @Test
    public void selectModule_blankModuleName_unknownModuleExceptionThrown() {
        String moduleName = "";
        String[] parameters = {};
        ModuleManager moduleManager = new ModuleManager();
        assertThrows(ModuleManagerException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }

    @Test
    public void selectModule_zeroParameterModuleWithParameters_moduleExceptionThrown() {
        String moduleName = "base64encode";
        String[] parameters = {"1"};
        ModuleManager moduleManager = new ModuleManager();

        assertThrows(ModuleException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }

    @Test
    public void selectModule_rotEncodeWithoutParameters_success() {
        String moduleName = "rotencode";
        String[] parameters = {};
        ModuleManager moduleManager = new ModuleManager();

        assertThrows(ModuleException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }

    @Test
    public void selectModule_rotEncodeAdditionalParameters_success() {
        String moduleName = "rotencode";
        String[] parameters = {"13", "a"};
        ModuleManager moduleManager = new ModuleManager();

        assertThrows(ModuleException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }

    @Test
    public void selectModule_rotEncodeInvalidParameters_success() {
        String moduleName = "rotencode";
        String[] parameters = {"a"};
        ModuleManager moduleManager = new ModuleManager();

        assertThrows(ModuleException.class, () -> moduleManager.selectModule(moduleName, parameters));
    }
}
