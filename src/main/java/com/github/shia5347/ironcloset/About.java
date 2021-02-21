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

public class About implements Screen{

Vector2 next;	
Vector2 pos;

Texture aboutMap;
TextureRegion about;

float regionX;

Boot game;
Sound select;	
public float speed = 4;

	public About(Boot game) {
		
		pos = new Vector2();
		next = new Vector2();
		
		aboutMap = new Texture(Gdx.files.internal("assets/WASD.png"));
		about = new TextureRegion(aboutMap,0,0,500,500);
	
		regionX = 1280/2 - (about.getRegionWidth()/2);
		
		next = new Vector2();
		pos = new Vector2();
		
		this.game = game;
	
		select = Gdx.audio.newSound(Gdx.files.internal("assets/Select.wav"));
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if(next.x < 1500-500)  {
				select.play();
				next.x += 500;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if(next.x > 0) {
				select.play();
				next.x -= 500;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new TitleScreen(game));
		}
		
		pos.lerp(next, speed * Gdx.graphics.getDeltaTime());
	
		about.setRegion((int)pos.x,0,500,500);	
		
		ScreenUtils.clear(Color.BLACK);	
		
		game.batch.begin();
		
		game.batch.draw(about,regionX,720/2 - about.getRegionHeight()/2);
		
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
		
		aboutMap.dispose();
		select.dispose();
	}
	
	
	
}
