package net.tobywig.slayers.capability.killAmount;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerKillsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerKills> PLAYER_KILLS = CapabilityManager.get(new CapabilityToken<PlayerKills>() { });

    private PlayerKills kills = null;
    private final LazyOptional<PlayerKills> optional = LazyOptional.of(this::createPlayerKillValues);

    private PlayerKills createPlayerKillValues() {
        if (this.kills == null) {
            this.kills = new PlayerKills();
        }
        return this.kills;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_KILLS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerKillValues().saveNBTData(nbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerKillValues().loadNBTData(nbt);
    }
}
