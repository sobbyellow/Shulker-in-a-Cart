package com.sobb.shulkerinacart.recipe;

import com.sobb.shulkerinacart.item.ItemShulkerMinecart;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class ShulkerMinecartColoringRecipe extends SpecialRecipe {

    public ShulkerMinecartColoringRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int i = 0;
        int j = 0;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack itemStack = inv.getStackInSlot(k);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof ItemShulkerMinecart) {
                    ++i;
                } else {
                    if (!itemStack.getItem().isIn(Tags.Items.DYES)) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeColor dyeColor = DyeColor.WHITE;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemStack1 = inv.getStackInSlot(i);
            if (!itemStack1.isEmpty()) {
                Item item = itemStack1.getItem();
                if (item instanceof ItemShulkerMinecart) {
                    itemStack = itemStack1;
                } else {
                    DyeColor temp = DyeColor.getColor(itemStack1);
                    if (temp != null) dyeColor = temp;
                }
            }
        }

        ItemStack itemStack2 = new ItemStack(ItemShulkerMinecart.getItemByColor(dyeColor));
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
        return ModRecipes.SHULKER_MINECART_COLORING.get();
    }
}
