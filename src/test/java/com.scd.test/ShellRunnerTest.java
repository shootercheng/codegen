package com.scd.test;

import com.scd.api.ShellRunner;
import org.junit.Test;

/**
 * @author James
 */
public class ShellRunnerTest {

    @Test
    public void shellRunWithConfig() {
        String configFilePath = "config/generatorProConfigTemplate.xml";
        String[] command = {ShellRunner.CONFIG_FILE, configFilePath, ShellRunner.OVERWRITE};
        ShellRunner.genCode(command);
    }
}
