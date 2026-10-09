package dev.latvian.mods.literalskyblock;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

@Mod(LiteralSkyBlock.MOD_ID)
public class LiteralSkyBlock {
    public static final String MOD_ID = "literalskyblock";
    public static final ResourceLocation SKY = new ResourceLocation(MOD_ID, "sky");

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<Block> VANTA_BLACK = BLOCKS.register("vanta_black",
            () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F))
    );
    public static final RegistryObject<Item> VANTA_BLACK_ITEM = ITEMS.register("vanta_black",
            () -> new BlockItem(VANTA_BLACK.get(), new Item.Properties())
    );

    public static final RegistryObject<BlockEntityType<SkyBlockEntity>> SKY_BLOCK_ENTITY = BLOCK_ENTITIES.register("sky_block",
            () -> BlockEntityType.Builder.of(SkyBlockEntity::new,
                    Arrays.stream(ProjectionType.VALUES).map(p -> p.skyBlock.get()).toArray(Block[]::new)
            ).build(null)
    );

    public LiteralSkyBlock() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        for (var p : ProjectionType.VALUES) {
            p.skyBlock = BLOCKS.register(p.getSerializedName() + "_block",
                    () -> new SkyBlock(p, BlockBehaviour.Properties.of()
                            .strength(1.5F, 6.0F)
                            .lightLevel(state -> p == ProjectionType.SKY ? 15 : 0))
            );
            p.skyBlockItem = ITEMS.register(p.getSerializedName() + "_block",
                    () -> new BlockItem(p.skyBlock.get(), new Item.Properties())
            );
        }

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}