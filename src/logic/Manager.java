package logic;

import java.util.*;

import data.*;

public class Manager {
    private static int id = 0;

    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();

    //Метод возвращает список задач Task
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    //Метод удаляет все задачи Task
    public void removeAllTasks() {
        taskList.clear();
    }

    //Метод создает задачу Task
    public void createTask(Task task) {
        taskList.put(task.getId(), task);
    }

    //Метод возвращает задачу Task по ее идентификатору
    public Task getTaskById(int id) {
        return taskList.get(id);
    }

    //Метод обновляет задачу Task
    public void updateTask(Task updatedTask) {
        taskList.replace(updatedTask.getId(), updatedTask);
    }

    //Метод удаляет задачу Task по ее идентификатору
    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    //Метод возвращает список задач Subtask
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    //Метод удаляет все задачи Subtask
    public void removeAllSubtasks() {
        subtaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtaskIdInEpic().clear();
            epic.checkEpicStatus(subtaskList);
        }
    }

    //Метод создает задачу Subtask
    public void createSubtask(Subtask subtask) {
        subtaskList.put(subtask.getId(), subtask);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.addSubtaskIdInEpic(subtask.getId());
        epic.checkEpicStatus(subtaskList);
    }

    //Метод возвращает задачу Subtask по ее идентификатору
    public Subtask getSubtaskById(int id) {
        return subtaskList.get(id);
    }

    //Метод обновляет задачу Subtask
    public void updateSubtask(Subtask updatedSubtask) {
        subtaskList.replace(updatedSubtask.getId(), updatedSubtask);
        Epic epic = epicList.get(updatedSubtask.getEpicId());
        epic.checkEpicStatus(subtaskList);

    }

    //Метод удаляет задачу Subtask по ее идентификатору
    public void removeSubtaskById(int id) {
        subtaskList.remove(id);
        for (Epic epic : epicList.values()) {
            for (int i = 0; i < epic.getSubtaskIdInEpic().size(); i++) {
                if (epic.getSubtaskIdInEpic().get(i) == id) {
                    epic.getSubtaskIdInEpic().remove(i);
                    epic.checkEpicStatus(subtaskList);
                    return;
                }
            }
        }

    }

    //Метод возвращает список задач Epic
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    //Метод удаляет все задачи Epic
    public void removeAllEpic() {
        epicList.clear();
        subtaskList.clear();
    }

    //Метод создает задачу Epic
    public void createEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    //Метод возвращает задачу Epic по ее идентификатору
    public Task getEpicById(int id) {
        return epicList.get(id);
    }

    //Метод обновляет задачу Epic
    public void updateEpic(Epic updatedEpic) {
        epicList.replace(updatedEpic.getId(), updatedEpic);
    }

    //Метод удаляет задачу Epic по ее идентификатору
    public void removeEpicById(int id) {
        epicList.remove(id);

        Set<Map.Entry<Integer, Subtask>> entrySet = subtaskList.entrySet();

        Iterator<Map.Entry<Integer, Subtask>> itr = entrySet.iterator();

        while (itr.hasNext()) {
            Map.Entry<Integer, Subtask> entry = itr.next();
            Subtask subtask = entry.getValue();

            if (subtask.getEpicId() == id) {
                itr.remove();
            }

        }
    }


    //Метод для присваивания уникального id классу Task и его наследников
    public static int assignId() {
        id++;
        return id;
    }


}
