package net.rotgruengelb.rbbg.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Widget;
import net.rotgruengelb.rbbg.config.ModConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsScreen.class, priority = 9000)
public class PhysicsmodButtonMixin {

	// For 1.20 Versions of PhysicsMod before 3.0.14
	@IfModLoaded(value = "physicsmod", maxVersion = "3.0.14")
	@TargetHandler(
			mixin = "net.diebuddies.mixins.MixinOptionsScreen", name = "init"
	)
	@Inject(method = "@MixinSquared:Handler", at = @At("HEAD"), cancellable = true)
	private void init(CallbackInfo ci) {
		if (ModConfigManager.getConfig().physicsmod) {
			ci.cancel();
		}
	}

	// For 1.21 Versions of PhysicsMod after 3.0.14
	@IfModLoaded(value = "physicsmod", minVersion = "3.0.16")
	@TargetHandler(
			mixin = "net.diebuddies.mixins.MixinOptionsScreen", name = "physicsmod$button"
	)
	@WrapWithCondition(
			method = "@MixinSquared:Handler", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;I)Lnet/minecraft/client/gui/widget/Widget;"
	)
	)
	private boolean physicsmod$button(GridWidget.Adder rowHelper, Widget physicsButton, int arg2) {
		return !ModConfigManager.getConfig().physicsmod;
	}
}