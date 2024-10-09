package me.foxils.foxutils.utilities;

@SuppressWarnings("unused")
public enum ActionType {
    LEFT_CLICK("Left-Click"),
    SHIFT_LEFT_CLICK("Sneaking + Left-Click"),
    RIGHT_CLICK("Right-Click"),
    SHIFT_RIGHT_CLICK("Sneaking + Right-Click"),
    MIDDLE_CLICK("Middle-Click"),
    FULL_SET_BONUS("Full Set Bonus"),
    KILL("On-Kill"),
    ATTACK("On-Attack"),
    SHOT("On-Shoot"),
    LAUNCH("On-Launch"),
    MINE("On-Mine"),
    DROP("On-Drop"),
    SWAP_HANDS("Swap Off-Hand"),
    CRAFT("On-Craft"),
    SHIFTDROP("Sneaking + On-Drop"),
    DOUBLEJUMP("Double Jump"),
    SHIFTDOUBLEJUMP("Sneaking + Double Jump"),
    PASSIVE("Passive Ability:"),
    NONE("");

    private final String text;

    ActionType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}