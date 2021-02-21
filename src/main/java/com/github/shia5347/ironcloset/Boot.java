package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Boot extends Game{

	public SpriteBatch batch;
	public BitmapFont font;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new TitleScreen(this));
	}
	
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
