package com.ldgm.bntsf;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PrizeLayer extends Group {

	int multiplier = 0;
	
	TextActor text1;
	float t1PosX, t1PosY, t1Rot;
	TextActor text2;
	float t2PosX, t2PosY, t2Rot;
	TextActor text3;
	float t3PosX, t3PosY, t3Rot;
	
	int[] allprizes = {0, 1, 2, 3, 5, 20, 100};
	
	public PrizeLayer() {
		text1 = new TextActor(false, Color.BLACK);
		text2 = new TextActor(false, Color.BLACK);
		text3 = new TextActor(false, Color.BLACK);
		
		addActor(text1);
		addActor(text2);
		addActor(text3);
		
		generatePrize();
		
		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				generatePrize();
			}
		});
	}
	
	public int getMultiplier(){
		return multiplier;
	}
	
	public void generatePrize(){
		double random = Math.random();
		if(random < 1 && random > 0.40){
			multiplier = 0;
		}else if(random <= 0.40f && random > 0.20){
			multiplier = 1;
		}else if(random <= 0.20 && random > 0.10){
			multiplier = 2;
		}else if(random <= 0.10 && random > 0.05){
			multiplier = 3;
		}else if(random <= 0.05 && random > 0.02){
			multiplier = 5;
		}else if(random <= 0.02 && random > 0.007){
			multiplier = 20;
		}else{
			multiplier = 100;
		}
		
		//System.out.println("m "+multiplier);
		
		generateTexts();
	}
	
	private void generateTexts(){
		if(multiplier == 0){
			//random text
			int random1 = (int)(Math.random()*(allprizes.length-2)) + 1;
			int random2 = (int)(Math.random()*(allprizes.length-2)) + 1;
			int random3 = (int)(Math.random()*(allprizes.length-2)) + 1;
			//System.out.println(random1+" "+random2+" "+random3);
			
			if(random1 == random2 && random2 == random3){
				int newRandom = 0;
				if(random1 == 1){
					newRandom = 2;
				}else if(random1 == allprizes.length-1){
					newRandom = allprizes.length-2;
				}else{
					newRandom = random1 + (Math.random()>0.5 ? -1 : 1);
				}
				
				switch((int)(Math.random()*3)){
				case 0:
					random1 = newRandom;
					break;
				case 1:
					random2 = newRandom;
					break;
				case 2:
					random3 = newRandom;
					break;
				}
			}
			
			if(random1 == 2 && random2 == 2){
				random3 = 5;
			}else if(random2 == 2 && random3 == 2){
				random1 = 5;
			}else if(random1 == 2 && random3 == 2){
				random2 = 5;
			}
			
			text2.setText("X"+allprizes[random1]);
			text1.setText("X"+allprizes[random2]);
			text3.setText("X"+allprizes[random3]);
		}else{
			text1.setText("X"+multiplier);
			text2.setText("X"+multiplier);
			text3.setText("X"+multiplier);
		}
		
		t1PosX = (float)(Math.random()*0.25f) + 0.05f;
		t2PosX = (float)(Math.random()*0.25f) + 0.34f;
		t3PosX = (float)(Math.random()*0.25f) + 0.64f;
		
		t1PosY = (float)Math.random()*0.8f;
		t2PosY = (float)Math.random()*0.8f;
		t3PosY = (float)Math.random()*0.8f;
		
		t1Rot = (float)Math.random()*180f;
		t2Rot = (float)Math.random()*180f;
		t3Rot = (float)Math.random()*180f;
		
		arrangeTexts();
	}
	
	private void arrangeTexts(){
		text1.setPosition(t1PosX * getWidth(), t1PosY * getHeight());
		text2.setPosition(t2PosX * getWidth(), t2PosY * getHeight());
		text3.setPosition(t3PosX * getWidth(), t3PosY * getHeight());
		text1.setRotation(t1Rot);
		text1.setRotation(t2Rot);
		text1.setRotation(t3Rot);
		
		//System.out.println("pos "+text1.getX()+" "+text1.getY());
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		arrangeTexts();
	}
	
}
