package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIdInEpic = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public void checkDurationAndStartTime(HashMap<Integer, Subtask> subtaskList) {
        if (!subtaskIdInEpic.isEmpty()) {
            startTime = subtaskList.get(subtaskIdInEpic.get(0)).startTime;
            endTime = subtaskList.get(subtaskIdInEpic.get(0)).getEndTime();

            for (Integer subtaskId : subtaskIdInEpic) {
                if (subtaskList.get(subtaskId).startTime.isBefore(startTime)) {
                    startTime = subtaskList.get(subtaskId).startTime;
                }
                if (subtaskList.get(subtaskId).getEndTime().isAfter(endTime)) {
                    endTime = subtaskList.get(subtaskId).getEndTime();
                }
            }
            duration = Duration.between(startTime, endTime);
        }
    }

    @Override
    public Type getClassType() {
        return Type.EPIC;
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Status status) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Integer> getSubtaskIdInEpic() {
        return subtaskIdInEpic;
    }

    public void addSubtaskIdInEpic(int id) {
        subtaskIdInEpic.add(id);
    }

    //Метод вычисляет статус эпика
    public void checkEpicStatus(HashMap<Integer, Subtask> subtaskList) {
        int valueNew = 0;
        int valueDone = 0;
        for (Integer subtaskId : subtaskIdInEpic) {
            if (subtaskList.get(subtaskId).getStatus().equals(Status.NEW)) {
                valueNew++;
            } else if (subtaskList.get(subtaskId).getStatus().equals(Status.DONE)) {
                valueDone++;
            }
        }
        if (valueNew == subtaskIdInEpic.size() || subtaskIdInEpic.size() == 0) {
            super.setStatus(Status.NEW);
        } else if (valueDone == subtaskIdInEpic.size()) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Epic{" + "SubtaskIdInEpic=" + subtaskIdInEpic + '\'' + "Epic status=" + getStatus() + ", name='" +
                getName() + '\'' + ", description='" + getDescription() + '\'' + ", id=" + getId() + '}';
    }

}
