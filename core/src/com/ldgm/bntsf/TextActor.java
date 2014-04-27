package com.ldgm.bntsf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextActor extends Label {
	public TextActor() {
		this(false, Color.YELLOW);
	}
	
	public TextActor(boolean small, Color color) {
		super("text", new LabelStyle(small ? new BitmapFont(Gdx.files.internal("font_16.fnt")) : new BitmapFont(Gdx.files.internal("font.fnt")), color));
	}
}
