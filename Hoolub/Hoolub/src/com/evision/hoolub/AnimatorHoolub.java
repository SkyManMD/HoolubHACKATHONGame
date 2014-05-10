package com.evision.hoolub;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatorHoolub extends Actor {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 2;

	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;

	private float stateTime;
	
	int pozX = 30, pozY = 200;
	static final int WIDTH = 102, HEIGHT = 132;
	//int speed = 10;
	//int healt = 100;
	int score = 0;

	public AnimatorHoolub() {
		setSize(WIDTH, HEIGHT);
		setPosition(0, 300);
		
		walkSheet = new Texture(Gdx.files.internal("data/image/player.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 102, 132);
		walkFrames = new TextureRegion[16];

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		for (int i = 0; i < 8; i++) {
			walkFrames[index++] = walkFrames[7 - i];
		}
		walkAnimation = new Animation(0.03f, walkFrames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	public void draw() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, getX(), getY());
		spriteBatch.end();
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) this.getX(), 0, (int)this.getWidth() - 30, (int)this.getHeight());
	}
}
