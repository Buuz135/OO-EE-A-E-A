package com.buuz135.oo_ee_a_e_a.client;

import com.buuz135.oo_ee_a_e_a.Config;
import com.buuz135.oo_ee_a_e_a.Oo_ee_a_e_a;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Oo_ee_a_e_a.MODID, dist = Dist.CLIENT)
public class Oo_ee_a_e_aClient {

    public static long lastSpin = -1;
    public static boolean fast = true;
    public static final ContextKey<Boolean> SHOULD_SPIN = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(Oo_ee_a_e_a.MODID, "should_spin"));
    public static final ContextKey<Float> ROTATION_AMOUNT = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(Oo_ee_a_e_a.MODID, "rotation_amount"));

    public Oo_ee_a_e_aClient(IEventBus modBus) {
        modBus.addListener(this::registerRenderStateModifier);
        NeoForge.EVENT_BUS.addListener(this::clientTickEvent);
    }

    private void registerRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(CatRenderer.class, (cat, renderState) -> {
            renderState.setRenderData(SHOULD_SPIN, cat.level().getGameTime() - lastSpin < 2*20);
            renderState.setRenderData(ROTATION_AMOUNT, cat.level().getGameTime() % (fast ? 90 : 180) + renderState.partialTick);
        });
    }

    private void clientTickEvent(ClientTickEvent.Pre event) {
        var level = Minecraft.getInstance().level;
        if (Minecraft.getInstance().isPaused() || level == null) return;
        if (lastSpin == -1) {
            lastSpin = level.getGameTime();
        }
        if (level.getGameTime() > lastSpin + Config.COOLDOWN_BETWEEN_SPINS.getAsInt() * 20L && level.random.nextFloat() < 0.05) {
            lastSpin = level.getGameTime();
            fast = level.random.nextBoolean();
            var entities = level.getEntitiesOfClass(Cat.class, new AABB(Minecraft.getInstance().player.blockPosition()).inflate(32));
            entities.forEach(cat -> level.playLocalSound(cat, fast ? Oo_ee_a_e_a.FAST.get() : Oo_ee_a_e_a.SLOW.get(), SoundSource.NEUTRAL, 1,1));
        }
    }
}
