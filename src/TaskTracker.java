import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskTracker {

    static {
        final String FILE_NAME = "taskManagerList.json";

        if(!Files.exists(Path.of(FILE_NAME))){
            try {
                Files.createFile(Path.of(FILE_NAME));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Error! Correct syntax - TaskTracker <command> <arg>");
            return;
        }

        TaskCommands taskCommands = new TaskCommands();
        String command = args[0];

        switch (command) {
            case "add":
                if(args.length < 2 || args[1].isBlank()){
                    System.out.println("Error! Correct syntax - TaskTracker add <description>");
                    return;
                }
                taskCommands.addTask(args[1]);
                break;
            case "update":
                if(args.length < 3 || args[2].isBlank()) {
                    System.out.println("Error! Correct syntax - TaskTracker update <task_id> <newDescription>");
                    return;
                }
                try {
                    taskCommands.updateTask(Integer.parseInt(args[1]), args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Error! Second argument must be a number!");
                }
                break;
            case "delete":
                if(args.length < 2){
                    System.out.println("Error! Correct syntax - TaskTracker delete <taskID>");
                    return;
                }
                try {
                    taskCommands.deleteTask(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Error! First argument must be a number!");
                }
                break;
            case "mark-in-progress":
                if(args.length < 2){
                    System.out.println("Error! Correct syntax - TaskTracker mark-in-progress <taskID>");
                    return;
                }
                try {
                    taskCommands.markInProgress(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Error! First argument must be a number!");
                }
                break;
            case "mark-done":
                if(args.length < 2){
                    System.out.println("Error! Correct syntax - TaskTracker mark-done <taskID>");
                    return;
                }
                try {
                    taskCommands.markDone(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Error! First argument must be a number!");
                }
                break;
            case "list":
                if(args.length < 2) {
                    taskCommands.listAllTask();
                } else {
                    switch (args[1]) {
                        case "done" -> taskCommands.listDoneTask();
                        case "todo" -> taskCommands.listToDoTask();
                        case "in-progress" -> taskCommands.listInProgressTask();
                        default -> taskCommands.wrongCommand();
                    }
                }
                break;
            default:
                taskCommands.wrongCommand();
        }
        taskCommands.writeFile();
    }
}