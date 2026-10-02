package dev.latvian.mods.literalskyblock.mixin;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.literalskyblock.client.CapturedInfo;
import dev.latvian.mods.literalskyblock.client.LSBClient;
import dev.latvian.mods.literalskyblock.client.LevelRendererLSB;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements LevelRendererLSB {
    @Shadow @Nullable private Frustum capturedFrustum;
    @Shadow private Frustum cullingFrustum;
    @Shadow @Final private Minecraft minecraft;
    @Shadow private int ticks;
    @Shadow @Final private RenderBuffers renderBuffers;

    @Unique
    private CapturedInfo lsb$capturedInfo = null;

    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    private void lsb$captureFrustum(PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo ci) {
        lsb$capturedInfo = new CapturedInfo(lightTexture, poseStack, projectionMatrix);
    }

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
                    shift = At.Shift.AFTER
            ),
            require = 0
    )
    private void lsb$drawSkyBuffer(PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo ci) {
        renderBuffers.bufferSource().endBatch(LSBClient.SKY_RENDER_TYPE);
    }

    @Shadow protected abstract void renderSnowAndRain(LightTexture lightTexture, float delta, double cameraX, double cameraY, double cameraZ);
    @Shadow public abstract void renderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup);
    @Shadow protected abstract void checkPoseStack(PoseStack poseStack);
    @Shadow public abstract void renderClouds(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ);

    @Unique
    @Override
    public void lsb$renderSky(float delta, Camera camera, GameRenderer gameRenderer, TextureTarget target) {
        if (lsb$capturedInfo == null) return;

        var levelRenderer = (LevelRenderer) (Object) this;

        var lightTexture = lsb$capturedInfo.lightTexture();
        var poseStack = lsb$capturedInfo.poseStack();
        var projectionMatrix = lsb$capturedInfo.projectionMatrix();

        Vec3 camPos = camera.getPosition();
        double camX = camPos.x();
        double camY = camPos.y();
        double camZ = camPos.z();
        Frustum frustum = this.capturedFrustum != null ? this.capturedFrustum : this.cullingFrustum;

        FogRenderer.setupColor(camera, delta, this.minecraft.level, this.minecraft.options.getEffectiveRenderDistance(), gameRenderer.getDarkenWorldAmount(delta));
        FogRenderer.levelFogColor();
        RenderSystem.clear(16640, Minecraft.ON_OSX);
        float f1 = gameRenderer.getRenderDistance();
        boolean flag1 = this.minecraft.level.effects().isFoggyAt(Mth.floor(camX), Mth.floor(camY)) || this.minecraft.gui.getBossOverlay().shouldCreateWorldFog();

        RenderSystem.setShader(GameRenderer::getPositionShader);
        this.renderSky(poseStack, projectionMatrix, delta, camera, flag1, () -> FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, f1, flag1, delta));

        net.neoforged.neoforge.client.ClientHooks.dispatchRenderStage(net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage.AFTER_SKY, levelRenderer, poseStack, projectionMatrix, this.ticks, camera, frustum);

        FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_TERRAIN, Math.max(f1, 32.0F), flag1, delta);

        poseStack.pushPose();

        if (this.minecraft.options.getCloudsType() != CloudStatus.OFF) {
            this.renderClouds(poseStack, projectionMatrix, delta, camX, camY, camZ);
        }

        this.renderSnowAndRain(lightTexture, delta, camX, camY, camZ);

        poseStack.popPose();

        // Sprawdzamy stos dopiero PO jego wyczyszczeniu (popPose)
        this.checkPoseStack(poseStack);

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        FogRenderer.setupNoFog();


    }
}