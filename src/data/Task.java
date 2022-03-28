package data;

import logic.InMemoryTaskManager;
import utils.TaskStatus;
import utils.TaskType;

import java.util.Objects;

public class Task {
    private final int id;
    private final String name;
    private final String description;
    private TaskStatus status = TaskStatus.NEW;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.assignId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public TaskType getClassType() {
        return TaskType.TASK;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' +
                ", id=" + id + ", status='" + status + '\'' + '}';
    }

}
