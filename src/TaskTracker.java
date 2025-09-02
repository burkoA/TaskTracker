import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskTracker {

    static {
        final String fileName = "taskManagerList.json";

        if(!Files.exists(Path.of(fileName))){
            try {
                Files.createFile(Path.of(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {

    }
}