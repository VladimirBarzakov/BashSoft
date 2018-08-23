package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("readdb")
public class ReadDatabaseCommand extends Command {

    @Inject
    private Database repository;

    public ReadDatabaseCommand(String input,
                               String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 2) {
            throw new InvalidCommandException(super.getInput());
        }

        String fileName = super.getData()[1];
        this.repository.loadData(fileName);
    }
}
