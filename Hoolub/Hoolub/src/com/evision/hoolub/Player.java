package com.evision.hoolub;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

//hoolobul
public abstract class Player extends Actor{
	private TextureRegion texture;
	int pozX = 30, pozY = 200;
	static final int WIDTH = 100, HEIGHT = 100;
	int speed = 10;
	int healt = 100;
	int score = 0;
	
	public Player(TextureRegion texture) {
		this.texture = texture;
		this.setSize(WIDTH, HEIGHT);
		this.setPosition(pozX, pozY);
		//this.setZIndex(2);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) this.getX(), 0, (int)this.getWidth(), (int)this.getHeight());
	}
}
