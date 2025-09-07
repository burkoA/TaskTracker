import java.time.LocalDateTime;

public class Task{
    private int id;
    private String description;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
    }

    public Task(String description) {
        this.description = description;
        this.status = StatusEnum.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String formatToJson() {
        return "{" +
                "\"id\"" + ":" + "\"" + this.id + "\"" + "," +
                "\"description\"" + ":" + "\"" + this.description + "\"" + "," +
                "\"status\"" + ":" + "\"" + status.getTaskStatus() + "\"" + "," +
                "\"createdAt\"" + ":" + "\"" + this.createdAt + "\"" + "," +
                "\"updatedAt\"" + ":" + "\"" + this.updatedAt + "\"" + "}";
    }

    @Override
    public String toString(){
        return "[Id" + ":" + this.id + ", " +
                "Description" + ":" + this.description + ", " +
                "Status" + ":" + status.getTaskStatus() + ", " +
                "Created at" + ":" + this.createdAt + ", " +
                "Updated at" + ":" + this.updatedAt +" ]";
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
