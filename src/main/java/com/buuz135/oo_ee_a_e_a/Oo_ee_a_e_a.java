package com.buuz135.oo_ee_a_e_a;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


@Mod(Oo_ee_a_e_a.MODID)
public class Oo_ee_a_e_a {

    public static final String MODID = "oo_ee_a_e_a";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static DeferredHolder<SoundEvent, SoundEvent> FAST = SOUNDS.register("oo_ee_a_e_a_fast", (rl) -> SoundEvent.createFixedRangeEvent(rl, 15));
    public static DeferredHolder<SoundEvent, SoundEvent> SLOW = SOUNDS.register("oo_ee_a_e_a_slow", (rl) -> SoundEvent.createFixedRangeEvent(rl, 15));


    public Oo_ee_a_e_a(IEventBus modEventBus, ModContainer modContainer) {
        SOUNDS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NeoForge.EVENT_BUS.register(this);

    }

    public static long lastSpin = -1;
    public static boolean fast = true;

    @SubscribeEvent
    public void clientTickEvent(ClientTickEvent.Pre event) {
        var level = Minecraft.getInstance().level;
        if (Minecraft.getInstance().isPaused() || level == null) return;
        if (lastSpin == -1) {
            lastSpin = level.getGameTime();
        }
        if (level.getGameTime() > lastSpin + Config.COOLDOWN_BETWEEN_SPINS.getAsInt()*20 && level.random.nextFloat() < 0.05) {
            lastSpin = level.getGameTime();
            fast = level.random.nextBoolean();
            var entities = level.getEntitiesOfClass(Cat.class, new AABB(Minecraft.getInstance().player.blockPosition()).inflate(32));
            entities.forEach(cat -> level.playLocalSound(cat, fast ? FAST.get() : SLOW.get(), SoundSource.NEUTRAL, 1,1));
        }
    }
}
