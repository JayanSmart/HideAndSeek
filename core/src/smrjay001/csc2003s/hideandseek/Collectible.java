package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import smrjay001.csc2003s.hideandseek.screens.ApplicationScreen;

public class Collectible extends Sprite {
	ApplicationScreen parent;

	public Collectible(ApplicationScreen parent, Texture texture, float x, float y) {
		super(texture);
		this.parent = parent;
		setPosition(x, y);
	}

	public Vector2 getPosition() {
		return new Vector2(getX(), getY());
	}
}
