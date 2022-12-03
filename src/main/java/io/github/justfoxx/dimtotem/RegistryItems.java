package io.github.justfoxx.dimtotem;

import io.github.justfoxx.dimtotem.config.Configs;
import io.github.justfoxx.dimtotem.config.ItemConfig;
import io.github.justfoxx.dimtotem.items.DimTotemItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class RegistryItems {
    public static final DimTotemItem DIM_TOTEM = new DimTotemItem();
    public static void init() {
        if(Configs.itemConfig.data.enabled) Registry.register(Registry.ITEM, PreMain.g.id("dimensional_totem"), DIM_TOTEM);
    }
}
