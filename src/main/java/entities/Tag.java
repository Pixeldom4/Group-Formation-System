package entities;

/**
 * Represents a tag associated with a skill.
 */
public class Tag implements TagInterface {

    private String tagSkill;

    /**
     * Constructs a Tag with the specified skill.
     *
     * @param tagSkill the skill associated with the tag
     */
    public Tag(String tagSkill) {
        this.tagSkill = tagSkill;
    }

    /**
     * Returns the skill associated with the tag.
     *
     * @return the skill
     */
    public String getSkill() {
        return tagSkill;
    }

    /**
     * Sets the skill associated with the tag.
     *
     * @param tagSkill the skill to be set
     */
    public void setSkill(String tagSkill) {
        this.tagSkill = tagSkill;
    }
}
