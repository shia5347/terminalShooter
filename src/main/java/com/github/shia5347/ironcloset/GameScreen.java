package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen{

Boot game;
OrthographicCamera camera;

Player player;

Arena arena;

public final float playerSpeed = 700; 
public final float playerRotationSpeed = 400;
	
Texture loose;

	public GameScreen(Boot game) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		this.game = game;
		
		//player = new Player(playerSpeed,playerRotationSpeed,this.game);
		arena = new Arena(playerSpeed,playerRotationSpeed,camera,this.game);
		loose = new Texture(Gdx.files.internal("assets/TryAgain.png"));
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		ScreenUtils.clear(Color.BLACK);
		
		game.batch.begin();
		//player.drawPlayer();
		arena.Update();
		/*
		for(Bullet bullet : player.activeBullets) {
		bullet.drawBullet();
		bullet.updateBullet();
		}
		game.batch.end();
		if(player.active == true) {
		player.processInput();
		}
		*/
		
		if(arena.running == false) 
			game.batch.draw(loose,1280/2 - loose.getWidth()/2,720/2 - loose.getHeight()/2);

		
		if(Gdx.input.isKeyJustPressed(Keys.R) && arena.running == false) {
			arena.highscore = (System.currentTimeMillis() - arena.startTime)/1000;
			arena.startTime = System.currentTimeMillis();
			arena.disposeArena();
			arena = new Arena(playerSpeed,playerRotationSpeed,camera,game);
		}
		
		

		//player.updatePlayer();
		game.batch.end();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit();
		
		
		
		
	}	

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		arena.disposeArena();
		loose.dispose();
		//player.disposePlayer();
		
	}
	
	public void updateBullet() {
		
	}

}
