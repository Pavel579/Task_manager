package Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIdInEpic = new ArrayList<>();


    public Epic() {
        super.setStatus("NEW");
    }

    @Override
    public void setStatus(String status) {

    }

    @Override
    public String toString() {
        return "Epic{" +
                "SubtaskIdInEpic=" + subtaskIdInEpic + '\'' +
                "Epic status=" + getStatus() +
                '}';
    }

    @Override
    public String getStatus() {
        return super.getStatus();
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
            if (subtaskList.get(subtaskId).getStatus().equals("NEW")) {
                valueNew++;
            } else if (subtaskList.get(subtaskId).getStatus().equals("DONE")) {
                valueDone++;
            }
        }
        if (valueNew == subtaskIdInEpic.size() || subtaskIdInEpic.size() == 0) {
            super.setStatus("NEW");
        } else if (valueDone == subtaskIdInEpic.size()) {
            super.setStatus("DONE");
        } else {
            super.setStatus("IN_PROGRESS");
        }
    }

}
