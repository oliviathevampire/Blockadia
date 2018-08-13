package team.hdt.sandboxgame.game_engine.util.toolbox;

public class WorldHighlights {
	
	private static final WorldHighlights highlights = new WorldHighlights();
	
	private Highlight highlight1 = new Highlight();
	private Highlight highlight2 = new Highlight();
	
	private WorldHighlights(){
	}
	
	public static WorldHighlights getHighlights(){
		return highlights;
	}
	
	public int getHighlightCount(){
		int count = 0;
		if(highlight1.isShown()){
			count++;
		}
		if(highlight2.isShown()){
			count++;
		}
		return count;
	}
	
	public void updateHighlights(){
		highlight1.update();
		highlight2.update();
	}
	
	public Highlight getHighlight1(){
		return highlight1;
	}
	
	public Highlight getHighlight2(){
		return highlight2;
	}
	
	public Highlight getActiveHighlight(){
		if(highlight1.isShown()){
			return highlight1;
		}else{
			return highlight2;
		}
	}

}
