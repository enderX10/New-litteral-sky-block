package io.github.gaming32.literalskyblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VoidBlock extends SkyBlock {
    public VoidBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends VoidBlock> codec() {
        return simpleCodec(VoidBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidBlockEntity(pos, state);
    }
}