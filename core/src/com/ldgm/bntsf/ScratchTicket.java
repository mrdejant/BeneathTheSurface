package com.ldgm.bntsf;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ldgm.bntsf.ScratchLayer.ScratchLayerListener;

public class ScratchTicket extends Group {

	ImageActor ticket;
	ScratchLayer scratchLayer;
	PrizeLayer prizeLayer;
	
	public ScratchTicket(ScratchLayerListener l) {
		ticket = new ImageActor("ticket.png");
		scratchLayer = new ScratchLayer(l);
		prizeLayer = new PrizeLayer();
		prizeLayer.generatePrize();
		
		addActor(ticket);
		addActor(prizeLayer);
		addActor(scratchLayer);
		
	}
	
	public float getScratchedRegion(){
		return scratchLayer.getClearedRegion();
	}
	
	public int getMultiplier(){
		return prizeLayer.getMultiplier();
	}
	
	public void resetTicket(){
		prizeLayer.generatePrize();
		scratchLayer.resetLayer();
	}
	
	public void ticketBought(){
		scratchLayer.setScratchSize(false);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		ticket.setSize(width, height);
		scratchLayer.setSize(width * 400/451, height * 210/262);
		scratchLayer.setPosition((getWidth() - scratchLayer.getWidth())/2f, (getHeight() - scratchLayer.getHeight())/2f);	
		
		prizeLayer.setSize(scratchLayer.getWidth(), scratchLayer.getHeight());
		prizeLayer.setPosition(scratchLayer.getX(), scratchLayer.getY());
		
	}
	
}
