package com.sobb.shulkerinacart.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ShulkerMinecartContainer extends Container {

    private final PlayerInventory playerInventory;
    private final IInventory inventory;


    public static ShulkerMinecartContainer newShulkerCartContainer(int id, PlayerInventory playerInventory) {
        return new ShulkerMinecartContainer(id, playerInventory, new Inventory(27));
    }

    public static ShulkerMinecartContainer newShulkerCartContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        return new ShulkerMinecartContainer(id, playerInventory, inventory);
    }

    public ShulkerMinecartContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        super(ModContainerTypes.SHULKER_MINECART.get(), id);
        assertInventorySize(inventory, 27);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        inventory.openInventory(playerInventory.player);
        this.setupSlots();
    }

    private void setupSlots() {
        for (int k = 0; k < 3; ++k) { // Shulker minecart inventory
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new ShulkerMinecartSlot(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }
        for (int i1 = 0; i1 < 3; ++i1) { // Player inventory
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) { // Player hotbar
            this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if (index < this.inventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemStack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, 0, this.inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStack;
    }
}
