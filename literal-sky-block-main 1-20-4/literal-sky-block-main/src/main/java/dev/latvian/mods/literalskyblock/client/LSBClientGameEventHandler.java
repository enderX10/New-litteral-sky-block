package dev.latvian.mods.literalskyblock.client;

import dev.latvian.mods.literalskyblock.LiteralSkyBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@Mod.EventBusSubscriber(modid = LiteralSkyBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class LSBClientGameEventHandler {
    @SubscribeEvent
    public static void renderLast(RenderLevelStageEvent event) {
        LSBClient.renderSky(event);
    }
}