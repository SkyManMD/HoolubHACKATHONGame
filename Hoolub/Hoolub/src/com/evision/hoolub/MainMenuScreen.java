package com.evision.hoolub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {

    final GameClass game;
    Music generic;
    TextureRegion mainScreen;
    TextureRegion[] buttons;
    Texture texture;
    Rectangle newGame;
    Rectangle sound;
    Rectangle exit;
    OrthographicCamera camera;

    public MainMenuScreen(final GameClass gam) {
        game = gam;
        mainScreen=new TextureRegion(new Texture("data/hoolobmain.png"),0,0,960,640);
        newGame=new Rectangle();
        sound=new Rectangle();
        exit=new Rectangle();
        newGame.height=60;
        newGame.width=253;
        newGame.x=91;
        newGame.y=318;
        exit.height=60;
        exit.width=253;
        exit.x=91;
        exit.y=Gdx.graphics.getHeight()-444;
        sound.height=60;
        sound.width=253;
        sound.x=91;
        sound.y=Gdx.graphics.getHeight()-383;
        generic=Gdx.audio.newMusic(Gdx.files.internal("data/mainMenuTheme.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);
        texture = new Texture(Gdx.files.internal("data/buttons.png"));
        buttons = new TextureRegion[3];
        buttons[0] = new TextureRegion(texture,0,0,253,60);
        buttons[1] = new TextureRegion(texture,0,61,253,60);
        buttons[2] = new TextureRegion(texture,0,122,253,60);
        
    }
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(mainScreen, 0, 0);
        game.batch.draw(buttons[0],newGame.x,newGame.y);
        game.batch.draw(buttons[1],sound.x,sound.y);
        game.batch.draw(buttons[2],exit.x,exit.y);
        game.batch.end();
        if (Gdx.input.isTouched()){
        	if(newGame.contains(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY()) ) {
                game.setScreen(new GameScreen(game));
                this.dispose();               
            }
        	if(sound.contains(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY()) ) {
                    if(generic.isPlaying())
                    	generic.stop();
                    else 
                    	{
                    	generic.play();
                    	generic.setLooping(true);
                    	}
            }
        	if(exit.contains(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY()) ) {
                System.exit(0);
        }
        	
        	
        }
        		
    }
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		generic.play();
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


}