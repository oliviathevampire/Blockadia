package team.hdt.sandboxgame.game_engine.common.entity.ai.basics;

import baseMovement.MovementComp;

public class StandAnimation {
	
	private final MovementComp mover;
	
	private boolean standing = false;
	
	public StandAnimation(MovementComp mover){
		this.mover = mover;
	}
	
	public void update(){
		if(!standing && mover.normalize()){
			standing = true;
		}
		if(standing){
			
		}
	}
	
	public void init(){
		standing = false;
	}

}
