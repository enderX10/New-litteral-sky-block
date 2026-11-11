package dev.latvian.mods.literalskyblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface LSBBlocks {
    DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, LiteralSkyBlock.MOD_ID);

    RegistryObject<Block> VANTA_BLACK = REGISTER.register("vanta_black",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
}