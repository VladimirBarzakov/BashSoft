package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.ContentComparer;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("cmp")
public class CompareFilesCommand extends Command {

    @Inject
    private ContentComparer tester;

    public CompareFilesCommand(String input,
                               String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 3) {
            throw new InvalidCommandException(super.getInput());
        }

        String firstPath = super.getData()[1];
        String secondPath = super.getData()[2];
       this.tester.compareContent(firstPath, secondPath);
    }


}
