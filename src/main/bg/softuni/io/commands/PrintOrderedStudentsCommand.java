package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidCommandException;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.staticData.ExceptionMessages;

@Alias("order")
public class PrintOrderedStudentsCommand extends Command {

    @Inject
    private Database repository;

    public PrintOrderedStudentsCommand(String input,
                                       String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 5) {
            throw new InvalidCommandException(super.getInput());
        }

        String courseName = super.getData()[1];
        String orderType = super.getData()[2].toLowerCase();
        String takeCommand = super.getData()[3].toLowerCase();
        String takeQuantity = super.getData()[4].toLowerCase();

//        StudentsRepository.printOrderedStudents(courseName, modifier, numberOfStudents);
        tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    }

    private  void tryParseParametersForOrder(
            String takeCommand, String takeQuantity,
            String courseName, String orderType) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
           this.repository.orderAndTake(courseName, orderType);
           return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.repository.orderAndTake(courseName, orderType, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
        }
    }
}
