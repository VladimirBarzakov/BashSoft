package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.DirectoryManager;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("cdabs")
public class ChangeAbsolutePathCommand extends Command {

    @Inject
    private DirectoryManager manager;

    public ChangeAbsolutePathCommand(String input,
                                     String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 2) {
            throw new InvalidCommandException(super.getInput());
        }

        String absolutePath = super.getData()[1];
        this.manager.changeCurrentDirAbsolute(absolutePath);
    }
}
