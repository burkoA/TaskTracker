import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskCommands {

    final static String FILE_NAME = "taskManagerList.json";
    final Path path = Path.of(FILE_NAME);
    private final List<String> tasksList = getAllTask();

    // Public Operations

    public void addTask(String description) {
        Task task = new Task(description);
        task.setId(getMaxId());
        tasksList.add(task.formatToJson());

        writeFile();
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    public void updateTask(int taskId, String taskDescription) {
        List<Task> taskList = formatJsonToTaskObject();

        for(Task task : taskList) {
            if(task.getId() != taskId)
                continue;

            task.setDescription(taskDescription);
            task.setUpdatedAt(LocalDateTime.now());
            break;
        }

        convertTaskToString(taskList);
        writeFile();
    }

    public void deleteTask(int taskId) {
        tasksList.remove(taskId);
        writeFile();
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

    // Methods for main functionality

    private int getMaxId() {
        List<Task> taskList = formatJsonToTaskObject();
        int maxValue = -1;

        for(Task task : taskList) {
            maxValue = Math.max(maxValue, task.getId());
        }

        return  maxValue + 1;
    }

    // Methods for reading tasks

    private List<Task> formatJsonToTaskObject() {
        List<Task> taskList = new ArrayList<>();

        for(String task : tasksList) {
            Task newTask = new Task();
            String[] prepared = task.replace("\"","")
                    .replace("{","")
                    .split(",");

            newTask.setId(Integer.parseInt(prepared[0].split(":")[1]));
            newTask.setDescription(prepared[1].split(":")[1]);

            if(StatusEnum.DONE.toString().equals(prepared[2].split(":")[1]))
                newTask.setStatus(StatusEnum.DONE);

            if(StatusEnum.TODO.toString().equals(prepared[2].split(":")[1]))
                newTask.setStatus(StatusEnum.TODO);

            if(StatusEnum.IN_PROGRESS.toString().equals(prepared[2].split(":")[1]))
                newTask.setStatus(StatusEnum.IN_PROGRESS);

            newTask.setCreatedAt(LocalDateTime.parse(prepared[3].split(":",2)[1]));

            if(prepared[4].contains("}")) {
                newTask.setUpdatedAt(LocalDateTime.parse(prepared[4]
                        .replace("}","")
                        .split(":",2)[1]));
            } else {
            newTask.setUpdatedAt(LocalDateTime.parse(prepared[4].split(":",2)[1]));
            }

            taskList.add(newTask);
        }

        return taskList;
    }

    private void convertTaskToString(List<Task> taskList) {
        tasksList.clear();

        for(Task task : taskList) {
            tasksList.add(task.formatToJson());
        }
    }

    private List<String> getAllTask() {
        try {
            String taskString = Files.readString(path);

            if(taskString.replace("[","")
                    .replace("]","").isBlank()) {
                return new ArrayList<>();
            }

            String[] taskArr =  taskString.replace("[","")
                    .replace("]","")
                    .replace("\n","")
                    .split("},");

            return new ArrayList<>(Arrays.stream(taskArr).toList());
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return new ArrayList<>();
    }

    private String loadToFile() {
        StringBuilder task = new StringBuilder();
        task.append("[\n");

        for(int i = 0; i < tasksList.size(); i++) {

            if(i < tasksList.size() - 1) {
                if (!tasksList.get(i).contains("}")) {
                    task.append(tasksList.get(i)).append("}").append(",").append("\n");
                } else {
                    task.append(tasksList.get(i)).append(",").append("\n");
                }
            }
            else {
                task.append(tasksList.get(i));
            }
        }

        task.append("\n]");
        return task.toString();
    }

    private void writeFile() {
        try {
            Files.write(path, loadToFile().getBytes());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
