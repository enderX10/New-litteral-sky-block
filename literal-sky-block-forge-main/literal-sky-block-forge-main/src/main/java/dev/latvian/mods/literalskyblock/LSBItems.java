package dev.latvian.mods.literalskyblock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface LSBItems {
    DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, LiteralSkyBlock.MOD_ID);

    RegistryObject<Item> VANTA_BLACK = REGISTER.register("vanta_black",
            () -> new BlockItem(LSBBlocks.VANTA_BLACK.get(), new Item.Properties()));
}