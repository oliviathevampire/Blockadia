package team.hdt.sandboxgame.game_engine.util.languages;

public class ComplexString {
	
	private final String original;
	
	public ComplexString(String original){
		this.original = original;
	}
	
	public String getString(String... inputs){
		String newString = original;
		for(int i=0;i<inputs.length;i++){
			newString = newString.replaceFirst("\\$"+i+"\\$", inputs[i]);
		}
		return newString;
	}

}
