public enum StatusEnum {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String taskStatus;

    private StatusEnum(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus(){
        return  this.getTaskStatus();
    }
}
