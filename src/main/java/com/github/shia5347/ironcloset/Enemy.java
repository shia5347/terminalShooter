package com.github.shia5347.ironcloset;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public abstract class Enemy {
	
	public Texture enemyTexture;
	public Sprite enemySprite;
	public Rectangle enemyRectangle;
	public Texture enemyBulletTexture;
	
	public float speed;
	public float spawnX, spawnY;
	public float speedX, speedY;
	public final int spriteSize = 32;
	public float scale;
	
	public long startTimeFlicker;
	
	public Bullet enemyBullet;
	public float alphaVal;
	
	public float rateOfFire;
	public long startTimeShoot;
	public float rotSpeed;
	
	public Vector2 pos;
	public int hitboxWidth,hitboxHeight;	
	
	public float delayShooting;
	
	public float health;
	
	public float temp;
	
	public boolean alive = true;

	Boot game;
	public final Array<Bullet> activeBullets = new Array<Bullet>();	
	
	abstract public void drawEnemy();
	abstract public void updateEnemy(Player activePlayer);
	abstract public void disposeEnemy();
	abstract public void disposeEnemyKilled();
	abstract public void CheckInjury(Player activePlayer);
	abstract public boolean toDispose();
	
	
}
