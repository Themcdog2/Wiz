import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class Main {
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	public static void main(String[] args){
		AppGameContainer appgc;
		
		
		
		try {
			appgc = new AppGameContainer(new WizGame("Simple Slick Game"));
			appgc.setDisplayMode(800, 600, false);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
