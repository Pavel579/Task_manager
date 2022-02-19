package data;

import utils.TaskStatus;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        setStatus(TaskStatus.NEW);
        setName(name);
        setDescription(description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return this.epicId;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public TaskStatus getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }

    @Override
    public String toString() {
        return "Subtask{" + "epicId=" + epicId + " Status=" + getStatus() + '}';
    }

}
