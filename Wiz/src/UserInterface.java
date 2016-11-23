import java.util.ArrayList;

import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class UserInterface {
	
	
	private SpriteSheet interfaceSheet;
	
	
	Image filledBlock;
	Image emptyBlock;
	
	UiButton abilityButton1;
	UiButton abilityButton2;
	UiButton abilityButton3;
	UiButton abilityButton4;
	
	static ArrayList<UiButton> abilityBar = new ArrayList<UiButton>();
	
	
	
	public UserInterface(){
		try {
			interfaceSheet = new SpriteSheet(new Image("res/ui/scrollsandblocks.png"), 32, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		filledBlock = interfaceSheet.getSprite(0, 0).getScaledCopy(1);
		filledBlock.setFilter(Image.FILTER_NEAREST);
		
		emptyBlock = interfaceSheet.getSprite(0, 1).getScaledCopy(1);
		emptyBlock.setFilter(Image.FILTER_NEAREST);
		
		Player p = WizGame.getPlayer();
		
		abilityButton1 = new UiButton(0, Main.HEIGHT - 64, 64, 64, filledBlock, p.teleportAbility);
		abilityButton2 = new UiButton(64, Main.HEIGHT - 64, 64, 64, filledBlock, p.fireAbility);
		abilityButton3 = new UiButton(128, Main.HEIGHT - 64, 64, 64, filledBlock, new Ability());
		abilityButton4 = new UiButton(192, Main.HEIGHT - 64, 64, 64, filledBlock, new Ability());
		
		abilityBar.add(abilityButton1);
		abilityBar.add(abilityButton2);
		abilityBar.add(abilityButton3);
		abilityBar.add(abilityButton4);

		
	}
	
	public ArrayList<UiButton> getClickboxes(){
		ArrayList<UiButton> clickboxes = new ArrayList<UiButton>();
		clickboxes.add(abilityButton1);
		return clickboxes;
		
	}
	
	public void receiveEvent(int key){
		if(key == Input.KEY_1){
			boolean activated = abilityBar.get(0).getAttatchedAbility().getActivated();
			abilityBar.get(0).getAttatchedAbility().setActivated(!activated);
			if(!abilityBar.get(0).getAttatchedAbility().getActivated()){
				abilityBar.get(0).setTexture(filledBlock);
			}else{
				abilityBar.get(0).setTexture(emptyBlock);
			}
		}
		
		if(key == Input.KEY_2){
			boolean activated = abilityBar.get(1).getAttatchedAbility().getActivated();
			abilityBar.get(1).getAttatchedAbility().setActivated(!activated);
			if(!abilityBar.get(1).getAttatchedAbility().getActivated()){
				abilityBar.get(1).setTexture(filledBlock);
			}else{
				abilityBar.get(1).setTexture(emptyBlock);
			}
		}
		
		if(key == Input.KEY_3){
			boolean activated = abilityBar.get(2).getAttatchedAbility().getActivated();
			abilityBar.get(2).getAttatchedAbility().setActivated(!activated);
			if(!abilityBar.get(2).getAttatchedAbility().getActivated()){
				abilityBar.get(2).setTexture(filledBlock);
			}else{
				abilityBar.get(2).setTexture(emptyBlock);
			}
		}
		
		if(key == Input.KEY_4){
			boolean activated = abilityBar.get(3).getAttatchedAbility().getActivated();
			abilityBar.get(3).getAttatchedAbility().setActivated(!activated);
			if(!abilityBar.get(3).getAttatchedAbility().getActivated()){
				abilityBar.get(3).setTexture(filledBlock);
			}else{
				abilityBar.get(3).setTexture(emptyBlock);
			}
		}
		
	}
	
	public void update(int delta){
		
		
	}
	
	public void render(Graphics g ,GameContainer c){
		
		//System.out.println(c.getWidth()/c.getHeight() );
		for(UiButton e : abilityBar){
			e.render(g);
		}
		
		
	}
	
	
	
	
	
			

}
