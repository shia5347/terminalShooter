package com.github.shia5347.ironcloset;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/*Remove pooling*/

public class Arena {

	public final float playerSpeed;
	public final float playerRotationSpeed;
	
	
	private final int MAXPLAYEROBJECTS = 2;
	
		
	Boot game;
	Player activePlayer;
	OrthographicCamera camera;
	Player currentPlayer;
	Texture pivotSpawnTexture;
	Sprite pivotSpawnSprite;
	BlueShip blueship;
	HealthBar healthBar;
	Sound switchPlayer;
	
	private float randomAngle;
	private int playerspawnFinishAt;
	private int blueshipspawnFinishAt,blueshipsmallspawnFinishAt,blueshiptwirlyspawnFinishAt;
	private int blueshipspawnMin,blueshipspawnMax;
	private int blueshipsmallspawnMin,blueshipsmallspawnMax;
	private int blueshiptwirlyspawnMin,blueshiptwirlyspawnMax;

	//private float spawnX;
	//private float spawnY;
	public long startTime;
	private long playerspawnStartTime;
	private long blueshipspawnStartTime,blueshipsmallspawnStartTime,blueshiptwirlyspawnStartTime;
	private long difficultyStartTime;
	
	private long playerspawnTimer;
	private long blueshipspawnTimer,blueshipsmallspawnTimer,blueshiptwirlyspawnTimer;
	private long difficultyTimer;
	
	private Random random;
	private boolean hasPassedInScreen;
	
	private boolean firstInstance = true;
	private boolean firstInstanceColourChange = false;
	
	public boolean running = true;

	
	//FreeTypeFontGenerator generator;
	BitmapFont font;
	
	
	public static float highscore = 0;
	
		public Arena(float playerSpeed, float playerRotationSpeed, OrthographicCamera camera,Boot game) {
		
			healthBar = new HealthBar(game,5,5);
			
			font = new BitmapFont();
			
			this.playerSpeed = playerSpeed;
			this.playerRotationSpeed = playerRotationSpeed;
			
			this.game = game;
			this.camera = camera;
			
			pivotSpawnTexture = new Texture(Gdx.files.internal("assets/Pivot.png"));
			pivotSpawnSprite = new Sprite(pivotSpawnTexture);
			
			pivotSpawnSprite.setAlpha(0);
			
			pivotSpawnSprite.setX(1280/2 - 8/2);
			pivotSpawnSprite.setY(720/2 - 8/2);
			
			random = new Random();
			
			activePlayer = poolPlayerObjects.obtain();
			activePlayerObjects.add(activePlayer);
			
			playerspawnStartTime = System.currentTimeMillis();
			blueshipspawnStartTime = System.currentTimeMillis();
			blueshipsmallspawnStartTime = System.currentTimeMillis();
			blueshiptwirlyspawnStartTime = System.currentTimeMillis();
			difficultyStartTime = System.currentTimeMillis();
			
			blueshipspawnMax = 20;
			blueshipspawnMin = 10;
			
			blueshipsmallspawnMax = 10;
			blueshipsmallspawnMin = 5;
			
			blueshiptwirlyspawnMax = 30;
			blueshiptwirlyspawnMin = 20;
		
			switchPlayer = Gdx.audio.newSound(Gdx.files.internal("assets/pianoSound.wav"));
		
			startTime = System.currentTimeMillis();
			
			//generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/NCSRogueland.ttf"));
			//FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			//parameter.size = 32;
			
			//font = generator.generateFont(parameter);
			//generator.dispose();
			
			//blueship = poolBlueShips.obtain();
	}
		
		private final Array<Player> inactivePlayerObjects = new Array<Player>();
		private final Array<Player> activePlayerObjects = new Array<Player>();
		
		private final Pool<Player> poolPlayerObjects = new Pool<Player>() {
		
		@Override
		protected Player newObject() {
			// TODO Auto-generated method stub
			return new Player(playerSpeed,playerRotationSpeed,game);
		}
		
	};
	
	//Enemies
	private final Array<BlueShip> activeBlueShips = new Array<BlueShip>();
	private final Pool<BlueShip> poolBlueShips = new Pool<BlueShip>() {
		@Override
		protected BlueShip newObject() {
			// TODO Auto-generated method stub
			return new BlueShip(2,500,0.5f,200,1.2f,game); 
		}
		
	};
	
	private final Array<BlueShip> activeBlueShipsSmall = new Array<BlueShip>();
	private final Pool<BlueShip> poolBlueShipsSmall = new Pool<BlueShip>() {
		@Override
		protected BlueShip newObject() {
			// TODO Auto-generated method stub
			return new BlueShip(2.5f,500,0.1f,100,0.5f,game); 
		}
		
	};

	private final Array<BlueShip> activeBlueShipsTwirly = new Array<BlueShip>();
	private final Pool<BlueShip> poolBlueShipsTwirly = new Pool<BlueShip>() {
		@Override
		protected BlueShip newObject() {
			// TODO Auto-generated method stub
			return new BlueShip(0.5f,500,2f,100,1f,game); 
		}
		
	};
	
	
	public void Update() {
		
		drawLoosing();
		
		healthBar.updateHealthBar(activePlayer.health);
		
		font.draw(game.batch,"Highscore: "+highscore,0,720-2);
		
			playerspawnFinishAt = random.nextInt((5-2) + 1) + 2;
			blueshipspawnFinishAt = random.nextInt((blueshipspawnMax-blueshipspawnMin) + 1) + blueshipspawnMin;
			blueshipsmallspawnFinishAt = random.nextInt((blueshipsmallspawnMax-blueshipsmallspawnMin)+1)+blueshipsmallspawnMin;
			blueshiptwirlyspawnFinishAt = random.nextInt((blueshiptwirlyspawnMax-blueshiptwirlyspawnMin) + 1) + blueshiptwirlyspawnMin;
			
		if(activePlayer.health < 0) {
			running = false;
			disposeArena();
		}
	
		if(firstInstanceColourChange == false) {
			activePlayer.setTextureRegion();
			firstInstanceColourChange = true; // Default instance has its colour changed
		}
	
		activePlayer.updatePlayer();
		activePlayer.drawPlayer();
		activePlayer.processInput();
			
		if(running == true) {
		//BlueShip stuff
			
			for(BlueShip blueship : activeBlueShips) {
				blueship.updateEnemy(activePlayer);
				blueship.drawEnemy();
				
				//System.out.println("EnemyTexture: "+blueship.enemyTexture);
				//System.out.println("EnemyBulletTexture: "+blueship.enemyBulletTexture);
				
				if(blueship.health < 0) {
					//blueship.killed.play();
					blueship.enemyRectangle.x = 1280*8;
				}
				
				if(blueship.toDispose() == true) {
					activeBlueShips.removeValue(blueship, true);
					poolBlueShips.free(blueship);
				}
				
				
				activePlayer.CheckInjury(blueship);
				
				
				
				}
			
			//Small blueship stuff
			
			for(BlueShip blueship : activeBlueShipsSmall) {
				blueship.updateEnemy(activePlayer);
				blueship.drawEnemy();
				
				//System.out.println("EnemyTexture: "+blueship.enemyTexture);
				//System.out.println("EnemyBulletTexture: "+blueship.enemyBulletTexture);
				
				
				if(blueship.health < 0) {
					//blueship.killed.play();
					blueship.enemyRectangle.x = 1280*8;
				}
				
				if(blueship.toDispose() == true) {
					activeBlueShipsSmall.removeValue(blueship, true);
					poolBlueShipsSmall.free(blueship);
				}
				
				
				activePlayer.CheckInjury(blueship);
				
				
				}
			
				//twirly blueship stuff
			
				for(BlueShip blueship : activeBlueShipsTwirly) {
				blueship.updateEnemy(activePlayer);
				blueship.drawEnemy();
				
				//System.out.println("EnemyTexture: "+blueship.enemyTexture);
				//System.out.println("EnemyBulletTexture: "+blueship.enemyBulletTexture);
				
				if(blueship.health < 0) {
					//blueship.killed.play();
					blueship.enemyRectangle.x = 1280*8; // Temporary fix
				}	
				
				if(blueship.toDispose() == true) {
					activeBlueShipsTwirly.removeValue(blueship, true);
					poolBlueShipsTwirly.free(blueship);
				}
				
				
				activePlayer.CheckInjury(blueship);
				
				
				}
		}
				
			blueshipspawnTimer = (System.currentTimeMillis() - blueshipspawnStartTime)/1000; 
			
			if(blueshipspawnTimer > blueshipspawnFinishAt) {
				
				//System.out.println("Enemy spawning...");
				BlueShip bs = poolBlueShips.obtain();
				
							
				bs.spawnX = (float)Math.cos(Math.toRadians(pivotSpawnSprite.getRotation())) ;
				bs.spawnY = -(float)Math.sin(Math.toRadians(pivotSpawnSprite.getRotation())) ; 
				
				bs.pos.x = pivotSpawnSprite.getX() + 1280  * bs.spawnX;
				bs.pos.y = pivotSpawnSprite.getY()  + 720  * bs.spawnY/** spawnY*/;	
				
				activeBlueShips.add(bs);

				//Reset timer
				blueshipspawnStartTime = System.currentTimeMillis();
				blueshipspawnTimer = 0;
				//blueshipspawnFinishAt = random.nextInt((10-5) + 1) + 5;
				
			}
			blueshipsmallspawnTimer = (System.currentTimeMillis() - blueshipsmallspawnStartTime)/1000; 

		if(blueshipsmallspawnTimer > blueshipsmallspawnFinishAt) {
				
				//System.out.println("Enemy spawning...");
				BlueShip bs = poolBlueShipsSmall.obtain();
				
							
				bs.spawnX = (float)Math.cos(Math.toRadians(pivotSpawnSprite.getRotation())) ;
				bs.spawnY = -(float)Math.sin(Math.toRadians(pivotSpawnSprite.getRotation())) ; 
				
				bs.pos.x = pivotSpawnSprite.getX() + 1280  * bs.spawnX;
				bs.pos.y = pivotSpawnSprite.getY()  + 720  * bs.spawnY/** spawnY*/;	
				
				activeBlueShipsSmall.add(bs);

				//Reset timer
				blueshipsmallspawnStartTime = System.currentTimeMillis();
				blueshipsmallspawnTimer = 0;
				//blueshipsmallspawnFinishAt = random.nextInt((80-30) + 1) + 30;
				
			}
	
		blueshiptwirlyspawnTimer = (System.currentTimeMillis() - blueshiptwirlyspawnStartTime)/1000; 
		
			if(blueshiptwirlyspawnTimer > blueshiptwirlyspawnFinishAt) {
				
				//System.out.println("Enemy spawning...");
				BlueShip bs = poolBlueShipsTwirly.obtain();
				
							
				bs.spawnX = (float)Math.cos(Math.toRadians(pivotSpawnSprite.getRotation())) ;
				bs.spawnY = -(float)Math.sin(Math.toRadians(pivotSpawnSprite.getRotation())) ; 
				
				bs.pos.x = pivotSpawnSprite.getX() + 1280  * bs.spawnX;
				bs.pos.y = pivotSpawnSprite.getY()  + 720  * bs.spawnY/** spawnY*/;	
				
				activeBlueShipsTwirly.add(bs);

				//Reset timer
				blueshiptwirlyspawnStartTime = System.currentTimeMillis();
				blueshiptwirlyspawnTimer = 0;
				//blueshiptwirlyspawnFinishAt = random.nextInt((60-50) + 1) + 50;
				
			}
			
		for(Player player : inactivePlayerObjects) {
		
		player.playerBox.x -= Gdx.graphics.getDeltaTime() * (500 * player.spawnX) ;
		player.playerBox.y -= Gdx.graphics.getDeltaTime() * (500 * player.spawnY) ;
		player.playerSprite.rotate(player.rotSpeed * Gdx.graphics.getDeltaTime());
	
		
			player.updatePlayer();
			player.drawPlayer();
			
		//if(player.active == true) 
			player.active = false;
			
			for(BlueShip bs : activeBlueShips) {
				
			player.CheckInjury(bs);
			if(player.health < 0) {
				player.disposePlayer();
				inactivePlayerObjects.removeValue(player, true);
				
				playerspawnStartTime = System.currentTimeMillis();
				playerspawnTimer = 0;	
				
			}
			
			}
			
		//If the player object is out of bounds then free it!
		
			
			if(player.hasPassedScreen == true) {
			
		if(player.playerBox.x > 1280  || player.playerBox.x < 0 ) {
			player.spawnX *= -1; // Switch directions
		}
		
		
		if(player.playerBox.y > 720  || player.playerBox.y < 0 ) {
			player.spawnY *= -1; // Switch directions
		}
		
		
			}
		
		}	
		
		if(running == true) {
		
		for(Bullet bullet : activePlayer.activeBullets) {
		bullet.drawBullet();
		bullet.updateBullet();
		
		}
		
		}
		
		pivotSpawnSprite.draw(game.batch);
		
		//Spawn players and stuff
		randomAngle = 5000 * Gdx.graphics.getDeltaTime();
		
		pivotSpawnSprite.rotate(randomAngle);
		
		playerspawnTimer = (System.currentTimeMillis() - playerspawnStartTime)/1000;
		
		//System.out.println("Spawnx: "+spawnX);
		//System.out.println("Spawny: "+spawnY*500);

		if(inactivePlayerObjects.size < MAXPLAYEROBJECTS) {
		if(playerspawnTimer == playerspawnFinishAt)  {
		
		//System.out.println("Player object spawning...");	
		
		//System.out.println(poolPlayerObjects.getFree());
		
		currentPlayer = poolPlayerObjects.obtain();
		
		currentPlayer.spawnX = (float)Math.cos(Math.toRadians(pivotSpawnSprite.getRotation())) ;
		currentPlayer.spawnY = -(float)Math.sin(Math.toRadians(pivotSpawnSprite.getRotation())) ; 
		

		currentPlayer.playerBox.x = pivotSpawnSprite.getX() + 1280  * currentPlayer.spawnX;
		currentPlayer.playerBox.y = pivotSpawnSprite.getY()  + 720  * currentPlayer.spawnY/** spawnY*/;
		
		currentPlayer.active = false;
		
		inactivePlayerObjects.add(currentPlayer); 
		
		//Reset timer
		playerspawnStartTime = System.currentTimeMillis();
		playerspawnTimer = 0;
		playerspawnFinishAt = random.nextInt((5-2) + 1) + 2;
		
		
		}
		
		}
		
		//This right here, I am worried that it may be expensive. May fix in future but right now I am in middle of a game jam
		//Switch player objects upon pressing enter (THEME: Strong Together)
		for(Player player : inactivePlayerObjects) {
				if(player.playerBox.x < 1280 && player.playerBox.x > 0) {
				if(player.playerBox.y < 720 && player.playerBox.y > 0) {
					player.hasPassedScreen = true;
					if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && player.health > 0) {
					
					switchPlayer.play();
												
						if(firstInstance == true) {
							activePlayer.spawnX = (float)Math.cos(Math.toRadians(pivotSpawnSprite.getRotation())) ;
							activePlayer.spawnY = -(float)Math.sin(Math.toRadians(pivotSpawnSprite.getRotation())) ; 
						}
						
						
						inactivePlayerObjects.add(activePlayer);
						activePlayer.active = false;
						
						activePlayer.setTextureRegion();
						
						activePlayer = player;
						

						inactivePlayerObjects.removeValue(player, true);

						activePlayer.active = true;
						
						activePlayer.setTextureRegion();
						
						firstInstance = false;

						break;
												
					}
						
					}
				}
		}
		
		//The difficulty increasing/decreasing
		difficultyTimer = (System.currentTimeMillis() - difficultyStartTime)/1000;
		
	
		if(difficultyTimer > 10) {
			
			if(blueshipspawnFinishAt > 3) {
				blueshipspawnMax --;
				blueshipspawnMin --;
			}
			
			
			if(blueshipsmallspawnFinishAt > 10) {
				blueshipsmallspawnMax --;
				blueshipsmallspawnMin --;
			}
			
			if(blueshiptwirlyspawnFinishAt > 10) {
				blueshiptwirlyspawnMax --;
				blueshiptwirlyspawnMin --;
			}
			
				
			//Reset the timer
			difficultyTimer = 0;
			difficultyStartTime = System.currentTimeMillis();
			

			
		}	
	}	
		
	
	public void disposeArena() {
		
		//System.out.println("Disposing players...");
			activePlayer.disposePlayer();
			activePlayerObjects.removeValue(activePlayer,true);
			poolPlayerObjects.free(activePlayer);
		
		for(Player player : inactivePlayerObjects) {
			//System.out.println("Disposing players");
			player.disposePlayer();
			inactivePlayerObjects.removeValue(player, true);
			poolPlayerObjects.free(player);
		}
		
		for(BlueShip blueship : activeBlueShips) {
			blueship.disposeEnemy();
			activeBlueShips.removeValue(blueship, true);
			poolBlueShips.free(blueship);
		}
	
		for(BlueShip blueship : activeBlueShipsSmall) {
			blueship.disposeEnemy();
			activeBlueShips.removeValue(blueship, true);
			poolBlueShips.free(blueship);
		}	
		
		for(BlueShip blueship : activeBlueShipsTwirly) {
			blueship.disposeEnemy();
			activeBlueShips.removeValue(blueship, true);
			poolBlueShips.free(blueship);
		}
		
		font.dispose();
		healthBar.dispose();
		switchPlayer.dispose();
		
	}
	
	
	public void drawLoosing() {
		if(running == false) {
			//font.draw(game.batch,"You lost",1280/2,720/2);
		}
	}
	
	

}