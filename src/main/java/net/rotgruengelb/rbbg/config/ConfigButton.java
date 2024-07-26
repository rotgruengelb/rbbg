package net.rotgruengelb.rbbg.config;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigButton extends ButtonWidget {
	protected ConfigButton(int x, int y, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
		super(x, y, 196, 20, message, onPress, narrationSupplier);
	}

	protected ConfigButton(int x, int y, Text message, PressAction onPress,
			NarrationSupplier narrationSupplier, boolean isCancelButton) {
		super(x, y, 98, 20, message, onPress, narrationSupplier);
	}
}
