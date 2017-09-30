package smrjay001.csc2003s.hideandseek.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import smrjay001.csc2003s.hideandseek.HSMain;
import smrjay001.csc2003s.hideandseek.Player;

import static smrjay001.csc2003s.hideandseek.HSMain.MENU;

/**
 * This is the screen on which the game itself will run.
 */
public class ApplicationScreen implements Screen, InputProcessor {

	private HSMain parent;

	private Batch batch;

	private BitmapFont font;


	private Boolean game_over;

	Player player;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	float moveSpeed;

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
		camera.translate(9*50, 0);
		camera.update();

		//Implement the asset manager
		parent.assMan.queueAddImage();
		parent.assMan.assetManager.finishLoading();

		tiledMap = new TmxMapLoader().load("assets/maps/EntranceHall2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();

		player = new Player((Texture) parent.assMan.assetManager.get("assets/characters/player1.png"));
		player.setX(15*48);
		moveSpeed = 0.8f;

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
			player.move();
			player.draw(batch);
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
		switch (keycode) {
			case Input.Keys.LEFT:
				camera.translate(-32, 0);
				break;
			case Input.Keys.RIGHT:
				camera.translate(32, 0);
				break;
			case Input.Keys.UP:
				camera.translate(0, 32);
				break;
			case Input.Keys.DOWN:
				camera.translate(0, -32);
				break;
			case Input.Keys.NUM_1:
				tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
				break;
			case Input.Keys.NUM_2:
				tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
				break;
			case Input.Keys.ESCAPE:
				parent.changeScreen(MENU);
				break;
		}
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
		moveTo(position);
		return true;
	}

	/**
	 * Increments the Player toward the clicked coordinates.
	 * @param clickCoordinates The enc position of the player on the screen
	 */
	private void moveTo(Vector3 clickCoordinates) {
//		if (!checkCollision(clickCoordinates)) {
			Vector2 difference = new Vector2(clickCoordinates.x - player.getX(), clickCoordinates.y - player.getY());
			if (Math.abs(player.getX() - clickCoordinates.x) > 1 || (Math.abs(player.getY() - clickCoordinates.y) > 1)) {
				difference.nor();
				player.setSpeed(new Vector2(difference.x * moveSpeed, difference.y * moveSpeed));
				player.setDestination(clickCoordinates.x, clickCoordinates.y);
				player.move();
				camera.update();
			} else {
				player.setSpeed(new Vector2().setZero());
				player.setPosition(clickCoordinates.x, clickCoordinates.y);
				camera.update();
			}
//		}
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

	/**
	 * Check if moving the player will cause collision.
	 * @param clickCoordinates The direction in which the player moves.
	 * @return true if there is collision, else false.
	 */
	boolean checkCollision(Vector2 clickCoordinates) {

		Vector2 originalPosition = new Vector2(player.getX(), player.getY());
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileWidth();
		boolean collisionX = false;
		boolean collisionY = false;


		//move on X
		//moving left
		if (clickCoordinates.x - player.getX() < 0) {
			//top left
			collisionX = collisionLayer.getCell((int) (player.getX() / tileWidth), (int) (player.getY() / tileHeight))
					.getTile().getProperties().containsKey("blocked");

			//middle left
			collisionX = collisionLayer.getCell((int) (player.getX() / tileWidth), (int) (player.getY() / tileHeight / 2))
					.getTile().getProperties().containsKey("blocked");

			//bottom left

		//moving right
		} else {
			//top right

			//middle right

			//bottom right

		}
		//move on Y
		if (clickCoordinates.y - player.getY() < 0) {
			//moving down

		} else {
			//moving up
		}

		return false;
	}

	/**
	 * Check if a player is within a grid which contains a collision object.
	 * @param player The player for which collision will be checked.
	 * @return true if the Player is within a collision grid block, else false.
	 */
	public boolean gridGollision(Player player) {
		Vector2 playerPos = player.getPosition();
		return false;
	}
}
