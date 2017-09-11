package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.Game;
import smrjay001.csc2003s.hideandseek.screens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class HSMain extends Game {

	private LoadingScreen loadingScreen;
	private ConfigScreen configScreen;
	private ApplicationScreen applicationScreen;
	private Menu menuScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int SETTINGS = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	
	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

		// Get the game ready

		//Load screens
		menuScreen = new Menu(this);
		configScreen = new ConfigScreen(this);
		applicationScreen = new ApplicationScreen(this);

		System.out.println("Change");
		changeScreen(MENU);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}


	/**
	 * This manages the switching from one screen in the Game.
	 * @param screen Screen Alias passed as an int. Aliases are stored as satic final ints, for ease of readability.
	 */
	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				System.out.println("To menu");
				if(menuScreen == null) menuScreen = new Menu(this);
				this.setScreen(menuScreen);
				break;
			case SETTINGS:
				if(configScreen == null) configScreen = new ConfigScreen(this);
				this.setScreen(configScreen);
				break;
			case APPLICATION:
				if(applicationScreen == null) applicationScreen = new ApplicationScreen(this);
				this.setScreen(applicationScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}
	
	@Override
	public void dispose () {

	}
}
