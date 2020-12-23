package com.sobb.shulkerinacart.recipe;

import com.sobb.shulkerinacart.item.ItemShulkerMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MinecartItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShulkerMinecartCraftingRecipe extends SpecialRecipe {

    public ShulkerMinecartCraftingRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int i = 0;
        int j = 0;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack itemStack = inv.getStackInSlot(k);
            if (!itemStack.isEmpty()) {
                if (Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock) {
                    ++i;
                } else {
                    if (!(itemStack.getItem() instanceof MinecartItem)) {
                        return false;
                    }

                    ++j;
                }

                if (i > 1 || j > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeColor color = DyeColor.WHITE;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemStack1 = inv.getStackInSlot(i);
            if (!itemStack1.isEmpty()) {
                Item item = itemStack1.getItem();
                if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
                    itemStack = itemStack1;
                    color = ((ShulkerBoxBlock)Block.getBlockFromItem(item)).getColor();
                }
            }
        }

        ItemStack itemStack2 = new ItemStack(ItemShulkerMinecart.getItemByColor(color));
        if (itemStack.hasTag()) {
            itemStack2.setTag(itemStack.getTag().copy());
        }

        return itemStack2;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.SHULKER_MINECART_CRAFTING.get();
    }
}
