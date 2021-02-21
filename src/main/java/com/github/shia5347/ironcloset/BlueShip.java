package com.github.shia5347.ironcloset;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BlueShip extends Enemy implements Pool.Poolable{

	public BlueShip(float speed,float rotSpeed,float delayShooting,float health,float scale,Boot game) {
		
		this.scale = scale;
		
		enemyTexture = new Texture(Gdx.files.internal("assets/BlueShip.png"));

		enemySprite = new Sprite(enemyTexture);
		enemySprite.scale(scale);
		
		enemyRectangle = new Rectangle();
		
		enemyRectangle.width = enemySprite.getWidth();
		enemyRectangle.height = enemySprite.getHeight();
		
		pos = new Vector2();
		
		this.speed = speed;
		this.health = health;
		
		temp = this.health;
		
		this.rotSpeed = rotSpeed;
		
		startTimeFlicker = System.currentTimeMillis();
		startTimeShoot = System.currentTimeMillis();
		
		alphaVal = 1;
		
		this.game = game;
		
		this.delayShooting = delayShooting;
		
		
		enemyBulletTexture = new Texture(Gdx.files.internal("assets/Bullet_BlueShip.png"));

	}
	
	private final Pool<Bullet> poolBullets = new Pool<Bullet>() {

		@Override
		protected Bullet newObject() {
			// TODO Auto-generated method stub
			return new Bullet(enemyRectangle.x + 10 ,enemyRectangle.y + 10 ,speedX,speedY,100,enemyBulletTexture,game);
		}
		
	};
	
	@Override
	public void drawEnemy() {
		// TODO Auto-generated method stub
		if(health > 0)
			enemySprite.draw(game.batch);
		for(Bullet bullet : activeBullets) {
			bullet.drawBullet();
		}
		
		
	}

	@Override
	public void updateEnemy(Player activePlayer) {
		
			CheckInjury(activePlayer);
			enemySprite.setAlpha(alphaVal);
			if(alphaVal > 1)
				alphaVal = 1;
		
		// TODO Auto-generated method stub
		speedX = (float)Math.cos(Math.toRadians(enemySprite.getRotation()));
		speedY = -(float)Math.sin(Math.toRadians(enemySprite.getRotation()));
		
		pos.lerp(activePlayer.playerBox.getPosition(activePlayer.pos), Gdx.graphics.getDeltaTime() * speed);
		enemyRectangle.setPosition(pos);
		
		enemySprite.rotate(Gdx.graphics.getDeltaTime() * rotSpeed);
		enemySprite.setX(enemyRectangle.x);
		enemySprite.setY(enemyRectangle.y);
		
		if((System.currentTimeMillis() - startTimeShoot)/100 > delayShooting) {
			
		if(health > 0) {
		Bullet bullet = poolBullets.obtain();
		bullet.setDirectionEnemy(this);
		bullet.shootBullet();
		
		activeBullets.add(bullet);
		
		}
		
		startTimeShoot = System.currentTimeMillis();

		
		}
		
		for(Bullet refBullet : activeBullets) {
			refBullet.updateBullet();
			if(refBullet.bulletRectangle.x > 1280 || refBullet.bulletRectangle.x < 0) {
				poolBullets.free(refBullet);
				activeBullets.removeValue(refBullet, true);
			} else {
				
			if(refBullet.bulletRectangle.y > 720 || refBullet.bulletRectangle.y < 0) {
				poolBullets.free(refBullet);
				activeBullets.removeValue(refBullet, true);
			}
			
			}
		}
		
		
		
		
		
	}
	
	
	@Override
	public void disposeEnemyKilled() {
		// TODO Auto-generated method stub
		enemyTexture.dispose();
		for(int x = 0; x < poolBullets.getFree(); x++) {
			Bullet bullet = poolBullets.obtain();
			activeBullets.add(bullet);
		}
		
		for(Bullet bullet : activeBullets) {
			//bullet.disposeBullet();
			poolBullets.free(bullet);
			activeBullets.removeValue(bullet, true);
		}
		
	}
	@Override
	public void disposeEnemy() {
		// TODO Auto-generated method stub
		enemyTexture.dispose();
		enemyBulletTexture.dispose();
		for(int x = 0; x < poolBullets.getFree(); x++) {
			Bullet bullet = poolBullets.obtain();
			activeBullets.add(bullet);
		}
		
		for(Bullet bullet : activeBullets) {
			bullet.disposeBullet();
			poolBullets.free(bullet);
			activeBullets.removeValue(bullet, true);
		}
		
		
	}
	
	
	@Override
	public boolean toDispose() {
		
		if(health < 0 && activeBullets.size == 0) {
			disposeEnemyKilled();
			return true;
		} else {
			
		if(health < 0) {
			enemyTexture.dispose();
			
			return false;
		}	
			
		}
		
		return false;
		
	}
	
	

	@Override
	public void CheckInjury(Player activePlayer) {
		// TODO Auto-generated method stub
		
		//enemySprite.setAlpha(1000);
		for(Bullet bullet : activePlayer.activeBullets) {
			if(bullet.bulletRectangle.overlaps(enemyRectangle)) {
				health -=1;
			

				if(((System.currentTimeMillis() - startTimeFlicker)/100) >= 1f) {
					alphaVal = 1000;
					}
	
				if(((System.currentTimeMillis() - startTimeFlicker)/100) >= 1.1f) {
					alphaVal = 1;
					startTimeFlicker = System.currentTimeMillis();
					
			}
				
				
				
		
			}
		}
		
				
		}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		health = temp;
		enemyTexture.dispose();
		enemyTexture = new Texture(Gdx.files.internal("assets/BlueShip.png"));
		enemySprite = new Sprite(enemyTexture);
		enemySprite.scale(scale);
		
	}

		
		
		/*
		for(Bullet bullet : activePlayer.activeBullets) {
			if(bullet.bulletRectangle.overlaps(enemyRectangle)) {
				
			}
		}
		*/
	}


