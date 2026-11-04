package Entity.Action;

import worldassets.Direction;
import worldassets.Tile;

public class Punch implements AttackSkill {
    private final double damage;
    public final double cooldown;

    public static final SkillList type = SkillList.PUNCH;
    @Override
    public SkillList getSkillName() {
        return type;
    }

    public int getCooldownTime() {
        return (int) cooldown*1000;
    }

    public static final Punch standardPuch = new Punch(1, 2);

    public Punch(double damage, double cooldown) {
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public void useSkill(Tile tile, Direction direction) {
        Tile affectedTile = direction.getTile(tile);
        if (affectedTile.hasEntity()) {
            affectedTile.getEntity().takeDamage(damage);
        }
    }
}
