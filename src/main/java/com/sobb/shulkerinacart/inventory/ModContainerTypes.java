package com.sobb.shulkerinacart.inventory;

import com.sobb.shulkerinacart.ShulkerInACart;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ShulkerInACart.ModID);

    public static final RegistryObject<ContainerType<ShulkerMinecartContainer>> SHULKER_MINECART =
            CONTAINERS.register("shulker_minecart", () -> new ContainerType<>(ShulkerMinecartContainer::newShulkerCartContainer));
}
