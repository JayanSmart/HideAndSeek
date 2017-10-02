package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Player extends Sprite {

	Vector2 velocity;
	Vector2 destination;

	private float speed = 0.1f;

	TiledMapTileLayer collisionLayer;

	public Player(Texture texture, TiledMapTileLayer collisionLayer) {
		super(texture);
		this.collisionLayer = collisionLayer;
		velocity = new Vector2().setZero();
		destination = new Vector2(32, 32);
	}

	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	private void setPosition(Vector2 position) {
		setX(position.x);
		setY(position.y);
	}

	public void setDestination(float x, float y) {
		destination = new Vector2(x, y);
	}

	private void update(float delta) {

//		printCollisionLayer();

		float oldX = getX(), oldY = getY();
		Boolean collisionX;
		boolean collisionY;
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileWidth();

		Vector2 difference = new Vector2(destination.x - getX(), destination.y - getY());
		if (Math.abs(getX() - destination.x) > 1 || (Math.abs(getY() - destination.y) > 1)) {
			difference.nor();
			velocity = new Vector2(difference.x * speed, difference.y * speed);
		} else {
			velocity = new Vector2().setZero();
			setPosition(destination.x, destination.y);
		}

		//move on X
		if (Math.abs(getX() - destination.x) > 1) {
			setX(getX() + velocity.x);
		}
		//Check collision
		if (destination.x - getX() < 0) {
			collisionX = collidesLeft();
		} else {
			collisionX = collidesRight();
		}

		if (collisionX) {
			System.out.println("CollisionX");
			setX(oldX);
		}

		//move on Y
		if (Math.abs(this.getY() - destination.y) > 1) {
			setY(getY() + velocity.y);
		}
		//moving down
		if (destination.y - getY() < 0) {
			collisionY = collidesBottom();
		} else {
			collisionY = collidesTop();
		}

		if (collisionY) {
			System.out.println("CollisionY");
			setY(oldY);
		}
	}


	/**
	 * The bit checking for collision detection
	 * @return true if there is collision, else false.
	 */
	private boolean bitcheck(int mapX, int mapY) {
		boolean collision = false;
		getTexture().getTextureData().prepare();
		Pixmap pixPlay = getTexture().getTextureData().consumePixmap();

		if (getX() <= mapX * 32) {
			if (getY() <= mapY * 32) {
				//Case 1
				for (int x = ((mapX*32) - ((int) getX())); x< (int) getWidth(); x++) {
					for (int y = ((mapY*32) - ((int) getY())); y < (int) getHeight(); y++) {
						if (pixPlay.getPixel(x,y) > 0) {
							collision = true;
						}
					}
				}

				return collision;
			} else {
				//Case 2
				for (int x = ((mapX*32) - ((int) getX())); x<= (int) getWidth(); x++) {
					for (int y = 0; y < (mapY + 1) * 32 - ((int) getY()); y++) {
						if (pixPlay.getPixel(x,y) > 0) {
							collision = true;
						}
					}
				}

				return collision;
			}
		} else {
			if (getY() <= mapY * 32) {
				//Case 3
				for (int x = 0; x < ((mapX+1)*32) - ((int) getX()); x++) {
					for (int y = ((mapY*32) - ((int) getY())); y < (int) getHeight(); y++) {
						if (pixPlay.getPixel(x,y) > 0) {
							System.out.println(pixPlay.getPixel(x,y));
							collision = true;
						}
					}
				}
				return collision;
			} else {
				//Case 4
				for (int x = 0; x < ((mapX+1)*32) - ((int) getX()); x++) {
					for (int y = 0; y < (mapY + 1) * 32 - ((int) getY()); y++) {
						if (pixPlay.getPixel(x,y) > 0) {
							collision = true;
						}
					}
				}
				return collision;
			}
		}
	}


	/**
	 * This method is for testing. It prints a text interpretation of the game board to the map.
	 */
	private void printCollisionLayer() {
		String out = "";
		int playerX = (int) Math.floor(getX()/32), playerY = (int) Math.floor(getY()/32);
		int destX = (int) Math.floor(destination.x/32), destY = (int) Math.floor(destination.y)/32;

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (x == playerX && y == playerY) {
					out += "XXX, ";
				} else if (x==destX && y == destY) {
					out += "DDD, ";
				}else {
					out += collisionLayer.getCell(x, y).getTile().getId() + ", ";
				}
			}
			out +="\n";
		}
		System.out.println(out);
	}

	/**
	 * Check if a Tile on the Tiled map contains the blocked property
	 * @param x the x position on the Tiled Map
	 * @param y the y position on the Tiled Map
	 * @return true if the Tile contains the "blocked" property, else false.
	 */
	private boolean isTileBlocked(float x, float y) {
		TiledMapTileLayer.Cell cell = collisionLayer.getCell((int)(x / collisionLayer.getTileWidth()),(int)(y / collisionLayer.getTileHeight()));
		if (cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) {
			return bitcheck((int)(x / collisionLayer.getTileWidth()),(int)(y / collisionLayer.getTileHeight()));
		} else {
			return false;
		}
	}

	boolean collidesRight(){
		for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 5) {
			if (isTileBlocked(getX() + 32, getY()+step)) return true;
		}
		return false;
	}

	boolean collidesLeft() {
		for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 5) {
			if (isTileBlocked(getX(), getY()+step)) return true;
		}
		return false;
	}

	boolean collidesTop() {
		for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 5) {
			if (isTileBlocked(getX() + step, getY()+32)) return true;
		}
		return false;
	}

	boolean collidesBottom() {

		for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 5) {
			if (isTileBlocked(getX() + step, getY())) return true;
		}
		return false;
	}


}