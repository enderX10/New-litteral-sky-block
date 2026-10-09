package dev.latvian.mods.literalskyblock.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.latvian.mods.literalskyblock.LiteralSkyBlock;
import dev.latvian.mods.literalskyblock.ProjectionType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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