package io.github.gaming32.literalskyblock.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import io.github.gaming32.literalskyblock.SkyBlock;
import io.github.gaming32.literalskyblock.SkyBlockEntity;
import io.github.gaming32.literalskyblock.VoidBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class SkyBlockEntityRenderer implements BlockEntityRenderer<SkyBlockEntity> {
    public SkyBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SkyBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource source, int light, int block) {
        if (!entity.getBlockState().getValue(SkyBlock.ACTIVE)) return;
        final Matrix4f matrix4f = poseStack.last().pose();
        final boolean solid = LSBClient.needIrisCompat && IrisCompat.shadersEnabled();
        renderCube(entity, matrix4f, source.getBuffer(
                solid
                        ? RenderType.solid()
                        : entity instanceof VoidBlockEntity
                        ? RenderType.endGateway()
                        : LSBClient.SKY_RENDER_TYPE
        ), solid);
        LSBClient.updateSky = true;
    }

    private void renderCube(SkyBlockEntity entity, Matrix4f matrix, VertexConsumer buffer, boolean block) {
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH, block);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH, block);
        renderFace(entity, matrix, buffer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST, block);
        renderFace(entity, matrix, buffer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST, block);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN, block);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP, block);
    }

    private void renderFace(SkyBlockEntity entity, Matrix4f matrix, VertexConsumer buffer, float f, float g, float h, float i, float j, float k, float l, float m, Direction direction, boolean block) {
        if (entity.shouldRenderFace(direction)) {
            addVert(entity, buffer, matrix, f, h, j, direction, block);
            addVert(entity, buffer, matrix, g, h, k, direction, block);
            addVert(entity, buffer, matrix, g, i, l, direction, block);
            addVert(entity, buffer, matrix, f, i, m, direction, block);
        }
    }

    // Nowy system w 1.21: wymusza kolejność budowania wierzchołka i eliminuje endVertex()
    // Powrót do systemu z 1.20.6: używamy buildera kończącego się endVertex()
    private void addVert(SkyBlockEntity entity, VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, Direction direction, boolean block) {
        if (block) {
            int r = (entity instanceof VoidBlockEntity) ? 25 : 120;
            int g = (entity instanceof VoidBlockEntity) ? 25 : 167;
            int b = (entity instanceof VoidBlockEntity) ? 51 : 255;

            consumer.vertex(matrix, x, y, z)
                    .color(r, g, b, 255)
                    .uv(0f, 0f)
                    .overlayCoords(net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY)
                    .uv2(LightTexture.FULL_BRIGHT)
                    .normal((float)-direction.getStepX(), (float)-direction.getStepY(), (float)-direction.getStepZ())
                    .endVertex();
        } else {
            consumer.vertex(matrix, x, y, z).endVertex();
        }
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}