package com.ldgm.bntsf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ldgm.bntsf.CounterScreen.CounterScreenListener;
import com.ldgm.bntsf.EndingScreen.EndingScreenListener;
import com.ldgm.bntsf.TicketScreen.TicketScreenListener;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	Stage stage;
	ScreenViewport vp;
	
	CounterScreen counterScreen;
	TicketScreen ticketScreen;
	DialogScreen dialogScreen;
	EndingScreen endingScreen;
	
	int cash = 2;
	
	int selectedPrice = 0;
	
	int ticketsBought = 0;
	int maxTickets = 5;
	
	TextActor cashLabel;
	TextActor ticketsLabel;
	
	@Override
	public void create () {
		SoundData.init();
		SoundData.ambience.setLooping(true);
		SoundData.ambience.setVolume(0.25f);
		SoundData.ambience.play();
		batch = new SpriteBatch();
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		vp = new ScreenViewport();
				
		counterScreen = new CounterScreen(new CounterScreenListener() {
			
			@Override
			public void ticketClicked(int num) {
				if(num > cash){
					dialogScreen.displayNoMoneyLine();
					return;
				}
				stage.addActor(ticketScreen);
				ticketScreen.setTicketPrice(num);
				ticketScreen.nextTicket(false);
				selectedPrice = num;
				correctScreenOrder();
				dialogScreen.displayRandomICantSeeLine();
			}
		});
		dialogScreen = new DialogScreen();
		ticketScreen = new TicketScreen(dialogScreen, new TicketScreenListener() {
			
			@Override
			public boolean next() {
				if(cash >= selectedPrice){
					return true;
				}
				return false;
			}
			
			@Override
			public void buy() {
				changeCash(-selectedPrice);
				correctScreenOrder();
				ticketsBought++;
				updateLabels();
			}
			
			@Override
			public void back() {
				ticketScreen.remove();
				correctScreenOrder();
			}

			@Override
			public void scorePrice(int multiplier) {
				changeCash(selectedPrice*multiplier);
				if(multiplier > 0){
					dialogScreen.displayIWonDolarsLine(selectedPrice*multiplier);
					SoundData.ohYea.play();
				}else{
					dialogScreen.displayDamnILostLine();
					SoundData.loose.play();
				}
				
				if(cash <= 0){
					//loose!!
					showEndingScreen();
					SoundData.loose.play();
				}else if(ticketsBought >= maxTickets){
					//win. show score
					showEndingScreen();
					SoundData.win.play();
				}
			}
		});
		endingScreen = new EndingScreen(new EndingScreenListener() {
			@Override
			public void retry() {
				restartEverything();
			}
		});
		
		cashLabel = new TextActor();
		cashLabel.setAlignment(Align.right);
		ticketsLabel = new TextActor();
		ticketsLabel.setAlignment(Align.right);
		
		stage.addActor(counterScreen);
		stage.addActor(ticketsLabel);
		
		
		updateLabels();
		
		correctScreenOrder();
		
		dialogScreen.displayRandomStartLine();
	}

	private void restartEverything(){
		cash = 2;
		selectedPrice = 0;
		ticketsBought = 0;
		endingScreen.remove();
		ticketScreen.remove();
		
		updateLabels();
		correctScreenOrder();
	}
	
	private void correctScreenOrder(){
		stage.addActor(cashLabel);
		stage.addActor(ticketsLabel);
		stage.addActor(dialogScreen);
	}
	
	private void changeCash(int diff){
		cash += diff;
		updateLabels();
	}
	
	public void showEndingScreen(){
		stage.addActor(endingScreen);
		endingScreen.setScore(cash);
		endingScreen.addAction(Actions.alpha(0));
		endingScreen.addAction(Actions.alpha(1, 2));
	}
	
	private void updateLabels(){
		cashLabel.setText("CASH:"+cash+"$");
		ticketsLabel.setText("TICKETS:"+ticketsBought+"/"+maxTickets);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

	}
	
	public void resize (int width, int height) {
		vp.update(width, height, true);
        stage.setViewport(vp);
        counterScreen.setSize(width, height);
        ticketScreen.setSize(width, height);
        dialogScreen.setSize(width, height);
        endingScreen.setSize(width, height);
        
        cashLabel.setSize(width, 30);
        cashLabel.setPosition(0, 10);
        ticketsLabel.setSize(cashLabel.getWidth(), cashLabel.getHeight());
        ticketsLabel.setPosition(0, cashLabel.getTop());
	}
	
	public void dispose() {
        stage.dispose();
        SoundData.dispose();
	}
}
