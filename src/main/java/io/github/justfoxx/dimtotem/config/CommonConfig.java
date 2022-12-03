package io.github.justfoxx.dimtotem.config;

import com.google.gson.JsonObject;
import io.github.ivymc.ivycore.config.ConfigData;

public class CommonConfig extends ConfigData {
    @Override
    public void onRead(JsonObject jsonObject) throws Exception {

    }

    public boolean enabled = true;
}
