public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void Check(){
        isDone = true;
    }

    public void Uncheck(){
        isDone = false;
    }

}
