import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Task> epicSubtasksList = new ArrayList<>();
    private String epicStatus;

    Epic() {
        epicStatus = "DONE";
    }

    @Override
    public void setStatus(String status) {

    }

    private void setEpicStatus(String status) {
        epicStatus = status;
    }

    @Override
    public String getStatus() {
        return this.epicStatus;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicSubtasksList=" + epicSubtasksList +
                '}';
    }


    public ArrayList<Task> getEpicSubtasksList() {
        return epicSubtasksList;
    }

    //Метод вычисляет статус эпика
    public void checkEpicStatus() {
        int valueNew = 0;
        int valueInProgress = 0;
        int valueDone = 0;
        for (Task epicSubtask : getEpicSubtasksList()) {
            if (epicSubtask.getStatus().equals("NEW")) {
                valueNew++;
            } else if (epicSubtask.getStatus().equals("IN_PROGRESS")) {
                valueInProgress++;
            } else if (epicSubtask.getStatus().equals("DONE")) {
                valueDone++;
            }
        }
        if (valueNew == getEpicSubtasksList().size() || getEpicSubtasksList().size() == 0) {
            setEpicStatus("NEW");
        } else if (valueDone == getEpicSubtasksList().size()) {
            setEpicStatus("DONE");
        } else {
            setEpicStatus("IN_PROGRESS");
        }
    }
}
