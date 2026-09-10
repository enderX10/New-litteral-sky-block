package dev.latvian.mods.literalskyblock;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LiteralSkyBlock.MOD_ID)
public class LiteralSkyBlock {
    public static final String MOD_ID = "literalskyblock";

    // Zmiana na klasyczny konstruktor dla 1.20.6
    public static final ResourceLocation SKY = new ResourceLocation(MOD_ID, "sky");

    public LiteralSkyBlock(IEventBus bus) {
        LSBBlocks.REGISTER.register(bus);
        LSBItems.REGISTER.register(bus);
        LSBBlockEntities.REGISTER.register(bus);

        for (var p : ProjectionType.VALUES) {
            p.skyBlock = LSBBlocks.REGISTER.registerBlock(
                    p.getSerializedName() + "_block",
                    b -> new SkyBlock(p, b),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                            .lightLevel(state -> p == ProjectionType.SKY ? 15 : 0)
            );
            p.skyBlockItem = LSBItems.REGISTER.registerSimpleBlockItem(p.skyBlock);
        }
    }
}