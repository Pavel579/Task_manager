package data;

import utils.TaskStatus;
import utils.TaskType;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIdInEpic = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public TaskType getClassType() {
        return TaskType.EPIC;
    }

    @Override
    public TaskStatus getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(TaskStatus status) {
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
            if (subtaskList.get(subtaskId).getStatus().equals(TaskStatus.NEW)) {
                valueNew++;
            } else if (subtaskList.get(subtaskId).getStatus().equals(TaskStatus.DONE)) {
                valueDone++;
            }
        }
        if (valueNew == subtaskIdInEpic.size() || subtaskIdInEpic.size() == 0) {
            super.setStatus(TaskStatus.NEW);
        } else if (valueDone == subtaskIdInEpic.size()) {
            super.setStatus(TaskStatus.DONE);
        } else {
            super.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Epic{" + "SubtaskIdInEpic=" + subtaskIdInEpic + '\'' + "Epic status=" + getStatus() + ", name='" +
                getName() + '\'' + ", description='" + getDescription() + '\'' + ", id=" + getId() + '}';
    }

}
