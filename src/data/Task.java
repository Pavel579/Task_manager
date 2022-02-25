package data;

import logic.InMemoryTaskManager;
import utils.TaskStatus;

public class Task {
    private final int id;
    private String name;
    private String description;
    private TaskStatus status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.assignId();
        status = TaskStatus.NEW;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        if (status.equals(TaskStatus.NEW) || status.equals(TaskStatus.IN_PROGRESS) || status.equals(TaskStatus.DONE)) {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", id=" + id + ", status='" + status + '\'' + '}';
    }

}
