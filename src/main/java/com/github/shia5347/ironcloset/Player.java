package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Player {
	
	public Rectangle playerBox;
	public Sprite playerSprite;
	
	private Texture playerTexture;
	private TextureRegion playerTextureRegion;
	private Texture playerBulletTexture;
	
	private final int pixelSize = 32;
	public Vector2 pos;
	
	float speed;
	float rotSpeed;
	
	//float newRot;
	
	public float speedx,speedy;
	
	public float spawnX, spawnY; // Will be used for the arena class
	
		
	public Bullet bullet;
	
	
	Boot game;
	
	public boolean active;
	public boolean hasPassedScreen = false;
	
	private long startTimeFlicker;
	private long startTimeSound;
	private long timer;
	
	private float alphaVal;
	
	public float health = 100;
	
	
	
	Sound sound;
	
	public Player(float speed, float rotSpeed,Boot game) {
		playerBox = new Rectangle();
		
		playerBox.x = 1280/2 - pixelSize/2;
		playerBox.y = 720/2 - pixelSize/2;
		
		playerBox.width = pixelSize;
		playerBox.height = pixelSize;
		
		playerTexture = new Texture(Gdx.files.internal("assets/Player.png"));
		playerTextureRegion = new TextureRegion(playerTexture,32,0,32,32);
		
		playerSprite = new Sprite(playerTextureRegion);
		
		pos = new Vector2();
		
		this.speed = speed;
		this.rotSpeed = rotSpeed;
		this.game = game;
		active = true;
	
		alphaVal = 1;
		
		active = true;
		
		startTimeFlicker = System.currentTimeMillis();
	
		playerBulletTexture = new Texture(Gdx.files.internal("assets/Bullet.png"));
		
		sound = Gdx.audio.newSound(Gdx.files.internal("assets/bullet.wav"));
		
	}
	
	public final Array<Bullet> activeBullets = new Array<Bullet>();
	private final Pool<Bullet> poolBullets = new Pool<Bullet>() {

		@Override
		protected Bullet newObject() {
			// TODO Auto-generated method stub
			return new Bullet(playerBox.x + 10 ,playerBox.y + 10 ,speedx,speedy,2000,playerBulletTexture,game);
		}
		
	};
	

		public void drawPlayer() {
		playerSprite.setX(playerBox.x);
		playerSprite.setY(playerBox.y);
		playerSprite.draw(game.batch);
	}
	
	public void processInput() {
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) /*playerSprite.rotate(-rotSpeed * Gdx.graphics.getDeltaTime()); */ playerBox.x += speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) /*playerSprite.rotate(rotSpeed * Gdx.graphics.getDeltaTime()); */ playerBox.x -= speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			
			//playerBox.x += y * speed * Gdx.graphics.getDeltaTime();
			//playerBox.y += x * speed * Gdx.graphics.getDeltaTime();
			
			playerBox.y += speed * Gdx.graphics.getDeltaTime();
		}
			
		
		if(Gdx.input.isKeyPressed(Input.Keys.S))  {
			//playerBox.x -= y * speed * Gdx.graphics.getDeltaTime();
			//playerBox.y -= x * speed * Gdx.graphics.getDeltaTime();
			playerBox.y -= speed * Gdx.graphics.getDeltaTime();

		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.L))  playerSprite.rotate(-rotSpeed * Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyPressed(Input.Keys.J))  playerSprite.rotate(rotSpeed * Gdx.graphics.getDeltaTime());
		
		timer = (System.currentTimeMillis() - startTimeSound)/100;
		
		//Shoot bullet
		if(Gdx.input.isKeyPressed(Input.Keys.K)) {
			
			if(timer > 0.6f) {
			sound.play(0.5f);
			timer = 0;
			startTimeSound = System.currentTimeMillis();
			}
			
			bullet = poolBullets.obtain();
			
			activeBullets.add(bullet);
			
			bullet.setDirectionPlayer(this);
			bullet.shootBullet();
			
			
			/*
			if(bullet.bulletRectangle.x > 1280) { 
				poolBullets.free(bullet);
				activeBullets.removeValue(bullet, true);
				System.out.println("Bullet went outside area");
			}
			
			/*if(bullet.bulletRectangle.y < 0) {
				poolBullets.free(bullet);
				activeBullets.removeValue(bullet, true);
				System.out.println("Bullet went outside area");
			}*/

			
		}
		
		
					
	}
	
	public void updatePlayer() {
		
		
		playerSprite.setAlpha(alphaVal);
		
		if(alphaVal > 1)
				alphaVal = 1;
	
		if(health < 100) {
			if(active == false) {
			health += Gdx.graphics.getDeltaTime() * 2.5f;
			}
		}
		
				
		speedx = (float)Math.cos(Math.toRadians(playerSprite.getRotation())) ;
		speedy = -(float)Math.sin(Math.toRadians(playerSprite.getRotation()));
		//System.out.println(speedx + "\t" + speedy);
		
		
		/*
		if(bullet.bulletRectangle.x > 500) { 
				poolBullets.free(bullet);
				activeBullets.removeValue(bullet, true);
				System.out.println("Bullet went outside area");
			}
			*/
		
		//If player tries to goes out of bounds of the screen then move back
		if(active == true) {
		if(playerBox.x > 1280 ) playerBox.x = 1280;
		if(playerBox.x < 0 ) playerBox.x = 0 ;
		if(playerBox.y > 720 ) playerBox.y = 720 ;
		if(playerBox.y < 0 ) playerBox.y = 0 ;
		
		//playerTextureRegion.setRegionX(32);
		
		} else {
			
		//playerTextureRegion.setRegion(32, 0, 32, 32);
		//playerSprite = new Sprite(playerTextureRegion);

		//playerTextureRegion.setRegionX(0);

		}
		for(Bullet bullet : activeBullets) {
			if(bullet.bulletRectangle.x > 1280 || bullet.bulletRectangle.x < 0) { 
				//bullet.isShot = false;
				poolBullets.free(bullet);
				activeBullets.removeValue(bullet, true);
				//System.out.println("Bullet went outside area");
			}
			}
			
			for(Bullet bullet : activeBullets) {
			if(bullet.bulletRectangle.y > 720 || bullet.bulletRectangle.y < 0) { 
				//bullet.isShot = false;
				poolBullets.free(bullet);
				activeBullets.removeValue(bullet, true);
				//System.out.println("Bullet went outside area");
			}
			}
	
		
		}
	
	
	public void CheckInjury(Enemy enemy) {
		// TODO Auto-generated method stub
		
		//enemySprite.setAlpha(1000);
		for(Bullet bullet : enemy.activeBullets) {
			if(active == true) {
			if(bullet.bulletRectangle.overlaps(playerBox)) {
				if(((System.currentTimeMillis() - startTimeFlicker)/100) >= 1f) {
					alphaVal = 1000;
					health --;
			}
	
				if(((System.currentTimeMillis() - startTimeFlicker)/100) >= 1.1f) {
					alphaVal = 1;
					startTimeFlicker = System.currentTimeMillis();
					health --;
			}
		
			}
			}
		}
				
		}
	
	public void setTextureRegion() {
		if(active == true) {
			playerTextureRegion.setRegion(0, 0, 32, 32);
			playerSprite = new Sprite(playerTextureRegion);
		} else {
			playerTextureRegion.setRegion(32, 0, 32, 32);
			playerSprite = new Sprite(playerTextureRegion);

		}
	}
	
	
	public void disposePlayer() {
		playerTexture.dispose();
		playerBulletTexture.dispose();
		
		for(int x = 0; x < poolBullets.getFree(); x++) {
			Bullet bullet = poolBullets.obtain();
			activeBullets.add(bullet);
		}
		
		for(Bullet bullet : activeBullets) {
			bullet.disposeBullet();
			activeBullets.removeValue(bullet, true);
			poolBullets.free(bullet);
		}
		
		sound.dispose();
		
	}
	
	
}