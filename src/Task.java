import java.sql.Time;

public class Task {
    private int id;
    private String description;
    private StatusEnum status;
    private Time createdAt;
    private Time updatedAt;

    public Task(int id, String description, StatusEnum status, Time createdAt, Time updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Time getCreatedAt() {
        return createdAt;
    }

    public Time getUpdatedAt() {
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

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Time updatedAt) {
        this.updatedAt = updatedAt;
    }
}
