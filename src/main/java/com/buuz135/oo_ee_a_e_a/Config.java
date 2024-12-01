package com.buuz135.oo_ee_a_e_a;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue COOLDOWN_BETWEEN_SPINS = BUILDER.comment("In Seconds").defineInRange("cooldownBetweenSpins", 50, 1, Integer.MAX_VALUE);


    static final ModConfigSpec SPEC = BUILDER.build();

}
