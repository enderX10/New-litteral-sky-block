package dev.latvian.mods.literalskyblock.integration;

import com.mojang.logging.LogUtils;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.pipeline.WorldRenderingPhase;
import net.irisshaders.iris.pipeline.WorldRenderingPipeline;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.LevelRenderer;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class IrisCompat {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static Field PIPELINE;
    private static boolean initialized = false;

    private static void init() {
        if (initialized) return;
        initialized = true;
        try {
            PIPELINE = LevelRenderer.class.getDeclaredField("pipeline");
            PIPELINE.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            PIPELINE = null;
            LOGGER.warn("Nie udało się pobrać pola 'pipeline' z LevelRenderer (Iris/Oculus nie modyfikuje renderera)");
        }
    }

    public static boolean isIrisLoaded() {
        return ModList.get().isLoaded("iris") || ModList.get().isLoaded("oculus");
    }

    public static boolean shadersEnabled() {
        return isIrisLoaded() && IrisApi.getInstance().isShaderPackInUse();
    }

    public static void preRender(LevelRenderer renderer) {
        if (!isIrisLoaded()) return;
        init();
        if (PIPELINE == null) return;

        try {
            final WorldRenderingPipeline pipeline = Iris.getPipelineManager().preparePipeline(Iris.getCurrentDimension());
            PIPELINE.set(renderer, pipeline);
            pipeline.setOverridePhase(WorldRenderingPhase.NONE);
        } catch (Exception e) {
            LOGGER.error("Błąd podczas preRender w IrisCompat", e);
        }
    }

    public static void postRender(LevelRenderer renderer) {
        if (!isIrisLoaded()) return;
        if (PIPELINE == null) return;

        try {
            PIPELINE.set(renderer, null);
        } catch (Exception e) {
            LOGGER.error("Błąd podczas postRender w IrisCompat", e);
        }
    }
}