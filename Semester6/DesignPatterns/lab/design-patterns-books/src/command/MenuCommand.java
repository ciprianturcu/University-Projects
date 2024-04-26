package command;

import lombok.Getter;

@Getter
public abstract class MenuCommand {
    private final String key;
    private final String description;

    MenuCommand(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute();

}
