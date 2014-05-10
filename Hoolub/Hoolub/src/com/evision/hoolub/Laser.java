package com.evision.hoolub;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Laser extends Actor{
	
	private TextureRegion texture;
	
	public Laser(TextureRegion texture) {
		this.texture = texture;
		this.setSize(8, 8);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) this.getX(), (int) this.getY(), (int)this.getWidth(), (int)this.getHeight());
	}
}
