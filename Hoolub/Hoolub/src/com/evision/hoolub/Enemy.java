package com.evision.hoolub;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Player {

	public Enemy(TextureRegion texture, int pozX, int pozY) {
		super(texture);
		super.pozX = pozX;
		super.pozY = pozY;
		this.setPosition(pozX, pozY);
		//this.setZIndex(0);
		this.toFront();
	}
	
	public void move(int coef) {
		this.setX(this.getX() - coef);
	}

}
