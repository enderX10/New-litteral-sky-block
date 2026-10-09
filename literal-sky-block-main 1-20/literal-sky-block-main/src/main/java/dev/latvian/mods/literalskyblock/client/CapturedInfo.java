package dev.latvian.mods.literalskyblock.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;

public record CapturedInfo(LightTexture lightTexture, PoseStack poseStack, Matrix4f projectionMatrix) {}