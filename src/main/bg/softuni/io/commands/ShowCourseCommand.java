package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("show")
public class ShowCourseCommand extends Command {

    @Inject
    private Database repository;

    public ShowCourseCommand(String input,
                             String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 2 && super.getData().length != 3) {
            throw new InvalidCommandException(super.getInput());
        }

        if (super.getData().length == 2) {
            String courseName = super.getData()[1];
            this.repository.getStudentsByCourse(courseName);
        }

        if (super.getData().length == 3) {
            String courseName = super.getData()[1];
            String userName = super.getData()[2];
            this.repository.getStudentMarkInCourse(courseName, userName);
        }
    }
}
