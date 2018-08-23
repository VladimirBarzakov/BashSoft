package main.bg.softuni.io;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.contracts.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandInterpreter implements Interpreter {

    private static final String COMMANDS_LOCATION = "src/main/bg/softuni/io/commands";
    private static final String COMMANDS_PACKAGE = "main.bg.softuni.io.commands.";

    private ContentComparer tester;
    private Database repository;
    private AsynchDownloader downloadManager;
    private DirectoryManager inputOutputManager;

    public CommandInterpreter(ContentComparer tester,
                              Database repository,
                              AsynchDownloader downloadManager,
                              DirectoryManager inputOutputManager) {
        this.tester = tester;
        this.repository = repository;
        this.downloadManager = downloadManager;
        this.inputOutputManager = inputOutputManager;
    }

    @Override
    public void interpretCommand(String input) {
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try {
            Executable command=parseCommand(input, data, commandName);
            command.execute();
        } catch (Throwable t){
            OutputWriter.displayException(t.getMessage());
        }

    }

    private Executable parseCommand(String input, String[] data, String command){
        File commandsFolder = new File(COMMANDS_LOCATION);
        Executable executable = null;
        boolean a = commandsFolder.isDirectory();
        File[] files = commandsFolder.listFiles();
        for (File file : commandsFolder.listFiles()) {
            if (!file.isFile()|| !file.getName().endsWith(".java")){
                continue;
            }
            try {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                Class<Executable> exeClass = (Class<Executable>) Class.forName(COMMANDS_PACKAGE+className);
                if (!exeClass.isAnnotationPresent(Alias.class)){
                    continue;
                }
                Alias alias = exeClass.getAnnotation(Alias.class);
                String value = alias.value();
                if (!value.equalsIgnoreCase(command)){
                    continue;
                }
                Constructor exeConstructor = exeClass.getConstructor(String.class, String[].class);
                executable = (Executable) exeConstructor.newInstance(input, data);
                this.injectDependencies(executable, exeClass);

            }catch (ReflectiveOperationException rfe){
                rfe.printStackTrace();
            }
        }
        if (executable==null){
            throw new InvalidInputException(input);
        }
        return executable;
    }

    private void injectDependencies(Executable executable, Class<Executable> exeClass) throws IllegalAccessException {
        Field[] exeFields = exeClass.getDeclaredFields();
        Field[] theseFields = CommandInterpreter.class.getDeclaredFields();
        for (Field fieldToSet : exeFields) {
            if (!fieldToSet.isAnnotationPresent(Inject.class)){
                continue;
            }
            fieldToSet.setAccessible(true);
            for (Field theseField : theseFields) {
                if (!theseField.getType().equals(fieldToSet.getType())){
                    continue;
                }
                theseField.setAccessible(true);
                fieldToSet.set(executable,theseField.get(this));
                break;
            }
        }

    }

    //private void tryDropDb(String input, String[] data){
    //    if (data.length!=1){
    //        this.displayInvalidCommandMessage(input);
    //        return;
    //    }
    //    this.repository.unloadData();
    //    OutputWriter.writeMessageOnNewLine("Database dropped!");
    //}

    //private  void tryDownloadFile(String command, String[] data) {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(command);
    //        return;
    //    }
//
    //    String fileUrl = data[1];
    //    this.downloadManager.download(fileUrl);
    //}

    //private  void tryDownloadFileOnNewThread(String command, String[] data) {
    //   if (data.length != 2) {
    //       displayInvalidCommandMessage(command);
    //       return;
    //   }

    //   String fileUrl = data[1];
    //   this.downloadManager.downloadOnNewThread(fileUrl);
    //}

    //private  void tryPrintFilteredStudents(String input, String[] data) {
    //    if (data.length != 5) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String course = data[1];
    //    String filter = data[2].toLowerCase();
    //    String takeCommand = data[3].toLowerCase();
    //    String takeQuantity = data[4].toLowerCase();
//
    //    tryParseParametersForFilter(takeCommand, takeQuantity, course, filter);
    //}

    //private  void tryParseParametersForFilter(
    //        String takeCommand, String takeQuantity,
    //        String courseName, String filter) {
    //    if (!takeCommand.equals("take")) {
    //        OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
    //        return;
    //    }
//
    //    if (takeQuantity.equals("all")) {
    //        this.repository.filterAndTake(courseName, filter);
    //        return;
    //    }
//
    //    try {
    //        int studentsToTake = Integer.parseInt(takeQuantity);
    //        this.repository.filterAndTake(courseName, filter, studentsToTake);
    //    } catch (NumberFormatException nfe) {
    //        OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
    //    }
    //}

    //private  void tryPrintOrderedStudents(String input, String[] data) {
    //    if (data.length != 5) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String courseName = data[1];
    //    String orderType = data[2].toLowerCase();
    //    String takeCommand = data[3].toLowerCase();
    //    String takeQuantity = data[4].toLowerCase();
//
//  //      StudentsRepository.printOrderedStudents(courseName, modifier, numberOfStudents);
    //    tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    //}

    //private  void tryParseParametersForOrder(
    //        String takeCommand, String takeQuantity,
    //        String courseName, String orderType) {
    //    if (!takeCommand.equals("take")) {
    //        OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
    //        return;
    //    }
//
    //    if (takeQuantity.equals("all")) {
    //        this.repository.orderAndTake(courseName, orderType);
    //        return;
    //    }
//
    //    try {
    //        int studentsToTake = Integer.parseInt(takeQuantity);
    //        this.repository.orderAndTake(courseName, orderType, studentsToTake);
    //    } catch (NumberFormatException nfe) {
    //        OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
    //    }
    //}

    //private  void tryShowWantedCourse(String input, String[] data) {
    //    if (data.length != 2 && data.length != 3) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    if (data.length == 2) {
    //        String courseName = data[1];
    //        this.repository.getStudentsByCourse(courseName);
    //    }
//
    //    if (data.length == 3) {
    //        String courseName = data[1];
    //        String userName = data[2];
    //        this.repository.getStudentMarkInCourse(courseName, userName);
    //    }
    //}

    //private  void tryOpenFile(String input, String[] data) throws IOException {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String fileName = data[1];
    //    String filePath = SessionData.currentPath + "\\" + fileName;
    //    File file = new File(filePath);
    //    Desktop.getDesktop().open(file);
    //}

    //private  void tryCompareFiles(String input, String[] data) throws IOException {
    //    if (data.length != 3) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String firstPath = data[1];
    //    String secondPath = data[2];
    //    this.tester.compareContent(firstPath, secondPath);
    //}

    //private  void tryGetHelp(String input, String[] data) {
    //    if (data.length != 1) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    displayHelp();
    //}

    //private  void tryReadDatabaseFromFile(String input, String[] data) throws IOException {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String fileName = data[1];
    //    this.repository.loadData(fileName);
    //}

    //private  void tryChangeAbsolutePath(String input, String[] data) throws IOException {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String absolutePath = data[1];
    //    this.inputOutputManager.changeCurrentDirAbsolute(absolutePath);
    //}

    //private  void tryChangeRelativePath(String input, String[] data) throws IOException {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String relativePath = data[1];
    //    this.inputOutputManager.changeCurrentDirRelativePath(relativePath);
    //}

    //private  void tryTraverseFolders(String input, String[] data) {
    //    if (data.length != 1 && data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    if (data.length == 1) {
    //        this.inputOutputManager.traverseDirectory(0);
    //    }
//
    //    if (data.length == 2) {
    //        this.inputOutputManager.traverseDirectory(Integer.valueOf(data[1]));
    //    }
    //}

    //private  void tryCreateDirectory(String input, String[] data) {
    //    if (data.length != 2) {
    //        displayInvalidCommandMessage(input);
    //        return;
    //    }
//
    //    String folderName = data[1];
    //    this.inputOutputManager.createDirectoryInCurrentFolder(folderName);
    //}

    //private  void displayInvalidCommandMessage(String input) {
    //    String output = String.format("The command '%s' is invalid", input);
    //    OutputWriter.displayException(output);
    //}

    //private  void displayHelp() {
    //    StringBuilder helpBuilder = new StringBuilder();
    //    helpBuilder.append("make directory - mkdir nameOfFolder")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("traverse directory - ls depth")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("comparing files - cmp absolutePath1 absolutePath2")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("change directory - cdRel relativePath or \"..\" for level up")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("change directory - cdAbs absolutePath")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("read students data base - readDb fileName")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("filter students - filter {courseName} excellent/average/poor take 2/5/all")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("order students - order {courseName} ascending/descending take 20/10/all")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("download file - download URL (saved in current directory)")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("download file on new thread - downloadAsynch URL (saved in the current directory)")
    //            .append(System.lineSeparator());
    //    helpBuilder.append("get help – help")
    //            .append(System.lineSeparator());
    //    OutputWriter.writeMessage(helpBuilder.toString());
    //    OutputWriter.writeEmptyLine();
    //}
}
