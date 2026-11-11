package dev.latvian.mods.literalskyblock;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LiteralSkyBlock.MOD_ID)
public class LiteralSkyBlock {
    public static final String MOD_ID = "literalskyblock";
    public static final ResourceLocation SKY = ResourceLocation.fromNamespaceAndPath(MOD_ID, "sky");

    public LiteralSkyBlock() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        LSBBlocks.REGISTER.register(bus);
        LSBItems.REGISTER.register(bus);
        LSBBlockEntities.REGISTER.register(bus);

        for (var p : ProjectionType.VALUES) {
            p.skyBlock = LSBBlocks.REGISTER.register(
                    p.getSerializedName() + "_block",
                    () -> new SkyBlock(p, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                            .lightLevel(state -> p == ProjectionType.SKY ? 15 : 0))
            );
            p.skyBlockItem = LSBItems.REGISTER.register(
                    p.getSerializedName() + "_block",
                    () -> new BlockItem(p.skyBlock.get(), new Item.Properties())
            );
        }
    }
}