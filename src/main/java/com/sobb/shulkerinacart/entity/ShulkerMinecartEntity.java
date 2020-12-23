package com.sobb.shulkerinacart.entity;

import com.sobb.shulkerinacart.inventory.ShulkerMinecartContainer;
import com.sobb.shulkerinacart.item.ItemShulkerMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MinecartTickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ShulkerMinecartEntity extends ContainerMinecartEntity {

    private static final DataParameter<Byte> COLOR = EntityDataManager.createKey(ShulkerMinecartEntity.class, DataSerializers.BYTE);

    public ShulkerMinecartEntity(EntityType<? extends ShulkerMinecartEntity> type, World world) {
        super(type, world);
        if (world.isRemote) {
            setupSound();
        }
    }

    public ShulkerMinecartEntity(@Nullable DyeColor color, World world, double x, double y, double z) {
        super(ModEntities.SHULKER_MINECART.get(), x, y, z, world);
        this.setColor(color);
        if (world.isRemote) {
            setupSound();
        }
    }

    @Override
    public void killMinecart(DamageSource source) {
        this.dropContentsWhenDead(false);
        this.remove();
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack itemStack = new ItemStack(getBlockByColor(this.getColor()));
            CompoundNBT compoundNBT = new CompoundNBT();
            this.writeAdditional(compoundNBT);
            compoundNBT.remove("Color");

            if (compoundNBT.getList("Items", 10).size() > 0) {
                itemStack.setTagInfo("BlockEntityTag", compoundNBT);
            }

            if (hasCustomName()) {
                itemStack.setDisplayName(getCustomName());
            }

            this.entityDropItem(itemStack);
            this.entityDropItem(new ItemStack(Items.MINECART));
            this.markDirty();

            Entity entity = source.getImmediateSource();
            if (entity != null && entity.getType() == EntityType.PLAYER) {
                PiglinTasks.func_234478_a_((PlayerEntity)entity, true);
            }
        }
    }

    // Drop the shulker box with its contents when broken in creative mode
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote && !this.removed) {
            if (this.isInvulnerableTo(source)) {
                return false;
            } else {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.markVelocityChanged();
                this.setDamage(this.getDamage() + amount * 10.0F);
                boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode;
                if (flag || this.getDamage() > 40.0F) {
                    this.removePassengers();
                    CompoundNBT compoundNBT = new CompoundNBT();
                    this.writeAdditional(compoundNBT);
                    if (flag && !this.hasCustomName() && !(compoundNBT.getList("Items", 10).size() > 0)) {
                        this.remove();
                    } else {
                        this.killMinecart(source);
                    }
                }

                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(COLOR, (byte)-1);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setColor(compoundNBT.getByte("Color") == -1 ? null : DyeColor.byId(compoundNBT.getByte("Color")));
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putByte("Color", this.getColor() == null ? -1 : (byte) this.getColor().getId());
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return !(stack.getItem() instanceof ItemShulkerMinecart || Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public int getSizeInventory() { return 27; }

    @Override
    public Type getMinecartType() { return Type.CHEST; }

    @Override
    public ItemStack getCartItem() {
        return new ItemStack(ItemShulkerMinecart.getItemByColor(this.getColor()));
    }

    @Nullable
    public DyeColor getColor() {
        return this.dataManager.get(COLOR) == -1 ? null : DyeColor.byId(this.dataManager.get(COLOR));
    }

    public void setColor(@Nullable DyeColor color) {
        this.dataManager.set(COLOR, color == null ? -1 : (byte)color.getId());
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return getBlockByColor(this.getColor()).getDefaultState();
    }

    @Override
    public int getDefaultDisplayTileOffset() { return 2; }

    @Override
    protected Container createContainer(int id, PlayerInventory playerInventoryIn) {
        return ShulkerMinecartContainer.newShulkerCartContainer(id, playerInventoryIn, this);
    }

    @OnlyIn(Dist.CLIENT)
    public void setupSound() {
        Minecraft.getInstance().getSoundHandler().play(new MinecartTickableSound(this));
    }

    public static Block getBlockByColor(@Nullable DyeColor color) {
        if (color == null) {
            return Blocks.SHULKER_BOX;
        } else {
            switch (color) {
                case WHITE:
                    return Blocks.WHITE_SHULKER_BOX;
                case ORANGE:
                    return Blocks.ORANGE_SHULKER_BOX;
                case MAGENTA:
                    return Blocks.MAGENTA_SHULKER_BOX;
                case LIGHT_BLUE:
                    return Blocks.LIGHT_BLUE_SHULKER_BOX;
                case YELLOW:
                    return Blocks.YELLOW_SHULKER_BOX;
                case LIME:
                    return Blocks.LIME_SHULKER_BOX;
                case PINK:
                    return Blocks.PINK_SHULKER_BOX;
                case GRAY:
                    return Blocks.GRAY_SHULKER_BOX;
                case LIGHT_GRAY:
                    return Blocks.LIGHT_GRAY_SHULKER_BOX;
                case CYAN:
                    return Blocks.CYAN_SHULKER_BOX;
                case PURPLE:
                default:
                    return Blocks.PURPLE_SHULKER_BOX;
                case BLUE:
                    return Blocks.BLUE_SHULKER_BOX;
                case BROWN:
                    return Blocks.BROWN_SHULKER_BOX;
                case GREEN:
                    return Blocks.GREEN_SHULKER_BOX;
                case RED:
                    return Blocks.RED_SHULKER_BOX;
                case BLACK:
                    return Blocks.BLACK_SHULKER_BOX;
            }
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
