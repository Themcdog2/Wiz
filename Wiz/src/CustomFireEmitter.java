import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class CustomFireEmitter implements ParticleEmitter{
	/** The x coordinate of the center of the fire effect */
	private int x;
	/** The y coordinate of the center of the fire effect */
	private int y;
	
	private int oldX;
	private int oldY;
	
	/** The particle emission rate */
	private int interval = 50;
	/** Time til the next particle */
	private int timer;
	/** The size of the initial particles */
	private float size = 40;
	
	Vector2f particlePosition = new Vector2f();
	Vector2f mousePosition = new Vector2f();
	Vector2f particleDirection = new Vector2f();
	
	/**
	 * Create a default fire effect at 0,0
	 */
	public CustomFireEmitter() {
	}

	/**
	 * Create a default fire effect at x,y
	 * 
	 * @param x The x coordinate of the fire effect
	 * @param y The y coordinate of the fire effect
	 */
	public CustomFireEmitter(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Create a default fire effect at x,y
	 * 
	 * @param x The x coordinate of the fire effect
	 * @param y The y coordinate of the fire effect
	 * @param size The size of the particle being pumped out
	 */
	public CustomFireEmitter(int x, int y, float size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	/**
	 * @see org.newdawn.slick.particles.ParticleEmitter#update(org.newdawn.slick.particles.ParticleSystem, int)
	 */
	public void update(ParticleSystem system, int delta) {
	//	system.setPosition(WizGame.getPlayer().worldX - 75, WizGame.getPlayer().worldY - 275);
		
		int moveSpeed = (int)WizGame.getPlayer().getMovementSpeed();
		
		
		/*
		if(WizGame.getPlayer().isMovingUp()){
			this.x = (int)WizGame.getPlayer().worldX + 20;
			this.y = (int)WizGame.getPlayer().worldY + 14 - 10;
			}else if(WizGame.getPlayer().isMovingDown()){
				this.x = (int)WizGame.getPlayer().worldX + 8;
				this.y = (int)WizGame.getPlayer().worldY + 14 - 10;
			}else if(WizGame.getPlayer().isMovingLeft()){
				this.x = (int)WizGame.getPlayer().worldX;
				this.y = (int)WizGame.getPlayer().worldY + 14;
			}else if(WizGame.getPlayer().isMovingRight()){
				this.x = (int)WizGame.getPlayer().worldX + 32;
				this.y = (int)WizGame.getPlayer().worldY + 14;
			}else{
				this.x = (int)WizGame.getPlayer().worldX + 8;
				this.y = (int)WizGame.getPlayer().worldY + 14;
			}
		*/
		this.x = (int)WizGame.getPlayer().worldX + 8;
		this.y = (int)WizGame.getPlayer().worldY + 14;
		
		
		//System.out.println(WizGame.getPlayer().worldX + " " + WizGame.getPlayer().worldY + " Player");
		System.out.println(system.getPositionX() + " " + system.getPositionY() + " Sys");
		
		timer -= delta;
		if (timer <= 0) {
			for(int i = 0; i < 100; i++){
			timer = interval;
			Particle p = system.getNewParticle(this, 1000);
			p.setColor(1, 1, 1, 0.5f * delta);
			p.setPosition(x, y);
			p.setSize(size);
			
			float vx = 0, vy = 0;
			boolean oneDirection; //Lol
			boolean playerUp = WizGame.getPlayer().isMovingUp()
				 , playerDown = WizGame.getPlayer().isMovingDown()
				 , playerLeft = WizGame.getPlayer().isMovingLeft()
				 , playerRight = WizGame.getPlayer().isMovingRight();
			
			if((playerUp && playerLeft) || (playerUp && playerRight) || (playerUp && playerDown) || (playerDown && playerLeft) || (playerDown && playerRight) || (playerLeft && playerRight)){
				oneDirection = false;
			}else{
				oneDirection = true;
			}
			
			oneDirection=true;
			
			//System.out.println(oneDirection);
			
			
			//System.out.println((int)(Math.random() * 3));
			int currentRand = (int) (Math.random() * 2);
			
			/*
			 * Which direction is the player moving?
			 * And is the player moving in only one direction?
			 */
			
			
			if(true){
			
				if(currentRand == 0){
					if(Math.random() > 0.5){
						p.setVelocity((float)(Math.random() * 0.02), (float)(Math.random() * 0.025));
					}else{
						p.setVelocity((float)(-Math.random() * 0.02), (float)(-Math.random() * 0.025));
					}
				}else{
					if(Math.random() > 0.5){
						p.setVelocity((float)(Math.random() * 0.02), (float)(-Math.random() * 0.025));
					}else{
						p.setVelocity((float)(-Math.random() * 0.02), (float)(Math.random() * 0.025));
					}
				}
				
			}
			
			/*else{
			
			//------------------------------------------
			
			if(playerUp && oneDirection){
			
			if(currentRand == 0){
			vx = (float) ((Math.random() * 0.02f));
			}else{
				vx = (float) -((Math.random() * 0.02f));  //North
			}
			vy = (float) ((float) -0.1f + (-(Math.random() * 0.05f)));
			
			}
			//------------------------------------------
			/*
			if(playerUp && playerRight){
			 vx = (float) ((float) 0.1f + (0.02f + (Math.random() * 0.04f))); //North-East
			 vy = (float) ((float) -0.1f + (-(Math.random() * 0.05f)));
			
			}
			
			//------------------------------------------
			if(playerRight && oneDirection){
			
			if(currentRand == 0){
				vy = (float) ((Math.random() * 0.025f));
			}else{
				vy = (float) (-(Math.random() * 0.025f));   //East
			}
			vx = 0.1f + (float) (0.02f + (Math.random() * 0.04f));
			
			}
			//------------------------------------------
			/*
			if(playerDown && playerRight){
			
			 vx = (float) ((float) 0.1f + (0.02f + (Math.random() * 0.04f))); //South-East
			 vy = (float) ((float) 0.1f + ((Math.random() * 0.05f)));
			}
			
			//------------------------------------------
			if(playerDown && oneDirection){
			
			if(currentRand == 0){
			vx = (float) ((Math.random() * 0.02f));
			}else{
				vx = (float) -((Math.random() * 0.02f));  //South
			}
			vy = 0.1f + (float) ((Math.random() * 0.05f));
			
			}
			//------------------------------------------
			/*
			if(playerDown && playerLeft){
			 vx = (float) ((float) -0.1f + (0.02f - (Math.random() * 0.04f))); //South-West
			 vy = (float) ((float) 0.1f + ((Math.random() * 0.05f)));
			}
			
			//------------------------------------------
			if(playerLeft && oneDirection){
			
			if(currentRand == 0){
				vy = (float) ((Math.random() * 0.025f));
			}else{
				vy = (float) (-(Math.random() * 0.025f));   //West
			}
			vx = -0.1f + (float) -(0.02f + (Math.random() * 0.04f));
			
			}
			/*
			//------------------------------------------
			if(playerLeft && playerUp){
			 vx = (float) ((float) -0.1f + -(0.02f + (Math.random() * 0.04f))); //North-West
			 vy = (float) ((float) -0.1f + -((Math.random() * 0.05f)));
			//------------------------------------------
			 * 
			 
			
			p.adjustVelocity(vx,vy);
			}
			*/
			}
			
		}
		
		
	}

	/**
	 * @see org.newdawn.slick.particles.ParticleEmitter#updateParticle(org.newdawn.slick.particles.Particle, int)
	 */
	public void updateParticle(Particle particle, int delta) {
		if (particle.getLife() > 600) {
			//particle.adjustSize(0.07f * delta);
		} else {
			//particle.adjustSize(-0.04f * delta * (size / 40.0f));
		}

		particlePosition.set(particle.getX(), particle.getY());
		mousePosition.set(WizGame.getWorldMouseLocation());
		Vector2f.sub(mousePosition, particlePosition, particleDirection);
		
		
		
		
		
		
		if(WizGame.getPlayer().isMovingUp() && WizGame.getPlayer().isMovingRight()){
			particle.adjustPosition(0.5f * delta * delta, -0.5f * delta * delta);
		}else if(WizGame.getPlayer().isMovingUp() && WizGame.getPlayer().isMovingLeft()){
			particle.adjustPosition(-0.5f * delta * delta, -0.5f * delta * delta);
		}else if(WizGame.getPlayer().isMovingDown() && WizGame.getPlayer().isMovingRight()){
			particle.adjustPosition(0.5f * delta * delta, 0.5f * delta * delta);
		}else if(WizGame.getPlayer().isMovingDown() && WizGame.getPlayer().isMovingLeft()){
			particle.adjustPosition(-0.5f * delta * delta, 0.5f * delta * delta);;
			
			
		}else if(WizGame.getPlayer().isMovingUp()){
			particle.adjustPosition(0, -0.5f * delta * delta);
		}else if(WizGame.getPlayer().isMovingDown()){
			particle.adjustPosition(0, 0.5f * delta * delta);
		}else if(WizGame.getPlayer().isMovingLeft()){
			particle.adjustPosition(-0.5f * delta * delta, 0);
		}else if(WizGame.getPlayer().isMovingRight()){
			particle.adjustPosition(0.5f * delta * delta, 0);
		}
		particle.adjustVelocity(particleDirection.x/500000 * delta, particleDirection.y/500000 * delta);
		
		
		Rectangle tempBounds = new Rectangle(particle.getX(), particle.getY(), this.size, this.size); 
		for(Rectangle e : WizGame.blocks){
			if(e.intersects(tempBounds)){
				particle.kill();
			}
		}
		
		
		float c = 0.002f * delta;
		particle.adjustColor(0,-c/2,-c*2,-c/4);
	}

	public boolean isEnabled() {
		return true;
	}


	public void setEnabled(boolean enabled) {
	}


	public boolean completed() {
		return false;
	}

	public boolean useAdditive() {
		return false;
	}

	public Image getImage() {
		return null;
	}

	public boolean usePoints(ParticleSystem system) {
		return false;
	}

	public boolean isOriented() {
		return false;
	}

	public void wrapUp() {
	}
	
	public void resetState() {
	}
}
