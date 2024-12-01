package com.buuz135.oo_ee_a_e_a.mixin;

import com.buuz135.oo_ee_a_e_a.Oo_ee_a_e_a;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatRenderer.class)
public class CatRendererMixin {

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/animal/Cat;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V", at = @At("HEAD"), cancellable = false)
    public void setupRotationMixin(Cat entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci){
        rotateCat(entity, poseStack, bob, yBodyRot, partialTick, scale);
    }


    public void rotateCat(Cat entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale){
        if (entity.level().getGameTime() - Oo_ee_a_e_a.lastSpin < 2*20){
            poseStack.mulPose(Axis.YP.rotation(entity.level().getGameTime() % (Oo_ee_a_e_a.fast ? 90 : 180) + partialTick));
        }
    }
}
