package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidCommandException;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.staticData.ExceptionMessages;

@Alias("filter")
public class PrintFilteredStudentsCommand extends Command {

    @Inject
    private Database repository;

    public PrintFilteredStudentsCommand(String input,
                                        String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 5) {
            throw new InvalidCommandException(super.getInput());
        }

        String course = super.getData()[1];
        String filter = super.getData()[2].toLowerCase();
        String takeCommand = super.getData()[3].toLowerCase();
        String takeQuantity = super.getData()[4].toLowerCase();

        tryParseParametersForFilter(takeCommand, takeQuantity, course, filter);
    }

    private  void tryParseParametersForFilter(
            String takeCommand, String takeQuantity,
            String courseName, String filter) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.repository.filterAndTake(courseName, filter);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.repository.filterAndTake(courseName, filter, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
        }
    }
}
