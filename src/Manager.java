import java.util.ArrayList;

public class Manager {
    private static int id = 0;
    private ArrayList<Task> taskList = new ArrayList<>();
    private ArrayList<Subtask> subtaskList = new ArrayList<>();
    private ArrayList<Epic> epicList = new ArrayList<>();

    @Override
    public String toString() {
        return "Manager{" +
                "taskList=" + taskList +
                ", subtaskList=" + subtaskList +
                ", epicList=" + epicList +
                '}';
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void createTask(Task task) {
        taskList.add(task);
    }

    public Task getTaskById(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void updateTask(int id, Task updatedTask) {
        for (int i=0; i<taskList.size(); i++){
            if (taskList.get(i).getId() == id){
                taskList.set(i, updatedTask);
            }
        }

    }

    public void removeTaskById(int id) {
        for (int i=0; i<taskList.size(); i++){
            if (taskList.get(i).getId() == id){
                taskList.remove(i);
                break;
            }
        }

    }



    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
    }

    public void removeAllSubtasks() {
        subtaskList.clear();
    }

    public void createSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    public Task getSubtaskById(int id) {
        for (Subtask subtask : subtaskList) {
            if (subtask.getId() == id) {
                return subtask;
            }
        }
        return null;
    }

    public void updateSubtask(int id, Subtask updatedSubtask) {
        for (int i=0; i<subtaskList.size(); i++){
            if (subtaskList.get(i).getId() == id){
                subtaskList.set(i, updatedSubtask);
            }
        }

    }

    public void removeSubtaskById(int id) {
        for (int i=0; i<subtaskList.size(); i++){
            if (subtaskList.get(i).getId() == id){
                subtaskList.remove(i);
                break;
            }
        }

    }



    public ArrayList<Epic> getEpicList() {
        return epicList;
    }

    public void removeAllEpic() {
        epicList.clear();
    }

    public void createEpic(Epic epic) {
        epicList.add(epic);
    }

    public Task getEpicById(int id) {
        for (Epic epic : epicList) {
            if (epic.getId() == id) {
                return epic;
            }
        }
        return null;
    }

    public void updateEpic(int id, Epic updatedEpic) {
        for (int i=0; i<epicList.size(); i++){
            if (epicList.get(i).getId() == id){
                epicList.set(i, updatedEpic);
            }
        }

    }

    public void removeEpicById(int id) {
        for (int i=0; i<epicList.size(); i++){
            if (epicList.get(i).getId() == id){
                epicList.remove(i);
                break;
            }
        }

    }



    public ArrayList<Task> getSubtasksListOfEpic(Epic epic){
        return epic.getEpicSubtasksList();
    }


    //Метод для присваивания уникального id классу Task и его наследников
    public static int assignId() {
        id++;
        return id;
    }
}
