package dev.latvian.mods.literalskyblock;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;

@Mod(LiteralSkyBlock.MOD_ID)
public class LiteralSkyBlock {
    public static final String MOD_ID = "literalskyblock";
    public static final ResourceLocation SKY = new ResourceLocation(MOD_ID, "sky");

    // Główne rejestry
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    // Rejestracja Vanta Black
    public static final DeferredHolder<Block, Block> VANTA_BLACK = BLOCKS.register("vanta_black",
            () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F))
    );
    public static final DeferredHolder<Item, BlockItem> VANTA_BLACK_ITEM = ITEMS.register("vanta_black",
            () -> new BlockItem(VANTA_BLACK.get(), new Item.Properties())
    );

    // Rejestracja bloku Entity
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyBlockEntity>> SKY_BLOCK_ENTITY = BLOCK_ENTITIES.register("sky_block",
            () -> BlockEntityType.Builder.of(SkyBlockEntity::new,
                    Arrays.stream(ProjectionType.VALUES).map(p -> p.skyBlock.get()).toArray(Block[]::new)
            ).build(null)
    );

    public LiteralSkyBlock(IEventBus bus) {
        // Dynamiczna rejestracja pod-bloków (Sky Block i Void Block)
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

        // Prawidłowe zapięcie rejestrów w NeoForge
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}