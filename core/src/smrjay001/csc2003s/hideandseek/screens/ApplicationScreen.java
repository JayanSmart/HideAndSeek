package smrjay001.csc2003s.hideandseek.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import smrjay001.csc2003s.hideandseek.HSMain;

import java.util.Random;

import static smrjay001.csc2003s.hideandseek.HSMain.MENU;

/**
 * This is the screen on which the game itself will run.
 */
public class ApplicationScreen extends ApplicationAdapter implements Screen, InputProcessor {

	private HSMain parent;

	private Batch batch;

	private BitmapFont font;


	private Boolean game_over;


	Texture img;
	Sprite sprite;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	//General triggers
	private final String TRUE = "True";
	private final String FALSE = "False";


	public ApplicationScreen(HSMain parent) {
		this.parent = parent;

	}

	@Override
	public void show() {

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		camera.update();

		tiledMap = new TmxMapLoader().load("assets/maps/EntranceHall2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();

		sprite = new Sprite(new Texture("assets/characters/char1.png"));


		game_over = false;

		font = new BitmapFont();
		font.setColor(Color.GOLDENROD);
	}

	@Override
	public void render (float delta) {
		Gdx.graphics.setWindowedMode(1200,800);
		Gdx.graphics.setResizable(true);
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (game_over) {
			batch.begin();
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				parent.changeScreen(MENU);
			}
			batch.end();
		} else {
			tiledMapRenderer.setView(camera);
			tiledMapRenderer.render();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			sprite.draw(batch);
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
		batch.dispose();
		font.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
			camera.translate(-32,0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate(32,0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,32);
		if(keycode == Input.Keys.DOWN)
			camera.translate(0,-32);
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		camera.update();
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 clickCoordinates = new Vector3(screenX-32,screenY+32,0);
		Vector3 position = camera.unproject(clickCoordinates);
		sprite.setPosition(position.x, position.y);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
