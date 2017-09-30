package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {

	Vector2 speed;
	Vector2 destination;

	public void setSpeed(Vector2 speed) {
		this.speed = speed;
	}

	public Vector2 getSpeed() {
		return speed;
	}

	public Vector2 getDestination() {
		return destination;
	}

	public void setDestination(float x, float y) {
		this.destination = new Vector2(x,y);
	}

	public Player() {
		super();
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(Texture texture) {
		super(texture);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(Texture texture, int srcWidth, int srcHeight) {
		super(texture, srcWidth, srcHeight);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(TextureRegion region) {
		super(region);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(region, srcX, srcY, srcWidth, srcHeight);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	public Player(Sprite sprite) {
		super(sprite);
		speed = new Vector2().setZero();
		destination = new Vector2().setZero();
	}

	@Override
	public void set(Sprite sprite) {
		super.set(sprite);
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
	}

	public void setPosition(Vector2 position) {
		super.setPosition(position.x, position.y);
	}

	@Override
	public void setX(float x) {
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		super.setY(y);
	}

	@Override
	public void setCenterX(float x) {
		super.setCenterX(x);
	}

	@Override
	public void setCenterY(float y) {
		super.setCenterY(y);
	}

	@Override
	public void setCenter(float x, float y) {
		super.setCenter(x, y);
	}

	@Override
	public void translateX(float xAmount) {
		super.translateX(xAmount);
	}

	@Override
	public void translateY(float yAmount) {
		super.translateY(yAmount);
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		super.translate(xAmount, yAmount);
	}

	@Override
	public void setColor(Color tint) {
		super.setColor(tint);
	}

	@Override
	public void setAlpha(float a) {
		super.setAlpha(a);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		super.setColor(r, g, b, a);
	}

	@Override
	public void setColor(float color) {
		super.setColor(color);
	}

	@Override
	public void setOrigin(float originX, float originY) {
		super.setOrigin(originX, originY);
	}

	@Override
	public void setOriginCenter() {
		super.setOriginCenter();
	}

	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
	}

	@Override
	public float getRotation() {
		return super.getRotation();
	}

	@Override
	public void rotate(float degrees) {
		super.rotate(degrees);
	}

	@Override
	public void rotate90(boolean clockwise) {
		super.rotate90(clockwise);
	}

	@Override
	public void setScale(float scaleXY) {
		super.setScale(scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
	}

	@Override
	public void scale(float amount) {
		super.scale(amount);
	}

	@Override
	public float[] getVertices() {
		return super.getVertices();
	}

	@Override
	public Rectangle getBoundingRectangle() {
		return super.getBoundingRectangle();
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}

	@Override
	public void draw(Batch batch, float alphaModulation) {
		super.draw(batch, alphaModulation);
	}

	@Override
	public float getX() {
		return super.getX();
	}

	@Override
	public float getY() {
		return super.getY();
	}

	@Override
	public float getWidth() {
		return super.getWidth();
	}

	@Override
	public float getHeight() {
		return super.getHeight();
	}

	@Override
	public float getOriginX() {
		return super.getOriginX();
	}

	@Override
	public float getOriginY() {
		return super.getOriginY();
	}

	@Override
	public float getScaleX() {
		return super.getScaleX();
	}

	@Override
	public float getScaleY() {
		return super.getScaleY();
	}

	@Override
	public Color getColor() {
		return super.getColor();
	}

	@Override
	public void setRegion(float u, float v, float u2, float v2) {
		super.setRegion(u, v, u2, v2);
	}

	@Override
	public void setU(float u) {
		super.setU(u);
	}

	@Override
	public void setV(float v) {
		super.setV(v);
	}

	@Override
	public void setU2(float u2) {
		super.setU2(u2);
	}

	@Override
	public void setV2(float v2) {
		super.setV2(v2);
	}

	@Override
	public void setFlip(boolean x, boolean y) {
		super.setFlip(x, y);
	}

	@Override
	public void flip(boolean x, boolean y) {
		super.flip(x, y);
	}

	@Override
	public void scroll(float xAmount, float yAmount) {
		super.scroll(xAmount, yAmount);
	}

	public Vector2 getPosition() {
		return new Vector2(this.getX(), getY());
	}

	public void move() {
		if (Math.abs(this.getX() - destination.x) > 1 || (Math.abs(this.getY() - destination.y) > 1)) {
			super.setPosition(this.getX() + speed.x, this.getY() + speed.y);
		} else {
			speed.setZero();
			this.setPosition(destination);
			destination.setZero();
		}
	}
}
