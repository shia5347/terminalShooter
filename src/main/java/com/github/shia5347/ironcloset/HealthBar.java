package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar {

	private Texture bar1,bar2;
	private TextureRegion bar;
	
	Boot game;
	
	int x,y;
	
	float health;
	
	public HealthBar(Boot game,int x,int y) {
		
		bar1 = new Texture(Gdx.files.internal("assets/HealthBar.png"));
		bar = new TextureRegion();
		
		bar.setRegion(bar1);
		
		this.game = game;
		
		this.x = x;
		this.y = y;
	}
	
	
	public void updateHealthBar(float health) {
		
		
		this.health = health;
		
		game.batch.draw(bar, x, y, this.health, 32);
		
	}
	
	public void dispose() {
		bar1.dispose();
	}
	
	
}