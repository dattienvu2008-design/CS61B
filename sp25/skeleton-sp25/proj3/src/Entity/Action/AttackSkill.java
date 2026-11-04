package Entity.Action;

import worldassets.Direction;
import worldassets.Tile;

public interface AttackSkill {
    double damage = 0;
    double cooldown = 0;
    SkillList type = SkillList.DUMMY;

    int getCooldownTime();
    SkillList getSkillName();

    void useSkill(Tile tile, Direction direction);
}
