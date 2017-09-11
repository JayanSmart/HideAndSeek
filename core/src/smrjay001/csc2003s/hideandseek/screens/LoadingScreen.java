package smrjay001.csc2003s.hideandseek.screens;

import com.badlogic.gdx.Screen;
import smrjay001.csc2003s.hideandseek.HSMain;

public class LoadingScreen implements Screen {
	private HSMain parent;

	public LoadingScreen(HSMain parent) {
		this.parent = parent;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		parent.changeScreen(HSMain.MENU);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
