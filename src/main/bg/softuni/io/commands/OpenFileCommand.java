package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.exceptions.InvalidCommandException;
import main.bg.softuni.staticData.SessionData;

import java.awt.*;
import java.io.File;

@Alias("open")
public class OpenFileCommand extends Command {
    public OpenFileCommand(String input,
                           String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 2) {
            throw new InvalidCommandException(super.getInput());
        }

        String fileName = super.getData()[1];
        String filePath = SessionData.currentPath + "\\" + fileName;
        File file = new File(filePath);
        Desktop.getDesktop().open(file);
    }
}
