package net.rotgruengelb.rbbg.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ModConfigScreen extends Screen {
	private final Screen parent;
	private final ModConfigManager.Config CONFIG;
	private boolean configurePhysicsmod;
	private boolean configureStutterfix;

	public ModConfigScreen(Screen parent) {
		super(Text.translatable("title.rbbg.config"));
		this.parent = parent;
		CONFIG = ModConfigManager.getConfig();
		this.configurePhysicsmod = CONFIG.physicsmod;
		this.configureStutterfix = CONFIG.stutterfix;
	}

	@Override
	protected void init() {
		int y = this.height / 4 + 24;

		// physicsmod
		this.addDrawableChild(new ConfigButton(this.width / 2 - 100, y, getSettingButtonTranslation("physicsmod"), button -> {
			configurePhysicsmod = !configurePhysicsmod;
			button.setMessage(getSettingButtonTranslation("physicsmod"));
		}, Supplier::get));

		// stutterfix
		this.addDrawableChild(new ConfigButton(this.width / 2 - 100, y + 24, getSettingButtonTranslation("stutterfix"), button -> {
			configureStutterfix = !configureStutterfix;
			button.setMessage(getSettingButtonTranslation("stutterfix"));
		}, Supplier::get));

		// Cancel button
		this.addDrawableChild(new ConfigButton(this.width / 2 - 100, y + 72, Text.translatable(
				"gui.cancel"), button -> this.client.setScreen(this.parent), Supplier::get, true));


		// Save button
		this.addDrawableChild(new ConfigButton(this.width / 2 + 2, y + 72, Text.translatable("gui.done"), button -> {
			CONFIG.physicsmod = this.configurePhysicsmod;
			CONFIG.stutterfix = this.configureStutterfix;
			ModConfigManager.saveConfig();
			this.client.setScreen(this.parent);
		}, Supplier::get, false));
	}

	private Text getSettingButtonTranslation(String s) {
		Text booleanTranslation = getBooleanTranslation(switch (s) {
			case "physicsmod" -> configurePhysicsmod;
			case "stutterfix" -> configureStutterfix;
			default -> throw new IllegalStateException("Unexpected value: " + s);
		});
		return Text.translatable("config.rbbg.setting." + s, booleanTranslation);
	}

	private Text getBooleanTranslation(boolean value) {
		if (value) {
			return Text.translatable("config.rbbg.hide");
		}
		return Text.translatable("config.rbbg.show");
	}


	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
		super.render(context, mouseX, mouseY, delta);
	}
}
