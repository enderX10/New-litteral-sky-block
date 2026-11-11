
package dev.latvian.mods.literalskyblock.client;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.latvian.mods.literalskyblock.LSBBlockEntities;
import dev.latvian.mods.literalskyblock.LiteralSkyBlock;
import dev.latvian.mods.literalskyblock.ProjectionType;
import dev.latvian.mods.literalskyblock.client.LSBClient;
import dev.latvian.mods.literalskyblock.client.SkyBlockEntityRenderer;
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
        BlockEntityRenderers.register(LSBBlockEntities.SKY_BLOCK.get(), SkyBlockEntityRenderer::new);
        ProjectionType.SKY.renderType = LSBClient.SKY_RENDER_TYPE;
        ProjectionType.VOID.renderType = RenderType.endGateway();
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), LiteralSkyBlock.SKY, DefaultVertexFormat.POSITION), LSBClient::setSkyShader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}