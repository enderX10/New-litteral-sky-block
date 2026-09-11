package dev.latvian.mods.literalskyblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface LSBBlocks {
    DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(LiteralSkyBlock.MOD_ID);

    // Całkowicie nowy, niezależny blok Vanta Black
    DeferredBlock<Block> VANTA_BLACK = REGISTER.registerBlock(
            "vanta_black",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );
}