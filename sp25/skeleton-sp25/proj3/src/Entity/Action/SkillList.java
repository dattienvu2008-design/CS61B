package Entity.Action;

public enum SkillList {
    PUNCH("Punch"),
    DUMMY("");

    private SkillList(String name) {
        this.name = name;
    }

    public String name;
}
