package smrjay001.csc2003s.hideandseek.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import smrjay001.csc2003s.hideandseek.HSMain;
import smrjay001.csc2003s.hideandseek.Player;
import smrjay001.csc2003s.hideandseek.util.ClockwiseComparator;

import java.util.Arrays;
import java.util.Map;

import static smrjay001.csc2003s.hideandseek.HSMain.MENU;

/**
 * This is the screen on which the game itself will run.
 */
public class ApplicationScreen implements Screen, InputProcessor {

	private HSMain parent;

	private SpriteBatch batch;

	private BitmapFont font;

	ShapeRenderer shapeRenderer, destinationRenderer;

	private Boolean game_over, debugging;

	private Player player;
	private TiledMap tiledMap;
	public OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;

	private Vector2[] corners;
	private Vector2[][] lines;

	public ApplicationScreen(HSMain parent) {
		this.parent = parent;
	}

	@Override
	public void show() {

		debugging = parent.debug;

		shapeRenderer = new ShapeRenderer();
		destinationRenderer = new ShapeRenderer();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		camera.update();

		//Implement the asset manager
		parent.assMan.queueAddImage();
		parent.assMan.assetManager.finishLoading();

		Gdx.graphics.setTitle("Game Features - SMRJAY001");

		tiledMap = new TmxMapLoader().load("assets/maps/tilesets/FeaturesMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();

		player = new Player(this, this.parent.assMan.assetManager.get("assets/characters/player1.png"),
				(TiledMapTileLayer) tiledMap.getLayers().get(0),
				parent.checking);
		player.setX(5*32);
		player.setY(8*32);
		player.setDestination(5*32, 8*32);

		game_over = false;

		font = new BitmapFont();
		font.setColor(Color.GOLDENROD);

		corners = new Vector2[] {
				new Vector2(19*32,19*32), 	//0
				new Vector2(19*32, 32), 		//1
				new Vector2(32, 32),			//2
				new Vector2(32,19*32),		//3
				new Vector2(13*32,19*32),		//4
				new Vector2(14*32,19*32),		//5
				new Vector2(14*32,13*32),		//6
				new Vector2(17*32,13*32),		//7
				new Vector2(13*32,12*32),		//8
				new Vector2(17*32,12*32),		//9
				new Vector2(3*32,11*32),		//10
				new Vector2(5*32,11*32),		//11
				new Vector2(8*32,8*32),		//12
				new Vector2(16*32, 8*32),		//13
				new Vector2(9*32, 7*32),		//14
				new Vector2(16*32, 7*32),		//15
				new Vector2(16*32,4*32),		//16
				new Vector2(9*32, 4*32),		//17
				new Vector2(3*32, 3*32),		//18
				new Vector2(5*32, 3*32),		//19
				new Vector2(8*32, 3*32),		//20
				new Vector2(15*32, 3*32),		//21
				new Vector2(15*32, 2*32),		//22
				new Vector2(16*32, 2*32),		//23
		};

		lines = new Vector2[][] {
				{corners[0], corners[1]},	//0
				{corners[1], corners[2]},	//1
				{corners[2], corners[3]},	//2
				{corners[3], corners[4]},	//3
				{corners[4], corners[8]},	//4
				{corners[8], corners[9]},	//5
				{corners[9], corners[7]},	//6
				{corners[7], corners[6]},	//7
				{corners[6], corners[5]},	//8
				{corners[5], corners[0]},	//9
				{corners[10], corners[11]},	//10
				{corners[11], corners[19]},	//11
				{corners[19], corners[18]},	//12
				{corners[18], corners[10]},	//13
				{corners[12], corners[13]},	//14
				{corners[13], corners[15]},	//15
				{corners[15], corners[14]},	//16
				{corners[14], corners[17]},	//17
				{corners[17], corners[16]},	//18
				{corners[16], corners[23]},	//19
				{corners[23], corners[22]},	//20
				{corners[22], corners[21]},	//21
				{corners[21], corners[20]},	//22
				{corners[20], corners[12]},	//23
		};
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

			//Render the player Destination
			destinationRenderer.setProjectionMatrix(camera.combined);
			destinationRenderer.setColor(Color.GREEN);
			destinationRenderer.begin(ShapeRenderer.ShapeType.Line);
			destinationRenderer.circle(player.getDestination().x+16, player.getDestination().y+16, 2);
			destinationRenderer.end();

			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			player.draw(batch);
			batch.end();

			Vector2 player_pos = new Vector2(player.getX()+16, player.getY()+16);
			Vector2[] vision_points = new Vector2[corners.length*3];
			for (int i = 0; i < vision_points.length; i++) {
				vision_points[i] = new Vector2(1000,1000);
			}

			Vector2 intersection;
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			intersection = new Vector2(0, 0); //Bigger then anything possible in game
			Vector2 offset;
			for (int i = 0; i<corners.length; i++) {
				offset = rotateRelativeVector2(player_pos, corners[i], 0.0001).setLength(1000);
				for (int j = 0; j < lines.length; j++) {
					if (Intersector.intersectSegments(player_pos, offset, lines[j][0], lines[j][1], intersection)) {
						if (intersection.dst(player_pos) < vision_points[3*i].dst(player_pos)) {
							vision_points[3 * i] = new Vector2(intersection.x, intersection.y);
						}
					}
				}
				for (int k = 0; k < lines.length; k++) {
					if (Intersector.intersectSegments(player_pos, corners[i], lines[k][0], lines[k][1], intersection)) {
						if (intersection.dst(player_pos) < vision_points[(3*i)+1].dst(player_pos)) {
							vision_points[(3*i)+1] = new Vector2(intersection.x, intersection.y);
						}
					}
				}
				offset = rotateRelativeVector2(player_pos, corners[i], -0.0001).setLength(1000);
				for (int l = 0; l < lines.length; l++) {
					if (Intersector.intersectSegments(player_pos, offset, lines[l][0], lines[l][1], intersection)) {
						if (intersection.dst(player_pos) < vision_points[(3*i)+2].dst(player_pos)) {
							vision_points[(3 * i) + 2] = new Vector2(intersection.x, intersection.y);
						}
					}
				}
			}


			Arrays.sort(vision_points, new ClockwiseComparator(player_pos));
			ShapeRenderer poly = new ShapeRenderer();
			poly.setProjectionMatrix(camera.combined);
			poly.setColor(Color.GRAY);
			poly.begin(ShapeRenderer.ShapeType.Line);
			float [] polyPoints = new float[vision_points.length*2];
			for (int i = 0; i < vision_points.length; i++) {
				polyPoints[i*2] = vision_points[i].x;
				polyPoints[i*2+1] = vision_points[i].y;
			}
			poly.polygon(polyPoints);

			poly.end();

			if (debugging) {
				for (int i = 0; i < vision_points.length; i++) {
					shapeRenderer.line(player_pos, vision_points[i]);
				}
//				for (int j = 0; j < lines.length; j++) {
//					shapeRenderer.line(lines[j][0], lines[j][1]);
//				}
//				for (int i = 0; i < vision_points.length; i+=2) {
//				shapeRenderer.polygon(vision_points.);

				shapeRenderer.setColor(Color.RED);
				shapeRenderer.end();
			}
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
		player.setDestination(position.x, position.y);
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

	/**
	 * Rotate the point about the pin by some angle
	 * @param pin the point roated around
	 * @param point the moving point
	 * @param angle the angle (in randian by which to rotate
	 * @return Vector2 containing the new point coordinates relative to the origin.
	 */
	private Vector2 rotateRelativeVector2(Vector2 pin, Vector2 point, double angle) {

		System.out.println("IN: "+point.x+" : "+point.y);
		Vector2 out =  new Vector2(
				(float) (((point.x - pin.x) * Math.cos(angle) - (point.y - pin.y) * Math.sin(angle)) + pin.x),
				(float) (((point.x - pin.x) * Math.sin(angle) + (point.y - pin.y) * Math.cos(angle)) + pin.y)
		);
		System.out.println("OUT: "+out.x+" : "+out.y);
		return out;
	}

}
