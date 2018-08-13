package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import java.util.ArrayList;
import java.util.List;

public class GuiClickableGroup {
	
	private List<GuiClickable> buttons = new ArrayList<GuiClickable>();
	private GuiClickable currentlyActive = null;
	private boolean tabs = false;
	
	public GuiClickableGroup(){}
	
	public GuiClickableGroup(boolean tabs){
		this.tabs = tabs;
	}
	
	public void addButton(final GuiClickable button, boolean selected) {
		addButton(button);
		if(selected){
			button.toggle();
			currentlyActive = button;
		}
	}

	public void addButton(final GuiClickable button) {
		if(tabs){
			button.disableManualTurnOff();
		}
		buttons.add(button);
		button.addListener(new ClickListener() {

			@Override
			public void eventOccurred(GuiClickEvent event) {
				if(event.isToggleOn()){
					select(button);
				}else if(event.isToggleOff()){
					currentlyActive = null;
				}
			}
		});
	}
	
	public List<GuiClickable> getButtons(){
		return buttons;
	}
	
	public boolean areAllOff(){
		for(GuiClickable button : buttons){
			if(button.isToggledOn()){
				return false;
			}
		}
		return true;
	}
	
	private void select(GuiClickable button){
		turnOffCurrentlyActive();
		currentlyActive = button;
	}
	
	public boolean turnOffCurrentlyActive(){
		if(currentlyActive!=null){
			currentlyActive.toggle();
			currentlyActive = null;
			return true;
		}
		return false;
	}


}
