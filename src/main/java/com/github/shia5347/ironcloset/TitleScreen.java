package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleScreen implements Screen{
	
	Texture menuWords;
	TextureRegion menu;
	
	Boot game;
	float move;
	
	Vector2 pos;
	Vector2 next;
	
	float regionX;
	
	float speed = 4f;

	boolean right,left;
	
	Sound select;
	
	public TitleScreen(Boot game) {
		menuWords = new Texture(Gdx.files.internal("assets/MainMenu.png"));
		menu = new TextureRegion(menuWords,0,0,200,400);
		
		
		this.game = game;
		
		pos = new Vector2();
		next = new Vector2();
		
		pos.x = 0;
		next.x = 200; 
		
		right = false;
		left = false;
		
		regionX = 1280/2 - (menu.getRegionWidth()/2);
	
		select = Gdx.audio.newSound(Gdx.files.internal("assets/Select.wav"));
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		move = speed; 
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			next.x = 200;
			select.play();
		}
	
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			next.x = 0;
			select.play();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		pos.lerp(next, speed * Gdx.graphics.getDeltaTime());
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && pos.x < 130) {
			game.setScreen(new GameScreen(game));
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && pos.x > 130) {
			game.setScreen(new About(game));
		}
		
		
		menu.setRegion((int)pos.x,0,200,400);
	
				
		
		ScreenUtils.clear(Color.BLACK);	
		game.batch.begin();
		game.batch.draw(menu, regionX, 0);
		game.batch.end();
		
		
		
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
		menuWords.dispose();
		select.dispose();
	}

}
