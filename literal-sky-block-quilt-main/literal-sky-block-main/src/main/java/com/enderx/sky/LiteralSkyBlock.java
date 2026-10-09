package com.enderx.sky;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;


public class LiteralSkyBlock implements ModInitializer {
	public static final String MOD_ID = "literalskyblock";

	@Override
	public void onInitialize(ModContainer mod) {
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
