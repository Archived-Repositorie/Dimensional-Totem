package io.github.justfoxx.dimtotem.items;

import io.github.justfoxx.dimtotem.PreMain;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.loader.api.FabricLoader;
import net.levelz.data.LevelLists;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DimTotemItem extends Item {
    public DimTotemItem() {
        super(new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.RARE));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("dimension")) {
            tooltip.add(Text.translatable("tooltip.dimtotem.dimension", Text.of(nbt.getString("dimension"))).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.dimtotem.not_dimension").formatted(Formatting.RED));
        }
    }

    public TypedActionResult<ItemStack> teleportUse(ServerPlayerEntity player, ServerWorld world, ItemStack itemStack) {
        NbtCompound nbt = itemStack.getOrCreateNbt();
        String dimension = nbt.getString("dimension");
        ServerWorld destination = player.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dimension)));
        if (destination == null) {
            return TypedActionResult.fail(itemStack);
        }
        if (world.getRegistryKey() != destination.getRegistryKey()) {
            return TypedActionResult.fail(itemStack);
        }
        if(!canUse(itemStack, player)) {
            return TypedActionResult.fail(itemStack);
        }


        if(FabricLoader.getInstance().isModLoaded("levelz")) {
            var customList = LevelLists.customItemList;
            String string = Registry.ITEM.getId(itemStack.getItem()).toString();
            if (!customList.isEmpty() && !PlayerStatsManager.playerLevelisHighEnough(player, customList, string, true)) {
//            player.sendMessage(
//                    new TranslatableText("item.levelz." + customList.get(customList.indexOf(string) + 1) + ".tooltip", customList.get(customList.indexOf(string) + 2)).formatted(Formatting.RED),
//                    true);
                return TypedActionResult.fail(itemStack);
            }

            itemStack.decrement(1);
        }

        itemStack.decrement(1);
        return TypedActionResult.success(itemStack);
    }

    private boolean canUse(ItemStack itemStack, ServerPlayerEntity player) {
        return true;
    }

}
