package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

public class GuiClickEvent {
	
	public final boolean leftClick;
	public final boolean rightClick;
	public final boolean mouseOver;
	public final boolean toggleChange;
	public final boolean eventState;
	
	private GuiClickEvent(boolean leftClick, boolean rightClick, boolean mouseOver, boolean toggle, boolean state){
		this.leftClick = leftClick;
		this.rightClick = rightClick;
		this.mouseOver = mouseOver;
		this.eventState = state;
		this.toggleChange = toggle;
	}
	
	public static GuiClickEvent newRightClickEvent(boolean state){
		GuiClickEvent event = new GuiClickEvent(false, true, false, false, state);
		return event;
	}
	
	public static GuiClickEvent newLeftClickEvent(boolean state){
		GuiClickEvent event = new GuiClickEvent(true, false, false, false, state);
		return event;
	}
	
	public static GuiClickEvent newMouseOverEvent(boolean state){
		GuiClickEvent event = new GuiClickEvent(false, false, true, false, state);
		return event;
	}
	
	public static GuiClickEvent newToggleEvent(boolean state){
		GuiClickEvent event = new GuiClickEvent(false, false, false, true, state);
		return event;
	}
	
	public boolean isToggleOn(){
		return toggleChange && eventState;
	}
	
	public boolean isToggleOff(){
		return toggleChange && !eventState;
	}
	
	public boolean isLeftClick(){
		return leftClick && eventState;
	}
	
	public boolean isLeftClickRelease(){
		return leftClick && !eventState;
	}
	
	public boolean isRightClick(){
		return rightClick && eventState;
	}
	
	public boolean isMouseOver(){
		return mouseOver && eventState;
	}
	
	public boolean isMouseOff(){
		return mouseOver && !eventState;
	}

}
