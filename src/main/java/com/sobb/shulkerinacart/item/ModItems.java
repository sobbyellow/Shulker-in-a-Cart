package com.sobb.shulkerinacart.item;

import com.sobb.shulkerinacart.ShulkerInACart;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShulkerInACart.ModID);

    public static final RegistryObject<Item> SHULKER_MINECART = ITEMS.register("shulker_minecart", () ->
            new ItemShulkerMinecart(null, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> WHITE_SHULKER_MINECART = ITEMS.register("white_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.WHITE, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> ORANGE_SHULKER_MINECART = ITEMS.register("orange_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.ORANGE, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> MAGENTA_SHULKER_MINECART = ITEMS.register("magenta_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.MAGENTA, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> LIGHT_BLUE_SHULKER_MINECART = ITEMS.register("light_blue_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.LIGHT_BLUE, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> YELLOW_SHULKER_MINECART = ITEMS.register("yellow_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.YELLOW, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> LIME_SHULKER_MINECART = ITEMS.register("lime_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.LIME, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> PINK_SHULKER_MINECART = ITEMS.register("pink_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.PINK, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> GRAY_SHULKER_MINECART = ITEMS.register("gray_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.GRAY, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> LIGHT_GRAY_SHULKER_MINECART = ITEMS.register("light_gray_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.LIGHT_GRAY, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> CYAN_SHULKER_MINECART = ITEMS.register("cyan_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.CYAN, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> PURPLE_SHULKER_MINECART = ITEMS.register("purple_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.PURPLE, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> BLUE_SHULKER_MINECART = ITEMS.register("blue_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.BLUE, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> BROWN_SHULKER_MINECART = ITEMS.register("brown_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.BROWN, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> GREEN_SHULKER_MINECART = ITEMS.register("green_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.GREEN, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> RED_SHULKER_MINECART = ITEMS.register("red_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.RED, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));
    public static final RegistryObject<Item> BLACK_SHULKER_MINECART = ITEMS.register("black_shulker_minecart", () ->
            new ItemShulkerMinecart(DyeColor.BLACK, new Item.Properties().group(ShulkerInACart.SHULKER_MINECARTS).maxStackSize(1)));

}
