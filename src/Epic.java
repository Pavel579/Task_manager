import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Task> epicSubtasksList = new ArrayList<>();

    Epic() {
        this.setStatus("NEW");
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicSubtasksList=" + epicSubtasksList +
                '}';
    }

    //Метод добавляет подзадачу в эпик
    public void addSubtask(Subtask subtask) {
        epicSubtasksList.add(subtask);
        checkEpicStatus();
    }

    //Метод заменяет подзадачу обновленной
    public void setSubtask(Task subtask){
        for (int i=0; i<epicSubtasksList.size(); i++){
            if (epicSubtasksList.get(i).getId() == subtask.getId()){
                epicSubtasksList.set(i, subtask);
            }
        }
        checkEpicStatus();
    }

    //Метод вычисляет статус эпика
    private void checkEpicStatus(){
        int valueNew=0;
        int valueInProgress = 0;
        int valueDone = 0;
        for (Task epicSubtask : epicSubtasksList){
            if (epicSubtask.getStatus().equals("NEW")){
                valueNew++;
            }else if (epicSubtask.getStatus().equals("IN_PROGRESS")){
                valueInProgress++;
            }else if (epicSubtask.getStatus().equals("DONE")){
                valueDone++;
            }
        }
        if (valueNew == epicSubtasksList.size() || epicSubtasksList.size()==0){
            this.setStatus("NEW");
        }else if (valueDone == epicSubtasksList.size()){
            this.setStatus("DONE");
        }else {
            this.setStatus("IN_PROGRESS");
        }
    }

    public ArrayList<Task> getEpicSubtasksList() {
        return epicSubtasksList;
    }
}
