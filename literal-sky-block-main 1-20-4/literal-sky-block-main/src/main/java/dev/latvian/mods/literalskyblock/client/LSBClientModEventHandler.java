package dev.latvian.mods.literalskyblock.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.latvian.mods.literalskyblock.LiteralSkyBlock;
import dev.latvian.mods.literalskyblock.ProjectionType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = LiteralSkyBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LSBClientModEventHandler {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(LiteralSkyBlock.SKY_BLOCK_ENTITY.get(), SkyBlockEntityRenderer::new);

        ProjectionType.SKY.renderType = LSBClient.SKY_RENDER_TYPE;
        ProjectionType.VOID.renderType = RenderType.endGateway();
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), LiteralSkyBlock.SKY, DefaultVertexFormat.POSITION), LSBClient::setSkyShader);
    }
}