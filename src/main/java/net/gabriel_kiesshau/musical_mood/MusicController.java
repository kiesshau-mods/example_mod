package net.gabriel_kiesshau.musical_mood;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

public class MusicController
{
  public static Biome currentBiome;

  public void playerTick(Player player)
  {
    if (player instanceof LocalPlayer && player.level().isLoaded(player.blockPosition()))
    {
      BlockPos playerPos = player.blockPosition();
      Level world = player.level();

      final var isPlayerUnderground = world.dimensionType().hasSkyLight() && !world.canSeeSky(playerPos);

      detectBiome(
        world,
        playerPos,
        player,
        isPlayerUnderground
      );
    }
  }

  private void detectBiome(Level world, BlockPos playerPos, Player player, boolean isPlayerUnderground)
  {
    Holder<Biome> biomeHolder = world.getBiome(playerPos);

    if (currentBiome != biomeHolder.get())
    {
      currentBiome = biomeHolder.get();
      final String x = "Entered biome";
      MusicalMoodMod.print(x);
    }
  }
}
