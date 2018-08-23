package main.bg.softuni.io.commands;

import main.bg.softuni.contracts.Executable;
import main.bg.softuni.exceptions.InvalidCommandException;

public abstract class Command implements Executable {
    private String input;
    private String[] data;


    public Command(String input,
                   String[] data) {
        this.setInput(input);
        this.setData(data);
    }

    protected String getInput() {
        return this.input;
    }

    private void setInput(String input){
        if (input==null||input.equals("")){
            throw new InvalidCommandException(input);
        }
        this.input=input;
    }

    protected String[] getData() {
        return this.data;
    }

    public void setData(String[] data) {
        if (data==null||data.length<1){
            throw new InvalidCommandException("data parameter");
        }
        this.data = data;
    }

    public abstract void execute() throws Exception;
}
