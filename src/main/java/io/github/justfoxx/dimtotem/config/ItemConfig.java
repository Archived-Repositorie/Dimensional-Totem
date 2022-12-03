package io.github.justfoxx.dimtotem.config;

import com.google.gson.JsonObject;
import io.github.ivymc.ivycore.config.ConfigData;
import io.github.justfoxx.dimtotem.PreMain;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.List;

public class ItemConfig extends ConfigData {
    public boolean enabled = true;
    public List<String> blacklist = List.of("minecraft:overworld");
    @Override
    public void onRead(JsonObject jsonObject) throws Exception {
    }
}
