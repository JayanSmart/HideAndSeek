package smrjay001.csc2003s.hideandseek.util;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import smrjay001.csc2003s.hideandseek.Collectible;
import smrjay001.csc2003s.hideandseek.Player;
import smrjay001.csc2003s.hideandseek.screens.ApplicationScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AIManager extends Player {

	ArrayList<Collectible> visibleItems;



	Random random;
	private int score;
	float[] vision;

	/**
	 * @param parent The Application
	 * @param texture The Displayed Image
	 * @param collisionLayer MapMetaData
	 * @param position The initial position of the Bot
	 */
	public AIManager(ApplicationScreen parent, Texture texture, TiledMapTileLayer collisionLayer, Vector2 position) {
		super(parent, texture, collisionLayer);
		visibleItems = new ArrayList<>(0);
		random = new Random();
		setX(position.x);
		setY(position.y);
		setDestination(position.x, position.y);
		score = 0;

		
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}

	@Override
	public void setDestination(float x, float y) {
		super.setDestination(x, y);
	}

	@Override
	public Vector2 getDestination() {
		return super.getDestination();
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void update(float delta) {
		//For testing
//		printCollisionLayer();

		float oldX = getX(), oldY = getY();
		Boolean collisionX;
		boolean collisionY;
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileWidth();

		float speed = 0.7f;

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
			setY(oldY);
		}
	}

	@Override
	public void pickUpItem() {
		super.pickUpItem();
	}

	public void think() {
		visibleItems = new ArrayList<>(0);
		vision = parent.getVision(new Vector2(getX(), getY()));

		//look for Items in vision
		for (Collectible item :
				parent.collectibles) {
			Rectangle boundingBox = item.getBoundingRectangle();
			Vector2[] player_poly_vision = new Vector2[vision.length/2];
			for (int i = 0; i < vision.length / 2; i++) {
				player_poly_vision[i] = new Vector2(vision[2*i], vision[2*i+1]);
			}
			if (Intersector.isPointInPolygon(new Array<>(player_poly_vision), new Vector2(boundingBox.x +16, boundingBox.y + 16))) {
				if (!visibleItems.contains(item)) visibleItems.add(item);
			}
		}

		//If no items, search
		if (visibleItems.size() == 0) {
			float x = destination.x+(random.nextInt(64) - 32)*random.nextInt(4);
			if (x<0) {
				x = -x;
			} else if (x > 640) {
				x -= 640;
			}
			float y = destination.y + (random.nextInt(64)-32)*random.nextInt(4);
			if (y<0) {
				y = -y;
			} else if (y > 640) {
				y -= 640;
			}
			super.setDestination(x, y);
		} else {
			//Go to pick up closest item
			float dist = Integer.MAX_VALUE;

			for (Collectible item :
					new ArrayList<>((Collection<Collectible>) visibleItems.clone())) {
				if (this.getBoundingRectangle().overlaps(item.getBoundingRectangle())) {
					parent.collectibles.remove(item);
					visibleItems.remove(item);
					this.addScore(1);
					System.out.println("Bot Scores!");
					break;
				} else {
					if (getDestination().dst(new Vector2(item.getX()+16, item.getY()+16)) < dist) {
						dist = getDestination().dst(new Vector2(item.getX()+16, item.getY()+16));
						setPathToo(item.getX() + 16, item.getY() + 16);
					}
				}
			}
		}
	}

	private void setPathToo(float x, float y) {


	}

	public float[] getVisionPolygon() {
			return vision;

	}
}
