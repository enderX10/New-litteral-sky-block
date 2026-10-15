package io.github.gaming32.literalskyblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class LiteralSkyBlock implements ModInitializer {
    public static final String MOD_ID = "literalskyblock";

    @Override
    public void onInitialize() {
        LSBBlocks.register();
        LSBItems.register();
        LSBBlockEntities.register();

        // Dodawanie do zakładki w 1.21
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> {
            content.accept(LSBItems.SKY_BLOCK);
            content.accept(LSBItems.VOID_BLOCK);
            content.accept(LSBItems.VANTA_BLACK);
        });
    }
}