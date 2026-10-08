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
    private void addVert(SkyBlockEntity entity, VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, Direction direction, boolean block) {
        consumer.addVertex(matrix, x, y, z);
        if (block) {
            if (entity instanceof VoidBlockEntity) {
                consumer.setColor(25, 25, 51, 255);
            } else {
                consumer.setColor(120, 167, 255, 255);
            }
            consumer.setUv(0f, 0f);
            consumer.setLight(LightTexture.FULL_BRIGHT);
            consumer.setNormal((float)-direction.getStepX(), (float)-direction.getStepY(), (float)-direction.getStepZ());
        }
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}