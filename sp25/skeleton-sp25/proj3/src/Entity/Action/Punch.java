package Entity.Action;

import worldassets.Direction;
import worldassets.Tile;

public class Punch implements AttackSkill {
    private final double damage;
    public static final Punch standardPuch = new Punch(1);

    public Punch(double damage) {
        this.damage = damage;
    }

    public void useSkill(Tile tile, Direction direction) {
        Tile affectedTile = direction.getTile(tile);
        if (affectedTile.hasEntity()) {
            affectedTile.getEntity().takeDamage(damage);
        }
    }
}
