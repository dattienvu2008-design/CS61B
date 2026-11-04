package Entity.Action;

import worldassets.Direction;
import worldassets.Tile;

public interface AttackSkill {
    double damage = 0;
    void useSkill(Tile tile, Direction direction);
}
