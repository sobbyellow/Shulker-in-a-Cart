package com.sobb.shulkerinacart.recipe;

import com.sobb.shulkerinacart.ShulkerInACart;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ShulkerInACart.ModID);

    public static final RegistryObject<IRecipeSerializer<ShulkerMinecartColoringRecipe>> SHULKER_MINECART_COLORING =
            RECIPES.register("shulker_minecart_coloring", () -> new SpecialRecipeSerializer<>(ShulkerMinecartColoringRecipe::new));

    public static final RegistryObject<IRecipeSerializer<ShulkerMinecartColoringRecipe2>> SHULKER_MINECART_CRAFTCOLORING =
            RECIPES.register("shulker_minecart_craftcoloring", () -> new SpecialRecipeSerializer<>(ShulkerMinecartColoringRecipe2::new));

    public static final RegistryObject<IRecipeSerializer<ShulkerMinecartCraftingRecipe>> SHULKER_MINECART_CRAFTING =
            RECIPES.register("shulker_minecart_crafting", () -> new SpecialRecipeSerializer<>(ShulkerMinecartCraftingRecipe::new));

}
