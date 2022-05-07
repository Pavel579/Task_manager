package data;

import logic.InMemoryTaskManager;
import utils.TaskStatus;
import utils.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private final int id;
    private final String name;
    private final String description;
    private TaskStatus status = TaskStatus.NEW;
    Duration duration;
    LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.assignId();
    }

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(int id, String name, String description, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
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
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\''
                + ", id=" + id + ", status='" + status + '\'' + '}';
    }

}
