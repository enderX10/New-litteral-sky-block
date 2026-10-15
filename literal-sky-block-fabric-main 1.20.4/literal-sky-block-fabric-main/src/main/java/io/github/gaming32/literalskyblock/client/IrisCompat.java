package io.github.gaming32.literalskyblock.client;

import com.mojang.logging.LogUtils;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.pipeline.WorldRenderingPhase;
import net.irisshaders.iris.pipeline.WorldRenderingPipeline;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.LevelRenderer;
import org.slf4j.Logger;

import java.lang.reflect.Field;

/**
 * @author Gaming32
 */
class IrisCompat {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Field PIPELINE;

    static {
        Field pipeline;
        try {
            pipeline = LevelRenderer.class.getDeclaredField("pipeline");
            pipeline.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            pipeline = null;
            LOGGER.error("Failed to get Iris pipeline field", e);
        }
        PIPELINE = pipeline;
    }

    static void preRender(LevelRenderer renderer) {
        if (PIPELINE == null) return;
        try {
            final WorldRenderingPipeline pipeline = Iris.getPipelineManager().preparePipeline(Iris.getCurrentDimension());
            PIPELINE.set(renderer, pipeline);
            pipeline.beginLevelRendering();
            pipeline.setPhase(WorldRenderingPhase.NONE);
        } catch (ReflectiveOperationException e) {
            LOGGER.error("Exception in preRender", e);
        }
    }

    static void postRender(LevelRenderer renderer) {
        if (PIPELINE == null) return;
        try {
            final WorldRenderingPipeline pipeline = (WorldRenderingPipeline)PIPELINE.get(renderer);
            pipeline.finalizeLevelRendering();
            PIPELINE.set(renderer, null);
        } catch (ReflectiveOperationException e) {
            LOGGER.error("Exception in postRender", e);
        }
    }

    static boolean shadersEnabled() {
        return IrisApi.getInstance().isShaderPackInUse();
    }
}