package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity;

public abstract class EntityLiving extends Entity {

	private int health;
	private int maxHealth;
	private int stamina;
	private int maxStamina;
	private int mana;
	private int maxMana;
	private int armor;
	private float speed;

	public EntityLiving(EntityType type) {
		super(type);
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	protected void setHealth(int health) {
		this.health = health;
	}
	
	protected void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	protected void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	protected void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}
	
	protected void setMana(int mana) {
		this.mana = mana;
	}
	
	protected void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
	protected void setArmor(int armor) {
		this.armor = armor;
	}
	
	protected void setSpeed(float speed) {
		this.speed = speed;
	}
}
