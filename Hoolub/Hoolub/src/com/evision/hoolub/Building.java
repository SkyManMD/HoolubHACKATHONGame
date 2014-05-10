package com.evision.hoolub;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Building extends Enemy {

	public Building(TextureRegion texture, int pozX, int pozY, int width, int height) {
		super(texture, pozX, pozY);
		this.setSize(width, height);
		//super.setZIndex(1);
		this.toBack();
	}

}
