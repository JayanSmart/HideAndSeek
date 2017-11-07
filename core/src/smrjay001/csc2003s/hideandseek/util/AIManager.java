package smrjay001.csc2003s.hideandseek.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import smrjay001.csc2003s.hideandseek.Collectible;
import smrjay001.csc2003s.hideandseek.Player;
import smrjay001.csc2003s.hideandseek.screens.ApplicationScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class AIManager extends Player {

	ArrayList<Collectible> visibleItems, seenItems;
	Queue<Node> path;

	// States
	final static int SEARCH = 1;
	final static int VISIBLE = 2;
	final static int SEEN = 3;

	boolean arrived;

	Random random;
	private int score;
	private int state;
	float[] vision;

	/**
	 * @param parent The Application
	 * @param texture The Displayed Image
	 * @param collisionLayer MapMetaData
	 * @param position The initial position of the Bot
	 */
	public AIManager(ApplicationScreen parent, Texture texture, TiledMapTileLayer collisionLayer, Vector2 position) {
		super(parent, texture, collisionLayer);
		visibleItems = new ArrayList<>();
		seenItems = new ArrayList<>();
		random = new Random();
		setX(position.x);
		setY(position.y);
		setDestination(position.x, position.y);
		score = 0;
		state = 1;
		visibleItems = new ArrayList<>(0);
		arrived = true;
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}

	@Override
	public void setDestination(float x, float y) {
		super.setDestination(x, y);
	}

	private void setDestination(Vector2 point) {
		super.setDestination(point.x, point.y);
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

		float speed = 0.7f;

		arrived = checkArrived(speed);
		if (arrived) {
			pickUpItem();
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
		for (Collectible item :
				new ArrayList<>((Collection<Collectible>) seenItems.clone())) {
			if (this.getBoundingRectangle().overlaps(item.getBoundingRectangle())) {
				parent.collectibles.remove(item);
				seenItems.remove(item);
				this.addScore(1);
				System.out.println("Bot Scores!");
				break;
			}
		}



		super.pickUpItem();
	}

	@Override
	protected boolean checkArrived(float speed) {
		return super.checkArrived(speed);
	}

	public void think() {
		visibleItems = new ArrayList<>();
		vision = parent.getVision(new Vector2(getX(), getY()));

		//look for Items in vision
		for (Collectible item :
				parent.collectibles) {
			Rectangle boundingBox = item.getBoundingRectangle();
			Vector2[] player_poly_vision = new Vector2[vision.length / 2];
			for (int i = 0; i < vision.length / 2; i++) {
				player_poly_vision[i] = new Vector2(vision[2 * i], vision[2 * i + 1]);
			}
			if (Intersector.isPointInPolygon(new Array<>(player_poly_vision), new Vector2(boundingBox.x + 16, boundingBox.y + 16))) {
				if (!visibleItems.contains(item)) visibleItems.add(item);
				if (!seenItems.contains(item)) seenItems.add(item);
			}
		}

		//First just try to pick up a coin on the current position (this is to stop a rare bug)
		pickUpItem();

		setState();

		switch (state) {
			case SEARCH:
				if (arrived) {
					setPathToo(parent.getRandomOpenPoint());
				}
				break;
			case VISIBLE:
				if (visibleItems.size() > 1) {
					setPathToo(getClosest(visibleItems));
				} else if (visibleItems.size() == 1) {
					setPathToo(visibleItems.get(0).getPosition());
				}
				break;
			case SEEN:
				if (visibleItems.size() == 0) {
					setPathToo(getClosest(seenItems));
				}
				break;

		}
	}

	private Vector2 getClosest(ArrayList<Collectible> items) {
		if (!items.isEmpty()) {
			float distance = Float.MAX_VALUE;
			Vector2 botPosition = new Vector2(getX(), getY());
			Collectible closest = items.get(0);

			for (Collectible item :
					items) {
				if (botPosition.dst(item.getPosition()) < distance) {
					distance = botPosition.dst(item.getPosition());
					closest = item;
				}
			}
			return closest.getPosition();
		}
		return parent.getRandomOpenPoint();
	}

	private void setState() {
		if (seenItems.isEmpty()) {
			state = SEARCH;
		} else {
			if (visibleItems.isEmpty() && (!seenItems.isEmpty())) {
				state = SEEN;
			} else {
				state = VISIBLE;
			}
		}

	}

	private void setPathToo(Vector2 target) {
		aStar(new Vector2(target.x/32, target.y/32));
	}

	/**
	 * This method will calculate the points to which the bot must move in order to arrive at the target.
	 * @param target The eventual end point
	 */
	private void aStar(Vector2 target) {
		path = new Queue<>();
		boolean skip = false;
		ArrayList<Node> open = new ArrayList<>();
		ArrayList<Node> closed = new ArrayList<>();
		Node current = new Node(new Vector2(getX()/32, getY()/32), 0, target, null);
		open.add(current);
		while (!open.isEmpty()) {
			Collections.sort(open, new CostComparator());
			current = open.remove(0);
			if (parent.debugging) {
				System.out.println("Current: "+Math.round(current.getPosition().x)+" : "+Math.round(current.getPosition().y));
				System.out.println("Target: "+target.x+" : "+target.y);
				System.out.println("OpenSize: "+open.size());
				System.out.println("Current Cost: "+current.getCost());
			}
			if (current.getPosition().x == target.x && current.getPosition().y == target.y) {
				while (current.getParent() != null) {
					if (parent.debugging) {
						parent.shapeRenderer.circle(current.getPosition().x*32+16, current.getPosition().y*32+16, 3);
					}
					path.addFirst(current);
					current = current.getParent();
					setDestination(path.first().getPosition().x*32, path.first().getPosition().y*32);
				}
				return;
			} else {
				closed.add(current);
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						skip = false;
						int newX = Math.round(current.getPosition().x - 1 + i);
						int newY = Math.round(current.getPosition().y - 1 + j);
						if (newX > 0 && newX < 20 && newY > 0 && newY < 20) {
							if (!(i == 1 && j == 1)) {
								if (!parent.isTileBlocked(newX*32, newY*32)) {
									Node successor = new Node(
											new Vector2(Math.round(current.getPosition().x - 1 + i), Math.round(current.getPosition().y - 1 + j)),
											(float) (current.getGone() + Math.sqrt(Math.pow((i - 1), 2) + Math.pow((j - 1), 2))),
											target, current);
									//If the node already exists in the OPEN or CLOSED list and it has a cost less this this current node, skip that node.
									for (Node node :
											(ArrayList<Node>) open.clone()) {
										if ((node.getPosition().x == successor.getPosition().x) && (node.getPosition().y == successor.getPosition().y)) {
											if (node.getCost() <= successor.getCost()) {
												skip = true; //Skip this node
											}
										}
									}
									if (skip) continue;
									for (Node node :
											(ArrayList<Node>) closed.clone()) {
										if ((node.getPosition().x == successor.getPosition().x) && (node.getPosition().y == successor.getPosition().y)) {
											if (node.getCost() <= successor.getCost()) {
												skip = true; //Skip this node
											} else {
												closed.remove(node);
											}
										}
									}
									if (skip) continue;
									open.add(successor);
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean lineOfSite(Vector2 target) {
		Array<Vector2> visionPoly = new Array<>(vision.length/2);
		for (int i = 0; i < vision.length; i+=2) {
			visionPoly.add(new Vector2(vision[i], vision[i+1]));
		}
		if (Intersector.isPointInPolygon(visionPoly, target)) {
			return true;
		}
		return false;
	}

	public float[] getVisionPolygon() {
			return vision;
	}
}
