package team.hdt.sandboxgame.game_engine.common.sessionStats;

import team.hdt.sandboxgame.game_engine.common.gameManaging.GameManager;
import team.hdt.sandboxgame.game_engine.common.util.time.Calendar;
import team.hdt.sandboxgame.game_engine.util.BinaryReader;
import team.hdt.sandboxgame.game_engine.util.BinaryWriter;

public class Stats {

	//world specific stats
	
	private static final float MINUTE = 60;
	private static final float DP_UPDATE_TIME = 11;
	private static final float MIN_FRACTION = DP_UPDATE_TIME / MINUTE;
	
	private Calendar timeAndDate;
	
	private int dp = 2500;
	private int dpPerMin = 0;
	private boolean loaded = false;
	
	private LockStatus lockStatus;
	
	private float time = 0;
	
	private Stats(){

	}
	
	public static Stats loadStats(BinaryReader reader) throws Exception{
		final Stats stats = new Stats();
		stats.dp = reader.readInt();
		stats.dpPerMin = reader.readInt();
		stats.timeAndDate = Calendar.load(reader);
		stats.lockStatus = LockStatus.loadLockStatus(reader);
		stats.loaded = true;
		return stats;
	}
	
	public static Stats createNewStats(){
		Stats stats = new Stats();
		stats.lockStatus = LockStatus.newLockStatus();
		stats.loaded = true;
		stats.timeAndDate = Calendar.init();
		return stats;
	}
	
	public void update(){
		timeAndDate.update(GameManager.getGameSeconds());
		time += GameManager.getGameSeconds();
		if(time >= DP_UPDATE_TIME){
			time %= DP_UPDATE_TIME;
			increaseDp(getDpChange());
		}
	}
	
	public Calendar getCalendar(){
		return timeAndDate;
	}
	
	public void export(BinaryWriter writer){
		writer.writeInt(dp);
		writer.writeInt(dpPerMin);
		timeAndDate.save(writer);
		lockStatus.export(writer);
	}
	
	public LockStatus getLockStatus(){
		return lockStatus;
	}
	
	public int getDpCount(){
		return dp;
	}
	
	public int getDpPerMinute(){
		return dpPerMin;
	}
	
	public boolean isLoaded(){
		return loaded;
	}
	
	public void increaseDp(int increase){
		dp += increase;
	}
	
	public void setDpPerMinute(int amount){
		if(amount!=dpPerMin){
			this.dpPerMin = amount;
		}
	}
	
	private int getDpChange(){
		return (int) (dpPerMin * MIN_FRACTION);
	}

}
