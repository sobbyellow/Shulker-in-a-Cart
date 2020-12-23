package com.sobb.shulkerinacart;

import com.sobb.shulkerinacart.entity.ModEntities;
import com.sobb.shulkerinacart.inventory.ModContainerTypes;
import com.sobb.shulkerinacart.inventory.ShulkerMinecartScreen;
import com.sobb.shulkerinacart.item.ModItems;
import com.sobb.shulkerinacart.recipe.ModRecipes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ShulkerInACart.ModID)
public class ShulkerInACart
{
    public static final String ModID = "shulkerinacart";
    public static IEventBus ModEventBus;

    public static final ItemGroup SHULKER_MINECARTS = new ItemGroup("shulker_minecarts") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SHULKER_MINECART.get());
        }
    };

    public ShulkerInACart() {
        ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEventBus.addListener(this::commonSetup);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ModEventBus.addListener(this::clientSetup));

        ModItems.ITEMS.register(ModEventBus);
        ModEntities.ENTITIES.register(ModEventBus);
        ModContainerTypes.CONTAINERS.register(ModEventBus);
        ModRecipes.RECIPES.register(ModEventBus);

    }

    void commonSetup(FMLCommonSetupEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SHULKER_MINECART.get(), MinecartRenderer::new);
        ScreenManager.registerFactory(ModContainerTypes.SHULKER_MINECART.get(), ShulkerMinecartScreen::new);
    }

}