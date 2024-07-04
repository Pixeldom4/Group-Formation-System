package Entities;

public class Tag implements TagInterface {
    private String tagSkill;

    // Constructor
    public Tag(String tagSkill) {
        this.tagSkill = tagSkill;
    }

    // Getter
    public String getSkill() {
        return tagSkill;
    }

    // Setter
    public void setSkill(String tagSkill) {
        this.tagSkill = tagSkill;
    }
}
