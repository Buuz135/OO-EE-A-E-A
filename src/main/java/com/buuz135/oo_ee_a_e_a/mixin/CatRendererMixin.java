package com.buuz135.oo_ee_a_e_a.mixin;

import com.buuz135.oo_ee_a_e_a.client.Oo_ee_a_e_aClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(CatRenderer.class)
public class CatRendererMixin {

    @Inject(method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/CatRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", at = @At("HEAD"))
    public void setupRotationMixin(CatRenderState renderState, PoseStack poseStack, float yBodyRot, float scale, CallbackInfo ci){
        if (Objects.requireNonNullElse(renderState.getRenderData(Oo_ee_a_e_aClient.SHOULD_SPIN), false)) {
            poseStack.mulPose(Axis.YP.rotation(renderState.getRenderData(Oo_ee_a_e_aClient.ROTATION_AMOUNT)));
        }
    }
}
