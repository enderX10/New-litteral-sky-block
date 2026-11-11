package dev.latvian.mods.literalskyblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public interface LSBBlockEntities {
    DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LiteralSkyBlock.MOD_ID);

    RegistryObject<BlockEntityType<SkyBlockEntity>> SKY_BLOCK = REGISTER.register("sky_block",
            () -> BlockEntityType.Builder.of(SkyBlockEntity::new,
                    Arrays.stream(ProjectionType.VALUES).map(p -> p.skyBlock.get()).toArray(Block[]::new)).build(null));
}