package com.evision.hoolub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {
	private BitmapFont font = new BitmapFont();
	private String text = "N/A";
	static final int WIDTH = 100, HEIGHT = 100;
	
	public Score(String text) {
		this.text = text;
		this.setSize(WIDTH, HEIGHT);
		this.toFront();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		font.setScale(1,WIDTH/HEIGHT);
		font.setColor(Color.BLACK);
		font.draw(batch, text, 10, Gdx.graphics.getHeight()-10);
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
