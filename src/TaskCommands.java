import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskCommands {

    final static String FILE_NAME = "taskManagerList.json";
    final Path path = Path.of(FILE_NAME);
    Task task;

    public void addTask(String description) {
        if(description.contains("\"")){
            description = removeQuotes(description);
        }

        task = new Task(description);
        int maxValue = getMaxValue();
        task.setId(maxValue);
        String fileText = allTextWithoutLastBracket() + task.formatToJson() + "\n" + "]";

        try {
            Files.write(path, fileText.getBytes());
            System.out.println("Task added successfully (ID: " + task.getId() + ")");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void wrongCommand() {
        System.out.println("Wrong! Command list: \n" + "1. add \n"
                + "2. update \n"
                + "3. delete \n"
                + "4. mark-in-progress \n"
                + "5. mark-done \n"
                + "6. list \n"
                + "7. list done \n"
                + "8. list todo \n"
                + "9. list in-progress \n");
    }

    private String removeQuotes(String text) {
        return text.replace("\"","");
    }

    private String allTextWithoutLastBracket() {
        try {
            return Files.readString(path).replace("]","");
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return "";
    }

    private String[] jsonFormatter() {
        String text = allTextWithoutLastBracket();

        if(text.replace("[","").isBlank()) {
            return new String[0];
        }

        String[] taskList = text.replace("[","")
                .replace("]","")
                .replace("\n","")
                .replace("\"","")
                .split("},");

        return taskList;
    }

    private int getMaxValue() {
        String[] value = jsonFormatter();

        if(value.length == 0)
            return 0;

        int id = -1;

        for(String task : value) {
            task = task.replace("{", "").replace("}", "");
            String[] parts = task.split(",");

            for(String part : parts) {
                int parseValue;
                if (part.trim().startsWith("id:")) {
                    parseValue = Integer.parseInt(part.split(":")[1].trim());
                    id = Math.max(parseValue,id);
                    break;
                }
            }
        }

        return id+1;
    }
}
