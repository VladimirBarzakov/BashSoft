package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidCommandException;
import main.bg.softuni.io.OutputWriter;

@Alias("dropdb")
public class DropDatabaseCommand extends Command {

    @Inject
    private Database repository;

    public DropDatabaseCommand(String input,
                               String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length!=1){
            throw new InvalidCommandException(super.getInput());
        }
        this.repository.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
