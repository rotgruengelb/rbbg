package net.rotgruengelb.rbbg.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.rotgruengelb.rbbg.config.ModConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsScreen.class, priority = 9000)
public class StutterfixButtonMixin {

	// For StutterFix
	@IfModLoaded("stutterfix")
	@TargetHandler(
			mixin = "net.wisecase2.stutterfix.mixin.gui.OptionsScreenMixin", name = "inject_StutterFixOption"
	)
	@Inject(method = "@MixinSquared:Handler", at = @At("HEAD"), cancellable = true)
	private void init(CallbackInfo ci) {
		if (ModConfigManager.getConfig().stutterfix) {
			ci.cancel();
		}
	}
}