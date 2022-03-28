package data;

import utils.TaskStatus;
import utils.TaskType;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return this.epicId;
    }

    @Override
    public TaskType getClassType() {
        return TaskType.SUBTASK;
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
        return "Subtask{" + "epicId=" + epicId + " Status=" + getStatus() + ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' + ", id=" + getId() + '}';
    }

}
