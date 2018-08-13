package team.hdt.sandboxgame.game_engine.client.gui.userInterfaces;

import java.util.ArrayList;
import java.util.List;

public class GuiTextButtonGroup {

	private List<GuiTextButton> buttons = new ArrayList<GuiTextButton>();
	private GuiTextButton currentlyActive;

	public void addButton(final GuiTextButton button, boolean selected){
		buttons.add(button);
		if(selected){
			setSelected(button);
		}
		button.addListener(on -> setSelected(button));
	}
	
	private void setSelected(GuiTextButton button){
		button.highlight(true);
		if(currentlyActive!=null){
			currentlyActive.highlight(false);
		}
		this.currentlyActive = button;
	}
	
}
