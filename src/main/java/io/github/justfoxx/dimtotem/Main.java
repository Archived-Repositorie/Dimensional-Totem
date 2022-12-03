package io.github.justfoxx.dimtotem;

import io.github.justfoxx.dimtotem.config.Configs;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        if (!Configs.commonConfig.data.enabled) {
            PreMain.g.LOGGER.info("DimTotem is disabled in commonconfig.json");
            return;
        }
        RegistryItems.init();
    }
}
