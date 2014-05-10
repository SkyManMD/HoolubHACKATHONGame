package com.evision.hoolub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SunActor extends Actor{
private TextureRegion texture;
	
	public SunActor(TextureRegion texture) {
		this.texture = texture;
		this.setSize(128, 128);
		this.setPosition(Gdx.graphics.getWidth() - getWidth() - 50, Gdx.graphics.getHeight() - getHeight() - 50);
		this.toBack();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
