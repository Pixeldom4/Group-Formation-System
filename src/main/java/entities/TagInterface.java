package entities;

/**
 * Interface representing a tag associated with a skill.
 */
public interface TagInterface {

    /**
     * Returns the skill associated with the tag.
     *
     * @return the skill
     */
    public String getSkill();

    /**
     * Sets the skill associated with the tag.
     *
     * @param tagSkill the skill to be set
     */
    public void setSkill(String tagSkill);
}
