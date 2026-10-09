package dev.latvian.mods.literalskyblock;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LiteralSkyBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LSBModEventHandler {
    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (var p : ProjectionType.VALUES) {
                if (p.skyBlockItem != null) {
                    event.accept(p.skyBlockItem.get());
                }
            }

            event.accept(LiteralSkyBlock.VANTA_BLACK_ITEM.get());
        }
    }
}