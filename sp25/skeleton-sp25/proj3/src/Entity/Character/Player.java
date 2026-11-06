package Entity.Character;

import Entity.Action.AttackSkill;
import Entity.Action.Punch;
import Entity.Entity;
import renderengine.TETileRenderer;
import world.World;
import worldassets.Direction;
import worldassets.tile.Tile;
import Entity.Cooldown;

import java.util.ArrayList;

public class Player extends Entity {
    public final Cooldown movementCooldown = new Cooldown();

    private final ArrayList<AttackSkill> attackSkillsList = new ArrayList<>();
    public int getNumSkills() {
        return attackSkillsList.size();
    }
    public AttackSkill getAttackSkill(int index) {
        return attackSkillsList.get(index);
    }

    private final ArrayList<Cooldown> attackSkillsCd = new ArrayList<>();
    private Cooldown getAttackSkillCd(int index) {
        return attackSkillsCd.get(index);
    }

    private Direction currentDirection;
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
    public Direction getCurrentDirection() {
        return currentDirection;
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
        attackSkillsList.add(Punch.standardPuch); attackSkillsCd.add(new Cooldown());
        setCurrentDirection(Direction.LEFT);
    }

    public void useSkill(int index, Direction direction) {
        AttackSkill currentSkill = getAttackSkill(index);
        if (!getAttackSkillCd(index).onCooldown()) {
            currentSkill.useSkill(getCurrentTile(), direction);
            getAttackSkillCd(index).startCooldown(currentSkill.getCooldownTime());
        }
    }

    public double getCooldownPercentageOfSkill(int index) {
        return 1 - (getAttackSkillCd(index).cooldownMillisLeft() / getAttackSkill(index).getCooldownTime());
    }
}
