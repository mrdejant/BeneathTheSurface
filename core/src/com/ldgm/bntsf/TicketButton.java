package com.ldgm.bntsf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TicketButton extends Group {

	ImageActor actor;
	
	public TicketButton(String img){
		actor = new ImageActor(new TextureRegion(new Texture(img)));
		addActor(actor);
	}
	
}
