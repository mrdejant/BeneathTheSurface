package com.ldgm.bntsf;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ldgm.bntsf.ScratchLayer.ScratchLayerListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class TicketScreen extends Group {
	public interface TicketScreenListener{
		public void back();
		public boolean next(); //check if we can get next
		public void buy();
		public void scorePrice(int multiplier);
	}
	TicketScreenListener listener;
	DialogScreen dialogScreen;
	
	ScratchTicket ticket1;
	ScratchTicket ticket2;
	
	ScratchTicket currentTicket;
	
	ImageActor back, next, buy;
	
	ImageActor bg;
	boolean isPaid = false;
	boolean isScored = false;
	int ticketPrice = 0;
	
	float scratchLimit = 0.15f;
		
	public TicketScreen(DialogScreen dialogScr, TicketScreenListener l) {
		listener = l;
		dialogScreen = dialogScr;
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.drawPixel(0, 0, 0x00000077);
		bg = new ImageActor(new TextureRegion(new Texture(pixmap)));
		
		back = new ImageActor("back.png");
		buy = new ImageActor("buy.png");
		next = new ImageActor("pass.png");
		
		ScratchLayerListener scratchlistener = new ScratchLayerListener() {
			
			@Override
			public void scratched(float percents) {
				//System.out.println("Checking "+isPaid+" "+isScored+" "+percents);
				if(!isPaid && percents > scratchLimit){
					buyTicket(true);
					shake();
					dialogScreen.displayYouAlreadyScratchedThisOne();
				}else if(isPaid && !isScored && percents > 0.75f){
					checkResult();
				}
			}
		};
		
		ticket1 = new ScratchTicket(scratchlistener);
		ticket2 = new ScratchTicket(scratchlistener);
		
		currentTicket = ticket1;
		
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!isPaid && scratchLimit < currentTicket.getScratchedRegion()){
					dialogScreen.displayYouAlreadyScratchedThisOne();
				}else if(isPaid && !isScored){
					dialogScreen.displayLetsScratchItABitMoreLine();
				}else{
					if(!isPaid && currentTicket.getScratchedRegion() > 0){
						dialogScreen.displayRandomPhewIGotAwayLine();
					}
					listener.back();
				}
			}
		});
		
		buy.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!isPaid){
					buyTicket(false);
				}else{
					dialogScreen.displayAlreadyBoughtLine();
				}
			}
		});
		
		next.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(listener.next()){
					if(!isPaid && scratchLimit < currentTicket.getScratchedRegion()){
						dialogScreen.displayYouAlreadyScratchedThisOne();
					}else if(isPaid && !isScored){
						dialogScreen.displayLetsScratchItABitMoreLine();
					}else{
						if(!isPaid && currentTicket.getScratchedRegion() > 0){
							dialogScreen.displayRandomPhewIGotAwayLine();
						}
						nextTicket(true);
					}
				}else{
					dialogScreen.displayNoMoneyLine();
				}
			}
		});
		
		addActor(bg);
		
		addActor(ticket1);
		addActor(ticket2);
		
		addActor(back);
		addActor(buy);
		addActor(next);
		
	}
	
	public void buyTicket(boolean force){
		listener.buy();
		isPaid = true;
		SoundData.purchase.play();
		currentTicket.ticketBought();
		
		if(currentTicket.getScratchedRegion() > 0.8f){
			checkResult();
		}else{
			if(!force){
				//if buy was forced another line is displayed
				dialogScreen.displayLetsScratchItLine();
			}
		}
	}
	
	public void checkResult(){
		isScored = true;
		listener.scorePrice(currentTicket.getMultiplier());
		next.setRegion("next.png");
	}
	
	public void setTicketPrice(int price){
		ticketPrice = price;
	}
	
	public void nextTicket(boolean force){
		if(!force && currentTicket.getScratchedRegion() == 0){
			return;
		}
		next.setRegion("pass.png");
		
		final ScratchTicket temp = currentTicket;
		currentTicket.addAction(sequence(Actions.moveBy(-500, 0, 0.5f), run(new Runnable() {
			public void run() {
				temp.setPosition(currentTicket.getX() + 500, temp.getY());
			}
		})));
		if(currentTicket == ticket1){
			currentTicket = ticket2;
		}else{
			currentTicket = ticket1;
		}
		currentTicket.addAction(Actions.moveBy(-500, 0, 0.5f));
		currentTicket.resetTicket();
		isPaid = false;
		isScored = false;
		
		if(ticketPrice == 1){
			scratchLimit = (float)Math.random()*0.05f+0.10f;
		}else if(ticketPrice == 2){
			scratchLimit = (float)Math.random()*0.05f+0.08f;
		}else if(ticketPrice == 5){
			scratchLimit = (float)Math.random()*0.05f+0.05f;
		}else if(ticketPrice == 10){
			scratchLimit = (float)Math.random()*0.05f+0.03f;
		}
		
		
	}
	
	private void shake(){
		addAction(sequence(Actions.moveBy(-10, 0, 0.0125f), repeat(5, sequence(Actions.moveBy(20, 0, 0.025f), Actions.moveBy(-20, 0, 0.025f))), Actions.moveBy(10, 0, 0.0125f)));
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		bg.setSize(width, height);
		
		ticket1.setSize(451, 262);
		ticket2.setSize(451, 262);
		
		back.setWidthKeepRatio(100);
		buy.setWidthKeepRatio(100);
		next.setWidthKeepRatio(100);
		
		currentTicket.setPosition((width - currentTicket.getWidth())/2f, height - currentTicket.getHeight() - 10);
		
		if(currentTicket == ticket1){
			ticket2.setPosition(currentTicket.getX() + 500, currentTicket.getY());
		}else{
			ticket1.setPosition(currentTicket.getX() + 500, currentTicket.getY());
		}
		
		back.setPosition(currentTicket.getX(), currentTicket.getY() - back.getHeight() - 10);
		buy.setPosition(back.getX() + back.getWidth()*1.1f, back.getY());
		next.setPosition(buy.getX() + buy.getWidth()*1.1f, back.getY());
		
		//dialog.setSize(200, 50);
	}
	
}
