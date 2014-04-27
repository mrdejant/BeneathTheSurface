package com.ldgm.bntsf;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ScratchLayer extends Group {
	public interface ScratchLayerListener{
		public void scratched(float percents);
	}
	ScratchLayerListener listener;
	
	Sprite sprite;
	Pixmap pixmap;
	Texture texture;
	int scratchSize = 10;
	
	float lastx, lasty;
	boolean isDrawing = false;
	
	public ScratchLayer(ScratchLayerListener l) {
		listener = l;
		
		sprite = new Sprite();
		pixmap = new Pixmap(Gdx.files.internal("scratch.png"));
		
		texture = new Texture(pixmap);
		sprite.setRegion(texture);
		
		getClearedRegion();
		setScratchSize(true);
		
		setSize(pixmap.getWidth(), pixmap.getHeight());
		
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Pixmap.setBlending(Blending.None);
				pixmap.setColor(Color.rgba8888(2, 2, 2, 0));
				pixmap.fillCircle((int)x, (int)getHeight() - (int)y, scratchSize);
				texture.draw(pixmap, 0, 0);
				listener.scratched(getClearedRegion());
				
				lastx = x;
				lasty = y;
				isDrawing = true;
				SoundData.scratch.loop();
				
				return true;
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
				if(!isDrawing){
					SoundData.scratch.stop();
					return;
				}
				//System.out.println(""+x+" "+y);
				Pixmap.setBlending(Blending.None);
				pixmap.setColor(Color.rgba8888(2, 2, 2, 0));
				if(scratchSize <= 5){
					int cx, cy;
					float distx = x - lastx;
					float disty = y - lasty;
					for(float i = 0; i<1; i+= 0.05f){
						cx = (int)(lastx + distx*i);
						cy = (int)(lasty + disty*i);
						pixmap.fillCircle((int)cx, (int)getHeight() - (int)cy, scratchSize);
					}
				}else{
					pixmap.fillCircle((int)x, (int)getHeight() - (int)y, scratchSize);
				}
				texture.draw(pixmap, 0, 0);
				//System.out.println(getClearedRegion());
				listener.scratched(getClearedRegion());
				
				lastx = x;
				lasty = y;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				SoundData.scratch.stop();
			}
			
		});
	}
	
	public void resetLayer(){
		pixmap = new Pixmap(Gdx.files.internal("scratch.png"));
		texture.draw(pixmap, 0, 0);
		setScratchSize(true);
	}
	
	public void setScratchSize(boolean small){
		if(small)
			scratchSize = 1;
		else
			scratchSize = 40;
		isDrawing = false;
	}
	
	public float getClearedRegion(){
		ByteBuffer bb = pixmap.getPixels();
		int pos = bb.position();
		bb.position(0);
		int all = bb.limit()/4;
		int cleared = 0;
		while(bb.hasRemaining()){
			int color = bb.getInt();
			if((color & 0x000000ff) == 0){
				cleared++;
			}
		}
		bb.position(pos);
		
		return (float)cleared/all;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		sprite.draw(batch);
		sprite.draw(batch);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		sprite.setSize(width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		sprite.setPosition(x, y);
	}
	
}
