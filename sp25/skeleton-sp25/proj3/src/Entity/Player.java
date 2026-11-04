package Entity;

import Entity.Action.AttackSkill;
import Entity.Action.Punch;
import tileengine.TETileRenderer;
import world.World;
import worldassets.Direction;
import worldassets.Tile;

import java.util.ArrayList;

public class Player extends Entity{
    public final Cooldown movementCooldown = new Cooldown();
    private final ArrayList<AttackSkill> attackSkillsList = new ArrayList<>();
    private AttackSkill getAttackSkill(int index) {
        return attackSkillsList.get(index);
    }

    public int getMovementCooldownMillis() {
        return movementCooldownMillis;
    }
    public void setMovementCooldownMillis(int movementCooldownMillis) {
        this.movementCooldownMillis = movementCooldownMillis;
    }
    private int movementCooldownMillis = 100;
    private World world;


    public Player(int health, int ID, TETileRenderer skin, Tile currentPos, World world) {
        super(health, ID, skin, currentPos);
        attackSkillsList.add(Punch.standardPuch);
    }

    public void useSkill(int index, Direction direction) {
        getAttackSkill(index).useSkill(getCurrentTile(), direction);
    }
}
