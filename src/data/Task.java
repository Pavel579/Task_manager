package data;

import logic.InMemoryTaskManager;
import utils.TaskStatus;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;

    public Task() {
        this.id = InMemoryTaskManager.assignId();
        status = TaskStatus.NEW;
        System.out.println(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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


}
