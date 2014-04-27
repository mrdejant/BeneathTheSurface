package com.ldgm.bntsf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundData {

	public static Music ambience;
	public static Sound scratch;
	public static Sound purchase;
	public static Sound loose;
	public static Sound win;
	public static Sound ohYea;
	
	public static void init(){
		ambience = Gdx.audio.newMusic(Gdx.files.internal("ambience.mp3"));
		scratch = Gdx.audio.newSound(Gdx.files.internal("scratch.wav"));
		purchase = Gdx.audio.newSound(Gdx.files.internal("cash-register-purchase.wav"));
		loose = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
		win = Gdx.audio.newSound(Gdx.files.internal("win2.wav"));
		ohYea = Gdx.audio.newSound(Gdx.files.internal("oh-yea.mp3"));
	}
	
	public static void dispose(){
		ambience.dispose();
		scratch.dispose();
		purchase.dispose();
		loose.dispose();
		win.dispose();
		ohYea.dispose();
	}
	
}
