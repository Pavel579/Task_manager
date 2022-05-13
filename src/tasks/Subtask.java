package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, int epicId, Duration duration, LocalDateTime startTime) {
        super(id, name, description, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return this.epicId;
    }

    @Override
    public Type getClassType() {
        return Type.SUBTASK;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
    }

    @Override
    public String toString() {
        return "Subtask{" + "epicId=" + epicId + " Status=" + getStatus() + ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' + ", id=" + getId() + '}';
    }

}
