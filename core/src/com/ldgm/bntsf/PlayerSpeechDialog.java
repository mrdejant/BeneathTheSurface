package com.ldgm.bntsf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class PlayerSpeechDialog extends Group {

	TextActor text;
	ImageActor background;
	
	boolean isBottom;
	
	public PlayerSpeechDialog(boolean bottom) {
		isBottom = bottom;
		
		if(isBottom){
			background = new ImageActor("player_talk.png");
		}else{
			background = new ImageActor("seller_talk.png");
		}
		text = new TextActor(true, Color.BLACK);
		text.setWrap(true);
		addActor(background);
		addActor(text);
		
		//text.setText("MABEY I SHOULD SCRACTH JUST A LITTLE BIT OFF... NO ONE WILL EVER KNOW");
		setSize(background.getWidth(), background.getHeight());
	}
	
	public void setText(String t){
		text.setText(t);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		background.setSize(width, height);
		text.setSize(width-10, height - 25);
		if(isBottom){
			text.setPosition(5, 20);
		}else{
			text.setPosition(5, 10);
		}
	}
	
}
