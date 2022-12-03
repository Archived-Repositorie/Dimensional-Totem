package io.github.justfoxx.dimtotem;

import io.github.ivymc.ivycore.Global;
import io.github.ivymc.ivycore.config.ConfigBuilder;
import io.github.justfoxx.dimtotem.config.CommonConfig;
import io.github.justfoxx.dimtotem.config.Configs;
import io.github.justfoxx.dimtotem.config.ItemConfig;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.IOException;
import java.nio.file.Path;

public class PreMain implements PreLaunchEntrypoint {
    public static Global g = new Global("dimtotem");
    public static ConfigBuilder configBuilder = new ConfigBuilder(g.MOD_ID);

    @Override
    public void onPreLaunch() {
        try {
            configBuilder.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Configs.commonConfig = configBuilder.createConfigKey(Path.of("commonconfig.json"), CommonConfig.class);
        Configs.itemConfig = configBuilder.createConfigKey(Path.of("itemconfig.json"), ItemConfig.class);
        try {
            Configs.commonConfig.readConfig();
            Configs.itemConfig.readConfig();
        } catch (IOException e) {
            try {
                Configs.commonConfig.writeConfig();
                Configs.itemConfig.writeConfig();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
