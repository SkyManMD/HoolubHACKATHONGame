package com.evision.hoolub;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class AnimatorHuman  extends Actor {
		private static final int FRAME_COLS = 4;
		private static final int FRAME_ROWS = 2;

		private Animation walkAnimation;
		private Texture walkSheet;
		private TextureRegion[] walkFrames;
		private SpriteBatch spriteBatch;
		private TextureRegion currentFrame;

		private float stateTime;

		static final int WIDTH = 42, HEIGHT = 66;

		public AnimatorHuman(Texture texture) {
			setSize(WIDTH, HEIGHT);
			setPosition(Gdx.graphics.getWidth(), 30);
			walkSheet = texture;//new Texture(Gdx.files.internal("data/image/Human.png"));
			walkFrames = new TextureRegion[16];

			int index = 0;

			for (int i = 0; i < 8; i++) {
				walkFrames[index++] = new TextureRegion(walkSheet,i*42,0,42,66);
				walkFrames[15-i] = walkFrames[i];
			}
			walkAnimation = new Animation(0.1f, walkFrames);
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
			return new Rectangle((int) this.getX(), (int) this.getY(), (int)this.getWidth(), (int)this.getHeight());
		}
		
		public void move(int coef) {
			this.setX(this.getX() - coef);
		}
	}

