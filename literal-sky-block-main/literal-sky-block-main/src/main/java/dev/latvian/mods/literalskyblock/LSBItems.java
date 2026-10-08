package dev.latvian.mods.literalskyblock;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface LSBItems {
    DeferredRegister.Items REGISTER = DeferredRegister.createItems(LiteralSkyBlock.MOD_ID);

    // Rejestrujemy przedmiot dla bloku Vanta Black
    DeferredItem<BlockItem> VANTA_BLACK = REGISTER.registerSimpleBlockItem(LSBBlocks.VANTA_BLACK);
}