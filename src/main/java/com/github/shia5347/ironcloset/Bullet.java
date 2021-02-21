package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class Bullet implements Pool.Poolable{
	
	public Sprite bulletSprite;
	private Texture bulletTexture;
	
	public Rectangle bulletRectangle;
	
	boolean isShot = false;
	
	final int collisionSize = 32;
	
	public float xstart,ystart;
	public float speedx,speedy;
	public float bulletSpeed;
	
	public boolean initPlayer = false;
	private boolean directionEnemySet = false;
	
	Player player;
	Boot game;
	public Bullet(float xstart,float ystart,float speedx,float speedy,float bulletSpeed,Texture texture,Boot game) {
		
		bulletRectangle = new Rectangle();
		
		bulletRectangle.width = collisionSize;
		bulletRectangle.height = collisionSize;
		
		this.xstart = xstart;
		this.ystart = ystart;
		
		bulletRectangle.x = xstart;
		bulletRectangle.y = ystart;
		
		bulletTexture = texture;
		bulletSprite = new Sprite(bulletTexture);	

		this.speedx = speedx;
		this.speedy = speedy;
		
		this.bulletSpeed = bulletSpeed;
		
		this.game = game;
		
	}
	
	

	public void updateBullet() {
		
		if(isShot == true) {
			
			bulletSprite.setX(bulletRectangle.x);
			bulletSprite.setY(bulletRectangle.y);
			
			bulletRectangle.x += speedy * bulletSpeed * Gdx.graphics.getDeltaTime(); 
			bulletRectangle.y += speedx * bulletSpeed * Gdx.graphics.getDeltaTime();
			
						
		} else {
			if(directionEnemySet == false) {
			this.speedx = player.speedx;
			this.speedy = player.speedy;
			}
		}
		
		
		/*if(initPlayer == true) {
			if(bulletRectangle.x > 1280) { 
				player.poolBullets.free(this);
				player.activeBullets.removeValue(this, true);
				System.out.println("Bullet went outside area");
			}

		}
		*/ bulletSprite.rotate(500*Gdx.graphics.getDeltaTime());
	}
	
	public void setDirectionPlayer(Player player) {
		if(this.isShot == false) {
			speedx = player.speedx;
			speedy = player.speedy;
			bulletRectangle.x  = player.playerBox.x + 10;
			bulletRectangle.y = player.playerBox.y + 10;
		}
	}
	
	public void setDirectionEnemy(Enemy enemy) {
		if(this.isShot == false) {
			speedx = enemy.speedX;
			speedy = enemy.speedY;
			bulletRectangle.x  = enemy.enemyRectangle.x + 10;
			bulletRectangle.y = enemy.enemyRectangle.y + 10;
			directionEnemySet = true;
		}
	}
	
	
	public void disposeBullet() {
		bulletTexture.dispose();
	}
	
	public void shootBullet() {
		isShot = true;
	}
	
	public void drawBullet() {
			bulletSprite.draw(game.batch);
	}

	public void verifyTexture(Texture texture) {
		if(bulletTexture == null) {
			bulletTexture = texture;
			bulletSprite = new Sprite(bulletTexture);
		}
	}
	
	
	@Override
	public void reset() {
		isShot = false;
	}
	
}
