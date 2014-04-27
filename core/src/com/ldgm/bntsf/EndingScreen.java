package com.ldgm.bntsf;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndingScreen extends Group {

	public interface EndingScreenListener{
		public void retry();
	}
	EndingScreenListener listener;
	
	TextActor endMessage;
	ImageActor bg;
	ImageActor retry;
	
	public EndingScreen(EndingScreenListener l){
		listener = l;
		
		endMessage = new TextActor();
		endMessage.setAlignment(Align.center);
		endMessage.setWrap(true);
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.drawPixel(0, 0, 0x000000BB);
		bg = new ImageActor(new TextureRegion(new Texture(pixmap)));
		retry = new ImageActor("retry.png");
		
		retry.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				listener.retry();
			}
		});
		
		addActor(bg);
		addActor(endMessage);
		addActor(retry);
	}
	
	public void setScore(int score){
		if(score == 0){
			endMessage.setText("YOU LOST ALL YOUR MONEY. TRY AGAIN!\n"+score+"$");
		}else if(score <= 2){
			endMessage.setText("NOT MUCH OF A SCORE,BUT YOU DID NOT LOST ALL YOUR MONEY!\n"+score+"$");
		}else if(score <= 10){
			endMessage.setText("YOU ARE GETTING PREETY GOOD AT THIS!\n"+score+"$");
		}else if(score <= 120){
			endMessage.setText("WOW, YOU SHOULD QUIT YOUR JOB!\n"+score+"$");
		}else{
			endMessage.setText("DAMN! THAT'S A LOT OF MONEY!\nCONGRATULATIONS!\n"+score+"$");
		}
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		bg.setSize(width, height);
		endMessage.setSize(width, height);
		retry.setPosition((width - retry.getWidth())/2f, 10);
	}
	
}
