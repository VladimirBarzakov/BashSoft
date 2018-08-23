package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.DirectoryManager;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("ls")
public class TraverseFoldersCommand extends Command {

    @Inject
    private DirectoryManager manager;

    public TraverseFoldersCommand(String input,
                                  String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 1 && super.getData().length != 2) {
            throw new InvalidCommandException(super.getInput());
        }

        if (super.getData().length == 1) {
            this.manager.traverseDirectory(0);
        }

        if (super.getData().length == 2) {
            this.manager.traverseDirectory(Integer.valueOf(super.getData()[1]));
        }
    }
}
