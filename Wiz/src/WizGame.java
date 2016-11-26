import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class WizGame extends BasicGame implements KeyListener, MouseListener, MouseMotionListener{
	protected SGL GL = Renderer.get();
	
	//Define the tiledmap
	Map map;
	// This will keep a list of Tiles that are blocked
	private boolean blocked[][];

	// For collision detection, we have a list of Rectangles you can use to test against
	static ArrayList<Rectangle> blocks;
	
	UserInterface ui;
	
	static //Define player
	Player p;
	
	//keys
	boolean keyW;
	boolean keyA;
	boolean keyD;
	boolean keyS;
	
	static boolean mouseDown;
	static boolean mouseReleased;
	
	static float scale = 1f;
	static float screenScrollX;
	static float screenScrollY;
	
	boolean scrollingUp;
	boolean scrollingDown;
	boolean scrollingLeft;
	boolean scrollingRight;

	private boolean isInCollision;
	
	static float mouseX;
	static float mouseY;

	private static float screenMouseX;

	private static float screenMouseY;
	
	

	
	
	public WizGame(String title) {
		super(title);
		
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		//Render map
		//g.scale(scale, scale);
		
		//g.translate(-screenScrollX, -screenScrollY); //has to be negative because f you
		GL.glTranslatef(-Math.round(screenScrollX), -Math.round(screenScrollY), 0);
		//GL.glScalef(scale, scale, 0);
		
		
		map.render(0, 0);
		p.render(g);
	
		
		//Light.renderAll(g, container, null);
		
		
		

		
		ui.render(g, container);
		
		
		
		//g.drawImage(new Image("res/particles/orangePixel.png"), 0, 0);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
		map = new Map("res/tileMaps/testMap1.tmx");
		p = new Player();
		ui = new UserInterface();
		

		
		// This will create an Array with all the Tiles in your map. When set to true, it means that Tile is blocked.
		blocked = new boolean[map.getWidth()][map.getHeight()];
		blocks = new ArrayList<Rectangle>();
		
		
		/*
		for(int i = 0; i < 100; i++){
		
		Particle temp = particlesys.getNewParticle(fe, 1000);
		temp.setImage(new Image("res/particles/orangePixel.png"));
		System.out.println(temp.getSize());
		temp.adjustSize(-9f);
		temp.setUsePoint(Particle.USE_QUADS);
		temp.setLife(1000f);
		System.out.println(temp.getSize());
		
		}
		*/
		// Loop through the Tiles and read their Properties

		// Set here the Layer you want to Read. In your case, it'll be layer 1,
		// since the objects are on the second layer.
		int layer = 0; 
		String value = null;
		for(int i = 0; i < map.getWidth(); i++) {
		    for(int j = 0; j < map.getHeight(); j++) {

		        // Read a Tile
		        int tileID = map.getTileId(i, j, layer);

		        // Get the value of the Property named "blocked"
		        
		         value = map.getTileProperty(tileID, "blocked", "false");
		       

		        // If the value of the Property is "true"...
		        if(value.equals("true")) {
		        	System.out.println("Tile Blocked " + i + " " + j);
		            // We set that index of the TileMap as blocked
		            blocked[i][j] = true;
		           // System.out.println(i + " " + j);

		            // And create the collision Rectangle
		            blocks.add(new Rectangle((float)i * 32, (float)j * 32, 32, 32)); //Replace 32 with tile size
		        }
		    }
		}
		
	}
	
	

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		
		 screenMouseX = input.getAbsoluteMouseX();
		 screenMouseY = input.getAbsoluteMouseY();
		 
		 
		 if(mouseDown == true && input.isMouseButtonDown(0) == false){
			 mouseReleased = true;
		 }else{
			 mouseReleased = false;
		 }
		 
		 mouseDown = input.isMouseButtonDown(0);
		
		 //System.out.println(mouseDown);
		 
		

		
		p.update(container, delta, isInCollision);
		screenScrollX = (p.getWorldX() - Main.WIDTH / 2);
		screenScrollY = (p.getWorldY() - Main.HEIGHT / 2);
		
		
		if(scrollingLeft){
			screenScrollX++;
		}
		if(scrollingRight){
			screenScrollX--;
		}
		if(scrollingUp){
			screenScrollY++;
		}
		if(scrollingDown){
			screenScrollY--;
		}
		
	}
	
	public static Vector2f getScreenMouseLocation(){
		return new Vector2f(screenMouseX, screenMouseY);
	}
	public static Vector2f getWorldMouseLocation(){
		return new Vector2f(screenMouseX + screenScrollX, screenMouseY + screenScrollY);
	}
	
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		if(button == Input.MOUSE_LEFT_BUTTON){
			if(p.teleportAbility.getActivated()){
				p.setTeleporting(true);
			}
		
		}
		
		/*
		for(UiButton e : ui.getClickboxes()){
			if(e.includes(x, y)){
				ui.receiveClick(e);			All of this code is worthless and a product of me being retarded 
			}
		}
		*/
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if(key == Input.KEY_W){
			p.setMovingUp(true);
		}
		if(key == Input.KEY_A){
			p.setMovingLeft(true);
		}
		if(key == Input.KEY_S){
			p.setMovingDown(true);
		}
		if(key == Input.KEY_D){
			p.setMovingRight(true);
		}
		if(key == Input.KEY_J){
			scale+=0.1f;
		}
		if(key == Input.KEY_L){
			scale-=0.1f;
		}
		if(key == Input.KEY_UP){
			scrollingUp = true;
		}
		if(key == Input.KEY_DOWN){
			scrollingDown = true;
		}
		if(key == Input.KEY_LEFT){
			scrollingLeft = true;
		}
		if(key == Input.KEY_RIGHT){
			scrollingRight = true;
		}
		
		ui.receiveEvent(key);
		
	}
	
	
	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		if(key == Input.KEY_W){
			p.setMovingUp(false);
		}
		if(key == Input.KEY_A){
			p.setMovingLeft(false);
		}
		if(key == Input.KEY_S){
			p.setMovingDown(false);
		}
		if(key == Input.KEY_D){
			p.setMovingRight(false);
		}
		
		if(key == Input.KEY_UP){
			scrollingUp = false;
		}
		if(key == Input.KEY_DOWN){
			scrollingDown = false;
		}
		if(key == Input.KEY_LEFT){
			scrollingLeft = false;
		}
		if(key == Input.KEY_RIGHT){
			scrollingRight = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

	public static Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		WizGame.p = p;
	}

	public static ArrayList<Rectangle> getBlockingElements() {
		// TODO Auto-generated method stub
		return blocks;
	}
	

}
