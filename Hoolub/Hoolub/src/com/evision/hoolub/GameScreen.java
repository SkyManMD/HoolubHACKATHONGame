package com.evision.hoolub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;


public class GameScreen implements Screen {
	final GameClass game;
	private SpriteBatch batch;
	private Texture texture;
	private Texture textureHouse, textureEnemy;
	private Stage stage;
	private Music flySound;
	private Music backgroundSound;
	private Music bird_poop_Sound;
	private Music aargh_censored_Sound;
	private Texture backgroundImage;
	
	private AnimatorHoolub playerHoolob;
	private Score score;
	private List<AnimatorHuman> enemyList = new ArrayList<AnimatorHuman>();
	private List<Building> buildingList = new ArrayList<Building>();
	private Laser laser;
	
	Thread buildingGenerate = new Thread(new GeneradeBuildingThread(), "generate building thread");
	Thread enemyGenerate = new Thread(new GenerateEnemyThread(), "generate enemy thread");
	private int rndSpeed = 200, enemyEscaped = 0;
	public GameScreen(final GameClass gam) {
		this.game = gam;
		batch = new SpriteBatch();
		backgroundImage = new Texture("data/image/background.png");
		backgroundImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		textureHouse = new Texture("data/image/House.png");
		textureEnemy = new Texture("data/image/Human.png");
		
		
		playerHoolob = new AnimatorHoolub();
		playerHoolob.addListener(new KeyListenerActor());
		
		backgroundSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/music/backgroundSound.mp3", FileType.Internal));
		backgroundSound.setVolume(0.3f);
		backgroundSound.play();
		backgroundSound.setLooping(true);
		
		flySound =  Gdx.audio.newMusic( Gdx.files.getFileHandle("data/music/sunet_aripi2.mp3", FileType.Internal));
		flySound.setVolume(0.9f);
		flySound.play();
		flySound.setLooping(true);
		
		bird_poop_Sound = Gdx.audio.newMusic( Gdx.files.getFileHandle("data/music/bird_poop_final_sound.mp3", FileType.Internal));
		bird_poop_Sound.setVolume(1.0f);
		
		aargh_censored_Sound = Gdx.audio.newMusic( Gdx.files.getFileHandle("data/music/aaargh_censored.mp3", FileType.Internal));
		aargh_censored_Sound.setVolume(1.0f);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, batch);
		
		Gdx.input.setInputProcessor(stage);

		playerHoolob.addListener(new KeyListenerActor());
		score = new Score("Score : " + playerHoolob.score + " / Escaped : " + enemyEscaped);
		laser = new Laser(new TextureRegion(new Texture("data/image/kaka.png"), 0, 0, 8, 8));
		laser.setVisible(false);

		stage.addActor(playerHoolob);
		stage.addActor(score);
		stage.setKeyboardFocus(playerHoolob);
		stage.addActor(laser);
		stage.addActor(new SunActor(new TextureRegion(new Texture("data/image/soare.png"),0,0,256,256)));
		
		buildingGenerate.start();
		enemyGenerate.start();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		int tileCount = 3; 
	    batch.draw(backgroundImage, 0, 0,
	    		backgroundImage.getWidth() * tileCount, 
	    		backgroundImage.getHeight() * tileCount, 
	         0, tileCount, 
	         tileCount, 0);
		//batch.draw(backgroundImage, 0, 0);//, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.end();
		
		
		keyListener();
	    moveBuilding((int)(100 * Gdx.graphics.getDeltaTime()));
		moveEnemy((int)(rndSpeed * Gdx.graphics.getDeltaTime()));	 
		moveGun((int)(400 * Gdx.graphics.getDeltaTime()));	 
		stage.draw();
		//stage.act(Gdx.graphics.getDeltaTime());
		
		playerHoolob.draw();
		//animatorHuman.draw();
		
		//desenare dusmani;
		Iterator<AnimatorHuman> iterator = enemyList.iterator();
		while(iterator.hasNext()) {
			AnimatorHuman currentEnemy = iterator.next();
			currentEnemy.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public synchronized void verifyEnemyKill() {
		Iterator<AnimatorHuman> iterator = enemyList.iterator();
		while(iterator.hasNext()) {
			AnimatorHuman currentEnemy = iterator.next();
			if (laser.getRectangle().intersects(currentEnemy.getRectangle())) {
				currentEnemy.remove();  //stergere actor de pe scena;
				iterator.remove();  //stergere actor din lista cu dusmani;
				rndSpeed = (new Random().nextInt(4) + 1)* 100; //generare alta viteza pt enemy;
				score.setText("Score : " + (++playerHoolob.score) + " / Escaped : " + enemyEscaped);
				aargh_censored_Sound.play();
				//System.out.println("reomved, enemy list size = " + enemyList.size());
			}
		}
	}
	 
	//generate enemy thread
	public class GenerateEnemyThread implements Runnable {

		@Override
		public void run() {
			//Random random = new Random();
	        while(true) {
	        	for (int i=0; i<4; i++) {
	        		try {
	        			Thread.sleep((new Random().nextInt(10) * 1000));  //2 sec;
	        			AnimatorHuman newEnemy = new AnimatorHuman(textureEnemy);  //generare enemy;
		        		enemyList.add(newEnemy);  //adaugare in lista;
		        		stage.addActor(newEnemy); //adaugare pe scena;
		        		System.out.println("size = " + enemyList.size());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        }		
	    }	
	}
	
	public synchronized void moveEnemy(int coef) {
			Iterator<AnimatorHuman> iterator = enemyList.iterator();
			while(iterator.hasNext()) {
				AnimatorHuman currentEnemy = iterator.next();
				currentEnemy.move(coef);
				if(currentEnemy.getX() <= -200) { //daca enemy ajunge la x=0 (pozitia player-ului)
					currentEnemy.remove();  //stergem de pe scena
					iterator.remove(); //stergeme din lista;
					rndSpeed = (new Random().nextInt(4) + 1) * 100;
					enemyEscaped++;
					score.setText( "Score : " + playerHoolob.score + " / Escaped : " + enemyEscaped);
					//System.out.println("enemy list size = " + enemyList.size());
				}
			}
	}
	
	//generate building thread
	public class GeneradeBuildingThread implements Runnable {

			@Override
			public void run() {
		        while(true) {
		        	for (int i=0; i < new Random().nextInt(5); i++) {
		        		try {
		        			Thread.sleep(9000);  //2 sec;
		        			TextureRegion txtRegionHouse = null;
		        			Building newBuilding = null;
		        			switch(new Random().nextInt(3)) {
		        			case 0:
		        				txtRegionHouse = new TextureRegion(textureHouse, 0, 0, 377, 557);
		        				newBuilding = new Building(txtRegionHouse, Gdx.graphics.getWidth(), 70, 377, 557);  //generare building;
		        				break;
		        			case 1:
		        				txtRegionHouse = new TextureRegion(textureHouse, 380, 0, 461, 260);
		        				newBuilding = new Building(txtRegionHouse, Gdx.graphics.getWidth(), 70, 461, 260);  //generare building;
		        				break;
		        			case 2:
		        				txtRegionHouse = new TextureRegion(textureHouse, 380, 260, 400, 259);
		        				newBuilding = new Building(txtRegionHouse, Gdx.graphics.getWidth(), 70, 400, 259);  //generare building;
		        				break;
		        			}
			        		
		        			newBuilding.toFront();
			        		buildingList.add(newBuilding);  //adaugare in lista;
			        		stage.addActor(newBuilding); //adaugare pe scena;
			        		//System.out.println("size = " + enemyList.size());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        	}
		        }		
		    }	
		}
	
	public synchronized void moveBuilding(int coef) {
			Iterator<Building> iterator = buildingList.iterator();
			while(iterator.hasNext()) {
				Building currentBuilding = iterator.next();
				currentBuilding.toFront();
				currentBuilding.move(coef);
				if(currentBuilding.getX() <= -400) { //daca enemy ajunge la x=0 (pozitia player-ului)
					currentBuilding.remove();  //stergem de pe scena
					iterator.remove(); //stergeme din lista;
					//System.out.println("building list size = " + buildingList.size());
				}
		}
	}
	
	private void keyListener() {
		if(Gdx.input.isKeyPressed(Keys.UP))  {
  		  playerHoolob.setY(playerHoolob.getY() + 200 * Gdx.graphics.getDeltaTime());
	    	  if(playerHoolob.getY() >= Gdx.graphics.getHeight() - playerHoolob.getHeight()) playerHoolob.setY(Gdx.graphics.getHeight() - playerHoolob.getHeight());
		}
	      if(Gdx.input.isKeyPressed(Keys.DOWN)) {
	    	  playerHoolob.setY(playerHoolob.getY() - 200 * Gdx.graphics.getDeltaTime());
	    	  if(playerHoolob.getY() <= 0) playerHoolob.setY(0);
	      }
	      if(Gdx.input.isKeyPressed(Keys.RIGHT))  {
	    	  playerHoolob.setX(playerHoolob.getX() + 200 * Gdx.graphics.getDeltaTime());
	    	  if(playerHoolob.getX() >= Gdx.graphics.getWidth() - playerHoolob.getWidth()) playerHoolob.setX(Gdx.graphics.getWidth() - playerHoolob.getWidth());
	      }
	      if(Gdx.input.isKeyPressed(Keys.LEFT)) {
	    	  playerHoolob.setX(playerHoolob.getX() - 200 * Gdx.graphics.getDeltaTime());
	    	  if(playerHoolob.getX() <= 0) playerHoolob.setX(0);
	      }
	    //  if(Gdx.input.isKeyPressed(Keys.SPACE)) verifyEnemyKill();
	}
	
	class KeyListenerActor extends InputListener {
		@Override
		public boolean keyDown (InputEvent event, int keycode) {
			switch(keycode) {
			case Keys.SPACE :
				//verifyEnemyKill();
		    	laser.setVisible(true);
		    	laser.setPosition(playerHoolob.getX()+20, playerHoolob.getY());
		    	bird_poop_Sound.play();
				break;
			}
			return false;
		}
		
		@Override
		public boolean keyUp (InputEvent event, int keycode) {
			switch(keycode) {
			case Keys.SPACE :
		    	//laser.setVisible(false);
				break;
			}
			return false;
		}
	}
	
	private void moveGun(int coef) {
		laser.setY(laser.getY() - coef);
		verifyEnemyKill();
		if(laser.getY() <= 0) { 
			laser.setY(0);
			laser.setVisible(false);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
