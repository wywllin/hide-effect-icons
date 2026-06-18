package com.example.hideeffecticons;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EffectExtensionWrapperCache {
    private static final Map<IClientMobEffectExtensions, IClientMobEffectExtensions> CACHE = new ConcurrentHashMap<>();

    public static IClientMobEffectExtensions getWrapper(IClientMobEffectExtensions original) {
        return CACHE.computeIfAbsent(original, orig -> new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return orig.isVisibleInInventory(instance);
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean renderInventoryIcon(MobEffectInstance instance, net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen<?> screen, net.minecraft.client.gui.GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return orig.renderInventoryIcon(instance, screen, guiGraphics, x, y, blitOffset);
            }

            @Override
            public boolean renderInventoryText(MobEffectInstance instance, net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen<?> screen, net.minecraft.client.gui.GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return orig.renderInventoryText(instance, screen, guiGraphics, x, y, blitOffset);
            }

            @Override
            public boolean renderGuiIcon(MobEffectInstance instance, net.minecraft.client.gui.Gui gui, net.minecraft.client.gui.GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
                return orig.renderGuiIcon(instance, gui, guiGraphics, x, y, z, alpha);
            }
        });
    }
}
