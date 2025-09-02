import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskTracker {

    static {
        final String FILE_NAME = "taskManagerList.json";

        if(!Files.exists(Path.of(FILE_NAME))){
            try {
                Files.createFile(Path.of(FILE_NAME));
                Files.writeString(Path.of(FILE_NAME),"[\n"+"]");
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
                break;
            case "delete":
                break;
            case "mark-in-progress":
                break;
            case "mark-done":
                break;
            case "list":
                break;
            case "list done":
                break;
            case "list todo":
                break;
            case "list in-progress":
                break;
            default:
                taskCommands.wrongCommand();
        }
    }
}