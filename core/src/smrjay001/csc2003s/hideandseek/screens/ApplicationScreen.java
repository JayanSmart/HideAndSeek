package smrjay001.csc2003s.hideandseek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import smrjay001.csc2003s.hideandseek.HSMain;

import java.util.Random;

import static smrjay001.csc2003s.hideandseek.HSMain.MENU;

/**
 * This is the screen on which the game itself will run.
 */
public class ApplicationScreen implements Screen {

	private HSMain parent;

	private SpriteBatch batch;
	private Texture grey_block;

	private Random random;
	private Timer.Task moveDownTask;

	private int game_width, game_length;
	private boolean game_over;

	private BitmapFont font;
	private BitmapFont uncompletedFont;
	private BitmapFont completedFont;


	//General triggers
	private final String TRUE = "True";
	private final String FALSE = "False";


	public ApplicationScreen(HSMain parent) {
		this.parent = parent;

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		grey_block = new Texture("assets/grey_block.png");
		random = new Random();

		game_over = false;

		font = new BitmapFont();
		font.setColor(Color.GOLDENROD);
		uncompletedFont = new BitmapFont();
		uncompletedFont.setColor(Color.GRAY);
		completedFont = new BitmapFont();
		completedFont.setColor(Color.GOLDENROD);

	}

	@Override
	public void render (float delta) {
		Gdx.graphics.setWindowedMode(1200,800);
		Gdx.graphics.setResizable(true);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (game_over) {
			batch.begin();
			font.draw(batch, "GAME OVER", 10*game_width, 11*game_length);
			font.draw(batch, "Press ESC to exit", 10*game_width, 10*game_length);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				parent.changeScreen(MENU);
			}
			batch.end();
		} else {

			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				dispose();
				parent.changeScreen(MENU);
			}


			batch.begin();


			font.setColor(Color.GOLD);
			batch.end();
		}
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
	public void dispose () {
		moveDownTask.cancel();
		batch.dispose();
		font.dispose();
		uncompletedFont.dispose();
		completedFont.dispose();

	}
}
