package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL30;
import smrjay001.csc2003s.hideandseek.screens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class HSMain extends Game {

	private LoadingScreen loadingScreen;
	private ConfigScreen configScreen;
	private ApplicationScreen applicationScreen;
	private Menu menuScreen;
	private EndScreen endScreen;
	public GameAssetManager assMan = new GameAssetManager();

	public boolean checking, debug;

	public final static int MENU = 0;
	public final static int SETTINGS = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public final static int CHECKING = 4;

	
	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

		// Get the game ready

		//Load screens
		menuScreen = new Menu(this);
		configScreen = new ConfigScreen(this);
		applicationScreen = new ApplicationScreen(this);

		changeScreen(MENU);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		this.screen.render(1);
	}


	/**
	 * This manages the switching from one screen in the Game.
	 * @param screen Screen Alias passed as an int. Aliases are stored as static final ints, for ease of readability.
	 */
	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if(menuScreen == null) menuScreen = new Menu(this);
				checking = false;
				debug = false;
				this.setScreen(menuScreen);
				break;
			case SETTINGS:
				if(configScreen == null) configScreen = new ConfigScreen(this);
				checking = false;
				debug = false;
				this.setScreen(configScreen);
				break;
			case APPLICATION:
				applicationScreen = new ApplicationScreen(this);
				checking = true;
				debug = false;
				this.setScreen(applicationScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				checking = false;
				debug = false;
				this.setScreen(endScreen);
				break;
			case CHECKING:
				applicationScreen = new ApplicationScreen(this);
				checking = true;
				debug = true;
				this.setScreen(applicationScreen);
				break;
		}
	}

	public void swapChecking() {
		checking = !checking;
	}
	
	@Override
	public void dispose () {
		loadingScreen.dispose();
		configScreen.dispose();
		applicationScreen.dispose();
		super.dispose();
	}
}
