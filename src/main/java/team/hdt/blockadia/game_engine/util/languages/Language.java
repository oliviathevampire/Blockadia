package team.hdt.blockadia.game_engine.util.languages;

public enum Language {
	
	ENGLISH("English"),
	GERMAN("Deutsch"),
	NORWEGIAN("Norwegian"),
	SWEDISH("Swedish"),
	LOLCAT("LOLCAT"),
	SHAKESPEAREAN_ENGLISH("Shakespearean English");
	
	private final String name;
	
	private Language(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
