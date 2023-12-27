package net.gabriel_kiesshau.musical_mood;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MusicalMoodMod.MOD_ID)
public class MusicalMoodMod
{
  public static final String MOD_ID = "musical_mood";
  private static final Logger LOGGER = LogUtils.getLogger();
  public static MusicController musicController = new MusicController();

  public static void print(String message) {
    LOGGER.info(message);
  }

  public MusicalMoodMod()
  {
    if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
    {
      return;
    }

    ModLoadingContext.get().registerExtensionPoint(
      IExtensionPoint.DisplayTest.class,
      () -> new IExtensionPoint.DisplayTest(
        () -> NetworkConstants.IGNORESERVERONLY,
        (a, b) -> true
      )
    );

    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    modEventBus.addListener(this::commonSetup);

    MinecraftForge.EVENT_BUS.register(this);

    ModLoadingContext.get().registerConfig(
      ModConfig.Type.COMMON,
      Config.SPEC
    );
  }

  private void commonSetup(final FMLCommonSetupEvent event)
  {
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event)
  {
    LOGGER.info("Starting up musical mood...");
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents
  {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP");
      LOGGER.info(
        "MINECRAFT NAME >> {}",
        Minecraft.getInstance().getUser().getName()
      );
    }
  }
}
