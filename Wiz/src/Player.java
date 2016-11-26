import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.util.ResourceLoader;

public class Player {
	
	
	
	public float worldX = 0;
	public float worldY = 64;
	
	public float teleportedWorldX;
	public float teleportedWorldY;
	
	public float playerWidth = 32;
	public float playerHeight = 32;
	
	private boolean moving;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	
	private float moveSpeed = 2f;
	
	private boolean running;
	private boolean teleporting;
	
	private boolean allowedToMove = true;
	
	ResourceLoader loader = new ResourceLoader();
	
	//Image files for Animations
	SpriteSheet spritesheet;
	
	ArrayList<Image[]> animations = new ArrayList<Image[]>();
	
	
	Image[] moveUpImages = new Image[16];
	Image[] moveDownImages = new Image[16];
	Image[] moveLeftImages = new Image[16];
	Image[] moveRightImages = new Image[16];
	
	Image[] teleportPowerImages = new Image[16];
	Image[] teleportExplosionImages = new Image[19];
	
	Animation moveUpAnimation;
	Animation moveDownAnimation;
	Animation moveLeftAnimation;
	Animation moveRightAnimation;
	
	Animation teleportPowerAnimation;
	Animation teleportExplosionAnimation;
	
	
	private boolean doTeleportAnimation;
	boolean flagForAnimationStop = false;
	private boolean teleportLoop;
	protected boolean animateTeleportExplosion;
	
	
	public Rectangle bounds = new Rectangle(worldX, worldY, playerWidth - 8, playerHeight - 8);
	
	CustomFireEmitter fe;
	ParticleSystem particleSystem;
	
	
	Ability teleportAbility = new Ability();
	Ability fireAbility = new Ability();
	private boolean isInCollision;
	
	/*
	Image[] attackAnimationImages = new Image[2];
	Image[] deadAnimationImages = new Image[3];
	Image[] hurtAnimationImages = new Image[2];
	Image[] idleAnimationImages = new Image[3];
	Image[] jumpAnimationImages = new Image[2];
	Image[] runAnimationImages = new Image[3];
	Image[] walkAnimationImages = new Image[3];
	
	
	//Animation
	Animation attackAnimation;
	Animation deadAnimation;
	Animation hurtAnimation;
	Animation idleAnimation;
	Animation jumpAnimation;
	Animation runAnimation;
	Animation walkAnimation;
	*/
	
	public Player(){
		loadAnimations();
		otherInit();
		
	}
	
	private void otherInit() {
		 fe = new CustomFireEmitter(100, 300, 2);
		 particleSystem = new ParticleSystem("res/particles/orangePixel.png", 2000);
		 particleSystem.addEmitter(fe);
		 particleSystem.setDefaultImageName("res/particles/orangePixel.png");
		
	}

	private void loadAnimations(){
		try {
			spritesheet = new SpriteSheet("res/player/wizard/spritesheet.png", 64, 64);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		
		//This is the shittiest, filthy, piece of ass code i have ever written \/ \/ \/ \/
		
		int i = 0;
		//moveDown Images
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < 4; c++){
				moveDownImages[i] = spritesheet.getSubImage(r, c).getScaledCopy(0.5f);
				i++;
			}
		}
		
		i = 0;
		//moveUp Images
		for(int r = 0; r < 4; r++){
			for(int c = 4; c < 8; c++){
				moveUpImages[i] = spritesheet.getSubImage(r, c).getScaledCopy(0.5f);
				i++;
			}
		}
		
		i = 0;
		//moveLeft Images
		for(int r = 4; r < 8; r++){
			for(int c = 0; c < 4; c++){
				moveLeftImages[i] = spritesheet.getSubImage(r, c).getScaledCopy(0.5f);
				i++;
			}
		}
		
		i = 0;
		//moveRight Images
		for(int r = 4; r < 8; r++){
			for(int c = 0; c < 4; c++){
				moveRightImages[i] = spritesheet.getSubImage(r, c).getFlippedCopy(true, false).getScaledCopy(0.5f);
				i++;
			}
		}
		
		
		i = 0;
		//teleport power Images
		for(int r = 4; r < 8; r++){
			for(int c = 4; c < 8; c++){
				teleportPowerImages[i] = spritesheet.getSubImage(c, r).getScaledCopy(0.5f);
				i++;
			}
		}
		
		for(int j = 0; j < 19; j++){
			try {
				teleportExplosionImages[j] = new Image("res/FX/fx10_blackExplosion/smoke_black_1_19_" + (j+1) + ".png").getScaledCopy(1f);
				//System.out.println(teleportExplosionImages[j]);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		//End of shitty code
		
		moveUpAnimation = new Animation(moveUpImages, 100);
		moveDownAnimation = new Animation(moveDownImages, 100);
		moveLeftAnimation = new Animation(moveLeftImages, 100);
		moveRightAnimation = new Animation(moveRightImages, 100);
		
		teleportPowerAnimation = new Animation(teleportPowerImages, 75);
		teleportExplosionAnimation = new Animation(teleportExplosionImages, 75);
		
		moveUpAnimation.setAutoUpdate(false);
		moveDownAnimation.setAutoUpdate(false);
		moveLeftAnimation.setAutoUpdate(false);
		moveRightAnimation.setAutoUpdate(false);
		
		teleportPowerAnimation.setAutoUpdate(false);
		teleportExplosionAnimation.setAutoUpdate(false);
		
	}
	
	public boolean[] checkMovingCollision(ArrayList<Rectangle> blocks){  // 0 = up   1 = down   2 = left    3 = right  4 = no collision

		 
		Rectangle tempBounds;
		boolean[] hits = new boolean[5];
		
		//Check up first
		tempBounds = new Rectangle(bounds.getX(), bounds.getY() - moveSpeed, bounds.getWidth(), bounds.getHeight());
			for(Rectangle ret : blocks) {
				if(tempBounds.intersects(ret)) {
					setInCollision(true);
					hits[0] = true;
			}
		}
			
			
		//Check down	
			tempBounds = new Rectangle(bounds.getX(), bounds.getY() + moveSpeed, bounds.getWidth(), bounds.getHeight());
			for(Rectangle ret : blocks) {
				if(tempBounds.intersects(ret)) {
					setInCollision(true);
					hits[1] = true;
			}
		}
			
			//Check left	
			tempBounds = new Rectangle(bounds.getX() - moveSpeed, bounds.getY(), bounds.getWidth(), bounds.getHeight());
			for(Rectangle ret : blocks) {
				if(tempBounds.intersects(ret)) {
					setInCollision(true);
					hits[2] = true;
			}
		}
			
			//Check right	
			tempBounds = new Rectangle(bounds.getX() + moveSpeed, bounds.getY() - moveSpeed, bounds.getWidth(), bounds.getHeight());
			for(Rectangle ret : blocks) {
				if(tempBounds.intersects(ret)) {
					setInCollision(true);
					hits[3] = true;
			}
				
				
		}
			hits[4] = true;
			return hits;
			
	}
	
	public void update(GameContainer c, int delta, boolean inCollision){
		

		
		bounds.setX(worldX);
		bounds.setY(worldY);
		
		teleportAbility = UserInterface.abilityBar.get(0).getAttatchedAbility();
		fireAbility = UserInterface.abilityBar.get(1).getAttatchedAbility();
		
		
		if(!movingUp && !movingDown && !movingLeft && !movingRight && !teleporting){
			moving = false;
		}else{
			moving = true;
		}
		
		if(teleporting){
			
			allowedToMove = false;
			
			System.out.println(c.getInput().getAbsoluteMouseX()+ " " + c.getInput().getAbsoluteMouseY());
			teleport(c.getInput().getMouseX(), c.getInput().getMouseY());
			
		}
		
		
		if(!teleporting){
			boolean[] collision = checkMovingCollision(WizGame.getBlockingElements());
		if(movingUp && collision[0] != true){
			worldY-= 0.5 * delta;
		}
		if(movingDown && collision[1] != true){
			worldY+= 0.5 * delta;
		}
		if(movingLeft && collision[2] != true){
			worldX-= 0.5 * delta;
		}
		if(movingRight && collision[3] != true){
			worldX+= 0.5 * delta;
		}
		}
		
		if(fireAbility.getActivated()){
			particleSystem.update(delta);
		}else{
			
			particleSystem.releaseAll(fe);
		}
		
		animate(delta);
		
	}
	
	
	public void render(Graphics g){
		
		
		
		if(!moving && !teleporting){
			moveDownAnimation.draw(worldX, worldY);
			moveDownAnimation.stop(); // this is all cheaty
		}else{
			moveDownAnimation.start();
		}
		if(allowedToMove){
		if(movingUp){
			moveUpAnimation.draw(worldX, worldY);
		} else
		if(movingDown){
			moveDownAnimation.draw(worldX, worldY);
		} else
		if(movingLeft){
			moveLeftAnimation.draw(worldX, worldY);
		}else 
		if(movingRight){
			moveRightAnimation.draw(worldX, worldY);
		}
		}
		
		if(fireAbility.getActivated()){
			particleSystem.render();
		}
		
		if(doTeleportAnimation){
			teleportPowerAnimation.draw(worldX, worldY);
		
		}
		if(animateTeleportExplosion){  //Look into formatting this better
			teleportExplosionAnimation.draw(teleportedWorldX - 12, teleportedWorldY - 4); //Idk why -12 and -4 looks better I think it centers the explosion
		}
		
	}
	
	public void animate(int delta){
		
		if(allowedToMove){
			if(movingUp){
				moveUpAnimation.update(delta);
			} else
			if(movingDown){
				moveDownAnimation.update(delta);;
			} else
			if(movingLeft){
				moveLeftAnimation.update(delta);
			}else 
			if(movingRight){
				moveRightAnimation.update(delta);
			}
			}
		
		if(doTeleportAnimation){
		teleportPowerAnimation.update(delta);

			moveUpAnimation.stop();
			moveDownAnimation.stop();
			moveLeftAnimation.stop();
			moveRightAnimation.stop();


			//System.out.println(teleportPowerAnimation.getFrame());

			
			
			if(teleportPowerAnimation.getFrame() == 15){
				flagForAnimationStop = true;
				//System.out.println("flagging a stop");
			}
			//System.out.println(teleportPowerAnimation.getFrame() == 0);
		//	System.out.println(flagForAnimationStop);
			//System.out.println((teleportPowerAnimation.getFrame() == 0 && flagForStop) + " da");
			if(teleportPowerAnimation.getFrame() == 0 && flagForAnimationStop){
				
				System.out.println("stopping");
				doTeleportAnimation = false;
				
				moveUpAnimation.start();
				moveDownAnimation.start();
				moveLeftAnimation.start();
				moveRightAnimation.start();

				teleportPowerAnimation.setCurrentFrame(0);
				 flagForAnimationStop = false;




			}
		}
		
		if(animateTeleportExplosion){
		//System.out.println("here");
			teleportExplosionAnimation.update(delta);
			if(teleportExplosionAnimation.getFrame() == 18){
				flagForAnimationStop = true;
			}
			
			if(teleportExplosionAnimation.getFrame() == 0 && flagForAnimationStop){
				animateTeleportExplosion = false;
				teleportExplosionAnimation.setCurrentFrame(0);
				flagForAnimationStop = false;
			}
		}

	}

	
	public void teleport(float x, float y){
		//The goal is to make the illusion we dissapear, so we dont want to move the player x or y until about 3 seconds
		
		
		
		if(!teleportLoop){
			
			doTeleportAnimation = true;
			teleportLoop = true;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				System.out.println("here");
				worldX = ((x - (playerWidth / 2)) / WizGame.scale) + WizGame.screenScrollX; //Change to half of player width
				worldY = ((y - (playerHeight / 2)) / WizGame.scale) + WizGame.screenScrollY; //Change to half of player height
				
				teleportedWorldX = worldX;
				teleportedWorldY = worldY;
				
				animateTeleportExplosion = true;
				allowedToMove = true;
				teleporting = false;
				teleportLoop = false;
				
				t.cancel();

				
				
			}
		}, 1200, 1); // wait 1500ms to do explosion effect then addition 100ms for animation to finish
		t.purge();
		}else{
			return;
		}
	}

	public float getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public float getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isTeleporting() {
		return teleporting;
	}

	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void setInCollision(boolean b) {
		this.isInCollision = b;
		
	}

	public float getMovementSpeed() {
		// TODO Auto-generated method stub
		return moveSpeed;
	}
	
	public void setMoveSpeed(float speed){
		moveSpeed = speed;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//teleportPowerAnimation.setLooping(false);

	/*
	animations.add(attackAnimationImages);
	animations.add(deadAnimationImages);
	animations.add(hurtAnimationImages);
	animations.add(idleAnimationImages);
	animations.add(jumpAnimationImages);
	animations.add(runAnimationImages);
	animations.add(walkAnimationImages);
	
	
	
	for(int i = 0; i < attackAnimationImages.length; i++){
		try {
			attackAnimationImages[i] =  new Image("res/player/wizard/attack_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	for(int i = 0; i < deadAnimationImages.length; i++){
		try {
			deadAnimationImages[i] =  new Image("res/player/wizard/dead_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	for(int i = 0; i < hurtAnimationImages.length; i++){
		try {
			hurtAnimationImages[i] =  new Image("res/player/wizard/hurt_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	
	
	
	for(int i = 0; i < idleAnimationImages.length; i++){
			try {
				idleAnimationImages[i] =  new Image("res/player/wizard/idle_" + (i + 1) +".png").getScaledCopy(32,  32);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		
	}
	
	for(int i = 0; i < jumpAnimationImages.length; i++){
		try {
			jumpAnimationImages[i] =  new Image("res/player/wizard/jump_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	for(int i = 0; i < runAnimationImages.length; i++){
		try {
			runAnimationImages[i] =  new Image("res/player/wizard/run_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	for(int i = 0; i < walkAnimationImages.length; i++){
		try {
			walkAnimationImages[i] =  new Image("res/player/wizard/walk_" + (i + 1) +".png").getScaledCopy(32,  32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	
}
	
	
	
	attackAnimation = new Animation(attackAnimationImages, 500);
	deadAnimation = new Animation(deadAnimationImages, 500);
	hurtAnimation = new Animation(hurtAnimationImages, 500);
	idleAnimation = new Animation(idleAnimationImages, 500);
	jumpAnimation = new Animation(jumpAnimationImages, 500);
	runAnimation = new Animation(runAnimationImages, 500);
	walkAnimation = new Animation(walkAnimationImages, 500);
	
	 // loader.getResource("res/player/wizard/idle1.png");
	
	*/
}
