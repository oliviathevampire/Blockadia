package team.hdt.sandboxgame.game_engine.common.world.player;

public class BendingStyle {

    private Player player;
    private Element element;
    public BendingStyle(Player player, Element element) {
        this.player = player;
        this.element = element;
    }

    public Projectile conjure() {
        switch (element) {
            case AIR:
            case FIRE:
                return new Projectile(player, element);
            default:
                return null;
        }
    }

    public enum Element {
        AIR, EARTH, WATER, FIRE
    }

}
