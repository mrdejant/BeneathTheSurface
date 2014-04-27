package com.ldgm.bntsf;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class DialogScreen extends Group {
	
	PlayerSpeechDialog player, seller;
	String[] startStr = {"LET'S SCRATCH SOME TICKETS AND WIN SOME MONEY!",
			"I FEEL REALY LUCKY TODAY!",
			"DAMN, I NEED SOMETHING TO SCRATCH!",
			"THIS WILL BE MY LUCKY DAY!",
			"SUCH A NICE DAY TO SCRATCH SOME TICKETS!"};
	float[] startDel = {3f,
			3f,
			3f,
			3f,
			3f};
	
	String[] cantSeeStr = {"DAMN, I CAN'T TELL IF THIS ONE IS MY LUCKY ONE.",
			"I AM NOT SURE IF THIS ONE IS THE RIGHT ONE...",
			"GOSH, HOW CAN I WIN IF I CAN'T SEE WHAT IS BEHIND?",
			"I KNOW THAT I SHOULDN'T DO THIS, BUT...",
			"IF ONLY I KNEW WHAT IS BENEATH THE SURFACE."};
	float[] cantSeeDel = {3f,
			3f,
			3f,
			3f,
			3f};
	String cantSee2Str = "MAYBE IF I SCRATCH IT JUST A LITTLE, NO ONE WILL EVER NOTICE...";
	float cantSee2Del = 5f;
	
	String dontScratchItStr = "DON'T SCRATCH IT IF YOU DIDN'T PAY FOR IT! I AM WATCHING YOU!";
	float dontScratchItDel = 4f;
	
	String youAlreadyScratched1Str = "HEY! YOU ALLREADY SCRATCHED THIS ONE! YOU NEED TO PAY FOR IT!";
	float youAlreadyScratched1Del = 3f;
	String youAlreadyScratched2Str = "DAMN! I GOT CAUGHT...";
	float youAlreadyScratched2Del = 3f;
	
	String[] phewIGotAwayStr = {"PHEW, SHE ALMOST GOT ME.",
			"MAN! I ALMOST GOT CAUGHT!",
			"I CAN'T BELIEVE THAT SHE DIDN'T NOTICE THAT SCRATCH!"};
	float[] phewIGotAwayDel = {3f,
			3f,
			3f};
	
	String letsScratchItStr = "LET'S SCRATCH IT!";
	float letsScratchItDel = 3f;
	
	String alreadyBoughtStr = "I ALREADY BOUGHT THIS ONE.";
	float alreadyBoughtDel = 3f;
	
	String letsScratchItABitMoreStr = "IF I PAID FOR IT, I SHOULD SCRATCH IT!";
	float letsScratchItABitMoreDel = 3f;
	
	String noMoneyStr = "I DON'T HAVE ENOUGH MONEY...";
	float noMoneyDel = 3f;
	
	String iWonDolarsStr = "OH YEA! I KNEW IT! I WON %d$!";
	float iWonDolarsDel = 3f;
	
	String damnLostStr = "DAMN! THERE GOES MY MONEY! F***";
	float damnLostDel = 5f;
	
	public DialogScreen() {
		player = new PlayerSpeechDialog(true);
		seller = new PlayerSpeechDialog(false);
		
		//addActor(player);
		//addActor(seller);
		
		setTouchable(Touchable.disabled);
	}
	
	public void displayRandomStartLine(){
		int random = (int)(Math.random() * startStr.length);
		startDialog(true, startStr[random], startDel[random], null);
	}
	
	public void displayRandomICantSeeLine(){
		startDialog(false, dontScratchItStr, dontScratchItDel, new Runnable(){
			@Override
			public void run() {
				int random = (int)(Math.random() * cantSeeStr.length);
				startDialog(true, cantSeeStr[random], cantSeeDel[random], new Runnable() {
					
					@Override
					public void run() {
						startDialog(true, cantSee2Str, cantSee2Del, null);
					}
				});
			}
		});
	}
	
	public void displayYouAlreadyScratchedThisOne(){
		startDialog(false, youAlreadyScratched1Str, youAlreadyScratched1Del, new Runnable(){
			@Override
			public void run() {
				startDialog(true, youAlreadyScratched2Str, youAlreadyScratched2Del, null);
			}
		});
	}
	
	public void displayRandomPhewIGotAwayLine(){
		int random = (int)(Math.random() * phewIGotAwayStr.length);
		startDialog(true, phewIGotAwayStr[random], phewIGotAwayDel[random], null);
	}
	
	public void displayDontScratchItLine(){
		startDialog(true, dontScratchItStr, dontScratchItDel, null);
	}
	
	public void displayLetsScratchItLine(){
		startDialog(true, letsScratchItStr, letsScratchItDel, null);
	}
	
	public void displayLetsScratchItABitMoreLine(){
		startDialog(true, letsScratchItABitMoreStr, letsScratchItABitMoreDel, null);
	}
	
	public void displayNoMoneyLine(){
		startDialog(true, noMoneyStr, noMoneyDel, null);
	}
	
	public void displayIWonDolarsLine(int money){
		startDialog(true, String.format(iWonDolarsStr, money), iWonDolarsDel, null);
	}
	
	public void displayDamnILostLine(){
		startDialog(true, damnLostStr, damnLostDel, null);
	}
	
	public void displayAlreadyBoughtLine(){
		startDialog(true, alreadyBoughtStr, alreadyBoughtDel, null);
	}
	
	public void startDialog(boolean isPlayer, String a, float d1, final Runnable runAtTheEnd){
		clearActions();
		removeActor(player);
		removeActor(seller);
		final PlayerSpeechDialog dlg;
		if(isPlayer){
			dlg = player;
		}else{
			dlg = seller;
		}
		dlg.setText(a);
		addActor(dlg);
		
		addAction(sequence(delay(d1), run(new Runnable(){
			@Override
			public void run() {
				removeActor(dlg);
				dlg.setText("");
				if(runAtTheEnd != null){
					Gdx.app.postRunnable(runAtTheEnd);
				}
			}
		})));
		
	}
	
	
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		player.setPosition(0, 0);
		seller.setPosition(width - seller.getWidth(), height - seller.getHeight());
	}
	
}
