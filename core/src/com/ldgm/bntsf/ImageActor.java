package com.ldgm.bntsf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ImageActor extends Actor {

	TextureRegion region;
	
	public ImageActor(){
		this((TextureRegion)null);
	}
	
	public ImageActor(String file){
		this(new TextureRegion(new Texture(file)));
	}
	
	public ImageActor(TextureRegion reg){
		region = reg;
		setSize(reg.getRegionWidth(), reg.getRegionHeight());
	}
	
	public void setRegion(String file){
		setRegion(new TextureRegion(new Texture(file)));
	}
	
	public void setRegion(TextureRegion reg){
		region = reg;
	}
	
	private float getRegionRatio(){
		return (float)region.getRegionWidth()/(float)region.getRegionHeight();
	}
	
	public void setWidthKeepRatio(float width){
		setSize(width, width/getRegionRatio());
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		setOrigin(getWidth()/2f, getHeight()/2f);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
