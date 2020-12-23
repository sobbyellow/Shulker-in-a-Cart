package com.sobb.shulkerinacart.entity;

import com.sobb.shulkerinacart.ShulkerInACart;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ShulkerInACart.ModID);

    public static final RegistryObject<EntityType<ShulkerMinecartEntity>> SHULKER_MINECART = ENTITIES.register(
            "shulker_minecart", () -> EntityType.Builder.<ShulkerMinecartEntity>create(
                    ShulkerMinecartEntity::new, EntityClassification.MISC)
                    .size(0.98F, 0.7F)
                    .build("shulker_minecart"));
}
