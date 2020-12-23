package com.sobb.shulkerinacart.inventory;

import com.sobb.shulkerinacart.item.ItemShulkerMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ShulkerMinecartSlot extends Slot {

    public ShulkerMinecartSlot(IInventory inventory, int slotIndex, int x, int y) {
        super(inventory, slotIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.isEmpty() || !(stack.getItem() instanceof ItemShulkerMinecart || Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }
    
}
