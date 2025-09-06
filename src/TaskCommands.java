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
    private final List<String> tasksStringList = getAllTask();
    private final List<Task> tasksObjectList = formatJsonToTaskObject();

    // Public Operations

    public void addTask(String description) {
        Task task = new Task(description);
        task.setId(getMaxId());
        tasksStringList.add(task.formatToJson());
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    public void updateTask(int taskId, String taskDescription) {
        for(Task task : tasksObjectList) {
            if(task.getId() != taskId)
                continue;

            task.setDescription(taskDescription);
            task.setUpdatedAt(LocalDateTime.now());
            break;
        }

        convertTaskToString(tasksObjectList);
    }

    public void markInProgress(int taskId) {
        for(Task task : tasksObjectList) {
            if(task.getId() != taskId)
                continue;

            task.changeStatusToInProgress();
            break;
        }

        convertTaskToString(tasksObjectList);
    }

    public void markDone(int taskId) {
        for(Task task : tasksObjectList) {
            if(task.getId() != taskId)
                continue;

            task.changeStatusToDone();
            break;
        }

        convertTaskToString(tasksObjectList);
    }

    public void deleteTask(int taskId) {
        convertTaskToString(tasksObjectList.stream()
                .filter(task -> task.getId() != taskId)
                .toList());
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

        for(String task : tasksStringList) {
            Task newTask = new Task();
            String[] prepared = task.replace("\"","")
                    .replace("{","")
                    .split(",");

            newTask.setId(Integer.parseInt(prepared[0].split(":")[1]));
            newTask.setDescription(prepared[1].split(":")[1]);

            if(StatusEnum.DONE.getTaskStatus().equals(prepared[2].split(":")[1]))
                newTask.setStatus(StatusEnum.DONE);

            if(StatusEnum.TODO.getTaskStatus().equals(prepared[2].split(":")[1]))
                newTask.setStatus(StatusEnum.TODO);

            if(StatusEnum.IN_PROGRESS.getTaskStatus().equals(prepared[2].split(":")[1]))
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
        tasksStringList.clear();

        for(Task task : taskList) {
            tasksStringList.add(task.formatToJson());
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

        for(int i = 0; i < tasksStringList.size(); i++) {

            if(i < tasksStringList.size() - 1) {
                if (!tasksStringList.get(i).contains("}")) {
                    task.append(tasksStringList.get(i)).append("}").append(",").append("\n");
                } else {
                    task.append(tasksStringList.get(i)).append(",").append("\n");
                }
            }
            else {
                task.append(tasksStringList.get(i));
            }
        }

        task.append("\n]");
        return task.toString();
    }

    public void writeFile() {
        try {
            Files.write(path, loadToFile().getBytes());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
