package team.hdt.blockadia.game_engine.client.saves;

import team.hdt.blockadia.game_engine.util.BinaryReader;
import team.hdt.blockadia.game_engine.util.BinaryWriter;

import java.util.Calendar;
import java.util.Date;

public class SaveSlotInfo {
	
	private Calendar lastPlayed = Calendar.getInstance();
	private int dp;

	public SaveSlotInfo(){
	}
	
	public void updateInfo(){
		lastPlayed = Calendar.getInstance();
	}
	
	public void save(BinaryWriter writer){
		updateInfo();
		writer.writeLong(lastPlayed.getTime().getTime());
	}
	
	public Calendar getLastPlayedDate(){
		return lastPlayed;
	}

	public void load(BinaryReader reader) throws Exception{
		long dateTime = reader.readLong();
		lastPlayed = Calendar.getInstance();
		lastPlayed.setTime(new Date(dateTime));
	}

}
