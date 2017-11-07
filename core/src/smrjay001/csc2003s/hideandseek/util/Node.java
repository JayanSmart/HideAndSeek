package smrjay001.csc2003s.hideandseek.util;

import com.badlogic.gdx.math.Vector2;

public class Node {
	private boolean open;
	private Vector2 position, target;
	private float gone, togo;
	private Node parent;

	public Node(Vector2 position, float gone, Vector2 target, Node parent) {
		this.position = position;
		this.gone = gone;
		this.target = target;
		this.togo = position.dst(target);
		this.open = true;
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isOpen() {
		return open;
	}

	public void close() {
		this.open = false;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getTarget() {
		return target;
	}

	public void setTarget(Vector2 target) {
		this.target = target;
	}

	public float getGone() {
		return gone;
	}

	public void setGone(float gone) {
		this.gone = gone;
	}

	public float getTogo() {
		return togo;
	}

	public void setTogo(float togo) {
		this.togo = togo;
	}

	public float getCost() {
		return gone+togo;
	}
}
