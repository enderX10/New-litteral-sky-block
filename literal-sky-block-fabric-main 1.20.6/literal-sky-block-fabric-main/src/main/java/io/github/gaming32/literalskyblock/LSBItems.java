package io.github.gaming32.literalskyblock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class LSBItems {
    public static final Item SKY_BLOCK = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation(LiteralSkyBlock.MOD_ID, "sky_block"),
            new BlockItem(LSBBlocks.SKY_BLOCK, new Item.Properties())
    );
    public static final Item VOID_BLOCK = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation(LiteralSkyBlock.MOD_ID, "void_block"),
            new BlockItem(LSBBlocks.VOID_BLOCK, new Item.Properties())
    );
    public static final Item VANTA_BLACK = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation (LiteralSkyBlock.MOD_ID, "vanta_black"),
            new BlockItem(LSBBlocks.VANTA_BLACK, new Item.Properties())
    );

    static void register() {}
}