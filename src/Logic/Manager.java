package Logic;

import java.util.ArrayList;
import java.util.HashMap;

import Data.*;

public class Manager {
    private static int id = 0;

    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();


    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void createTask(Task task) {
        taskList.put(task.getId(), task);
    }

    public Task getTaskById(int id) {
        return taskList.get(id);
    }

    public void updateTask(Task updatedTask) {
        taskList.replace(updatedTask.getId(), updatedTask);
    }

    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    //****************  Subtask  ***************
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    public void removeAllSubtasks() {
        subtaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtaskIdInEpic().clear();
            epic.checkEpicStatus(subtaskList);
        }
    }

    public void createSubtask(Subtask subtask) {
        subtaskList.put(subtask.getId(), subtask);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.addSubtaskIdInEpic(subtask.getId());
        epic.checkEpicStatus(subtaskList);
    }

    public Subtask getSubtaskById(int id) {
        return subtaskList.get(id);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subtaskList.replace(updatedSubtask.getId(), updatedSubtask);
        Epic epic = epicList.get(updatedSubtask.getEpicId());
        epic.checkEpicStatus(subtaskList);

    }

    public void removeSubtaskById(int id) {
        subtaskList.remove(id);
        for (Epic epic : epicList.values()) {
            for (int i = 0; i < epic.getSubtaskIdInEpic().size(); i++) {
                if (epic.getSubtaskIdInEpic().get(i) == id) {
                    epic.getSubtaskIdInEpic().remove(i);
                    epic.checkEpicStatus(subtaskList);
                    break;
                }
            }
        }

    }

    //****************   Epic   **********************
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public void removeAllEpic() {
        epicList.clear();
        for (Subtask subtask : subtaskList.values()){
            subtask.setEpicId(0);
        }
    }

    public void createEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    public Task getEpicById(int id) {
        return epicList.get(id);
    }

    public void updateEpic(Epic updatedEpic) {
        epicList.replace(updatedEpic.getId(), updatedEpic);
    }

    public void removeEpicById(int id) {
        epicList.remove(id);
        for (Subtask subtask : subtaskList.values()){
            if (subtask.getEpicId() == id){
                subtask.setEpicId(0);
            }
        }

    }


    //Метод для присваивания уникального id классу Task и его наследников
    public static int assignId() {
        id++;
        return id;
    }


}
