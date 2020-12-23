package com.sobb.shulkerinacart.item;

import com.sobb.shulkerinacart.entity.ShulkerMinecartEntity;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemShulkerMinecart extends Item {

    private static final IDispenseItemBehavior SHULKER_MINECART_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior behavior = new DefaultDispenseItemBehavior();

        // Dispense the shulker minecart from a dispenser.
        @Override
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            World world = source.getWorld();
            double d0 = source.getX() + (double)direction.getXOffset() * 1.125D;
            double d1 = Math.floor(source.getY()) + (double)direction.getYOffset();
            double d2 = source.getZ() + (double)direction.getZOffset() * 1.125D;
            BlockPos blockPos = source.getBlockPos().offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock)blockState.getBlock()).getRailDirection(blockState, world, blockPos, null) : RailShape.NORTH_SOUTH;
            double d3;
            if (blockState.isIn(BlockTags.RAILS)) {
                if (railShape.isAscending()) {
                    d3 = 0.6D;
                } else {
                    d3 = 0.1D;
                }
            } else {
                if (!blockState.isAir() || !world.getBlockState(blockPos.down()).isIn(BlockTags.RAILS)) {
                    return this.behavior.dispense(source, stack);
                }

                BlockState blockState1 = world.getBlockState(blockPos.down());
                RailShape railShape1 = blockState1.getBlock() instanceof AbstractRailBlock ? blockState1.get(((AbstractRailBlock)blockState1.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                if (direction != Direction.DOWN && railShape1.isAscending()) {
                    d3 = -0.4D;
                } else {
                    d3 = -0.9D;
                }
            }

            ContainerMinecartEntity containerMinecartEntity = new ShulkerMinecartEntity(((ItemShulkerMinecart)stack.getItem()).getColor(), world, d0, d1 + d3, d2);
            if (stack.hasDisplayName()) {
                containerMinecartEntity.setCustomName(stack.getDisplayName());
            }

            if (stack.hasTag()) {
                CompoundNBT compoundNBT = stack.getChildTag("BlockEntityTag");
                if (compoundNBT != null && compoundNBT.contains("Items", 9)) {
                    NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(compoundNBT, nonNullList);
                    for (int i = 0; i < nonNullList.size(); ++i) {
                        ItemStack itemStack2 = nonNullList.get(i);
                        if (!itemStack2.isEmpty()) {
                            containerMinecartEntity.setInventorySlotContents(i, itemStack2);
                        }
                    }
                }
            }
            world.addEntity(containerMinecartEntity);
            stack.shrink(1);
            return stack;
        }
    };

    @Nullable
    private final DyeColor color;

    public ItemShulkerMinecart(@Nullable DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        DispenserBlock.registerDispenseBehavior(this, SHULKER_MINECART_DISPENSE_BEHAVIOR);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundNBT = stack.getChildTag("BlockEntityTag");
        if (compoundNBT != null) {
            if (compoundNBT.contains("Items", 9)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundNBT, nonNullList);
                int i = 0;
                int j = 0;

                for (ItemStack itemStack : nonNullList) {
                    if (!itemStack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            IFormattableTextComponent iFormattableTextComponent = itemStack.getDisplayName().deepCopy();
                            iFormattableTextComponent.appendString(" x").appendString(String.valueOf(itemStack.getCount()));
                            tooltip.add(iFormattableTextComponent);
                        }
                    }
                }

                if (j - i > 0) {
                    tooltip.add((new TranslationTextComponent("container.shulkerBox.more", j - i)).mergeStyle(TextFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getItem();

        // Undying Shulker Minecarts
        if (blockState.getBlock() instanceof CauldronBlock) {
            if (blockState.get(CauldronBlock.LEVEL) > 0 && !world.isRemote) {
                CauldronBlock cauldron = (CauldronBlock) blockState.getBlock();
                ItemStack itemStack1 = new ItemStack(ModItems.SHULKER_MINECART.get(), 1);
                if (itemStack.hasTag()) {
                    itemStack1.setTag(itemStack.getTag().copy());
                }
                context.getPlayer().setHeldItem(context.getHand(), itemStack1);
                cauldron.setWaterLevel(world, blockPos, blockState, blockState.get(CauldronBlock.LEVEL) - 1);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        } else if (blockState.isIn(BlockTags.RAILS)) {
            if (!world.isRemote) {
                RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock)blockState.getBlock()).getRailDirection(blockState, world, blockPos, null) : RailShape.NORTH_SOUTH;
                double d0 = 0.0D;
                if (railShape.isAscending()) {
                    d0 = 0.5D;
                }

                ContainerMinecartEntity containerMinecartEntity = new ShulkerMinecartEntity(this.getColor(), world, (double)blockPos.getX() + 0.5D,
                        (double)blockPos.getY() + 0.0625D + d0, (double)blockPos.getZ() + 0.5D);

                if (itemStack.hasDisplayName()) {
                    containerMinecartEntity.setCustomName(itemStack.getDisplayName());
                }

                // Sets the shulker minecart's inventory from the item's nbt
                if (itemStack.hasTag()) {
                    CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
                    if (compoundNBT != null && compoundNBT.contains("Items", 9)) {
                        NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                        ItemStackHelper.loadAllItems(compoundNBT, nonNullList);
                        for (int i = 0; i < nonNullList.size(); ++i) {
                            ItemStack itemStack2 = nonNullList.get(i);
                            if (!itemStack2.isEmpty()) {
                                containerMinecartEntity.setInventorySlotContents(i, itemStack2);
                            }
                        }
                    }
                }
                world.addEntity(containerMinecartEntity);
            }
            itemStack.shrink(1);

            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    public static Item getItemByColor(@Nullable DyeColor color) {
        if (color == null) {
            return ModItems.SHULKER_MINECART.get();
        } else {
            switch(color) {
                case WHITE:
                    return ModItems.WHITE_SHULKER_MINECART.get();
                case ORANGE:
                    return ModItems.ORANGE_SHULKER_MINECART.get();
                case MAGENTA:
                    return ModItems.MAGENTA_SHULKER_MINECART.get();
                case LIGHT_BLUE:
                    return ModItems.LIGHT_BLUE_SHULKER_MINECART.get();
                case YELLOW:
                    return ModItems.YELLOW_SHULKER_MINECART.get();
                case LIME:
                    return ModItems.LIME_SHULKER_MINECART.get();
                case PINK:
                    return ModItems.PINK_SHULKER_MINECART.get();
                case GRAY:
                    return ModItems.GRAY_SHULKER_MINECART.get();
                case LIGHT_GRAY:
                    return ModItems.LIGHT_GRAY_SHULKER_MINECART.get();
                case CYAN:
                    return ModItems.CYAN_SHULKER_MINECART.get();
                case PURPLE:
                default:
                    return ModItems.PURPLE_SHULKER_MINECART.get();
                case BLUE:
                    return ModItems.BLUE_SHULKER_MINECART.get();
                case BROWN:
                    return ModItems.BROWN_SHULKER_MINECART.get();
                case GREEN:
                    return ModItems.GREEN_SHULKER_MINECART.get();
                case RED:
                    return ModItems.RED_SHULKER_MINECART.get();
                case BLACK:
                    return ModItems.BLACK_SHULKER_MINECART.get();
            }
        }
    }

}
