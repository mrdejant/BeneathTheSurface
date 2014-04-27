package com.ldgm.bntsf;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CounterScreen extends Group {

	public interface CounterScreenListener{
		public void ticketClicked(int num);
	}
	CounterScreenListener listener;
	
	ImageActor bg;
	
	TicketButton button1;
	TicketButton button2;
	TicketButton button5;
	TicketButton button10;
	
	public CounterScreen(CounterScreenListener l) {
		listener = l;
		
		bg = new ImageActor("counter.png");
		button1 = new TicketButton("oneDL-02.png");
		button2 = new TicketButton("twoDL-02.png");
		button5 = new TicketButton("fiveDL-02.png");
		button10 = new TicketButton("tenDL-02.png");
		
		button1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				listener.ticketClicked(1);
			}
		});
		
		button2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				listener.ticketClicked(2);
			}
		});
		
		button5.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				listener.ticketClicked(5);
			}
		});
		
		button10.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				listener.ticketClicked(10);
			}
		});
		
		
		
		addActor(bg);
		addActor(button1);
		addActor(button2);
		addActor(button5);
		addActor(button10);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		bg.setSize(width, height);
		
		float ticketSize = 100;
		
		button1.setPosition(10, 130 - button1.getHeight()*1.1f);
		
		button2.setPosition(button1.getX() + ticketSize*1.1f, button1.getY());
		
		button5.setPosition(button2.getX() + ticketSize*1.1f, button1.getY());
		
		button10.setPosition(button5.getX() + ticketSize*1.1f, button1.getY());
		
		
	}
	
}
