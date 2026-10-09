package dev.latvian.mods.literalskyblock.client;

import dev.latvian.mods.literalskyblock.LiteralSkyBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LiteralSkyBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class LSBClientGameEventHandler {
    @SubscribeEvent
    public static void renderLast(RenderLevelStageEvent event) {
        LSBClient.renderSky(event);
    }
}