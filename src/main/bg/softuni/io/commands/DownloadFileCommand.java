package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.AsynchDownloader;
import main.bg.softuni.exceptions.InvalidCommandException;

@Alias("download")
public class DownloadFileCommand extends Command {

    @Inject
    private AsynchDownloader downloadManager;

    public DownloadFileCommand(String input,
                               String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (super.getData().length != 2) {
            throw new InvalidCommandException(super.getInput());
        }

        String fileUrl = super.getData()[1];
       this.downloadManager.download(fileUrl);

    }
}
