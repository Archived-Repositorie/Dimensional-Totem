package io.github.justfoxx.dimtotem.mixin;

import io.github.justfoxx.dimtotem.PreMain;
import io.github.justfoxx.dimtotem.RegistryItems;
import io.github.justfoxx.dimtotem.config.Configs;
import io.github.justfoxx.dimtotem.items.DimTotemItem;
import net.fabricmc.loader.api.FabricLoader;
import net.levelz.data.LevelLists;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AdvancementCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Shadow @Final public MinecraftServer server;

    @Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
    private void onMoveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        if(!Configs.itemConfig.data.enabled) return;
        if(Configs.itemConfig.data.blacklist.contains(destination.getRegistryKey().getValue().toString())) return;

        var player = (ServerPlayerEntity) (Object) this;
        if(player.isCreative()) return;
        if(player.isSpectator()) return;

        var mainHandStack = player.getMainHandStack();
        var offHandStack = player.getOffHandStack();

        DimTotemItem totem;
        ItemStack totemStack;
        TypedActionResult<ItemStack> result;
        if(mainHandStack.isOf(RegistryItems.DIM_TOTEM)) {
            totemStack = mainHandStack;
        } else if(offHandStack.isOf(RegistryItems.DIM_TOTEM)) {
            totemStack = offHandStack;
        } else {
            player.sendMessageToClient(Text.translatable("msg.dimtotem.no_totem"), true);
            cir.setReturnValue(null);
            cir.cancel();
            return;
        }
        totem = (DimTotemItem) mainHandStack.getItem();
        result = totem.teleportUse(player, destination, totemStack);
        if(!result.getResult().isAccepted()) {
            player.sendMessageToClient(Text.translatable("msg.dimtotem.wrong_totem"), true);
            cir.setReturnValue(null);
            cir.cancel();
        }

    }
}
