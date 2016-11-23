import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class UiButton extends Rectangle{

	Image texture;
	Ability attatchedAbility;
	
	
	public UiButton(float x, float y, float width, float height, Image texture, Ability ability) {
		super(x, y, width, height);
		this.texture = texture;
		attatchedAbility = ability;
		
	}
	
	public void render(Graphics g){
		
		
		/*
		 * Im not sure why i have to subtract the scroll offset separately,
		 * everything should be using the same graphics context, and should be translated but idk
		 */
		
		 texture.draw(x / WizGame.scale + WizGame.screenScrollX, y / WizGame.scale + WizGame.screenScrollY, width / WizGame.scale, height / WizGame.scale);
		
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Ability getAttatchedAbility() {
		return attatchedAbility;
	}

	public void setAttatchedAbility(Ability attatchedAbility) {
		this.attatchedAbility = attatchedAbility;
	}
	
	

}
