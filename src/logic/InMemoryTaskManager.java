package logic;

import data.Epic;
import data.Subtask;
import data.Task;
import utils.Managers;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static int id = 0;
    private final HashMap<Integer, Task> taskList = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private final HashMap<Integer, Epic> epicList = new HashMap<>();
    private final HistoryManager historyInMemory = Managers.getDefaultHistory();

    //Метод для присваивания уникального id классу Task и его наследников
    public static int assignId() {
        id++;
        return id;
    }

    //Метод возвращает список задач Task
    @Override
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    //Метод удаляет все задачи Task
    @Override
    public void removeAllTasks() {
        taskList.clear();
    }

    //Метод создает задачу Task
    @Override
    public void createTask(Task task) {
        taskList.put(task.getId(), task);
    }

    //Метод возвращает задачу Task по ее идентификатору
    @Override
    public Task getTaskById(int id) {
        historyInMemory.add(taskList.get(id));
        return taskList.get(id);
    }

    //Метод обновляет задачу Task
    @Override
    public void updateTask(Task updatedTask) {
        taskList.replace(updatedTask.getId(), updatedTask);
    }

    //Метод удаляет задачу Task по ее идентификатору
    @Override
    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    //Метод возвращает список задач Subtask
    @Override
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    //Метод удаляет все задачи Subtask
    @Override
    public void removeAllSubtasks() {
        subtaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtaskIdInEpic().clear();
            epic.checkEpicStatus(subtaskList);
        }
    }

    //Метод создает задачу Subtask
    @Override
    public void createSubtask(Subtask subtask) {
        subtaskList.put(subtask.getId(), subtask);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.addSubtaskIdInEpic(subtask.getId());
        epic.checkEpicStatus(subtaskList);
    }

    //Метод возвращает задачу Subtask по ее идентификатору
    @Override
    public Subtask getSubtaskById(int id) {
        historyInMemory.add(subtaskList.get(id));
        return subtaskList.get(id);
    }

    //Метод обновляет задачу Subtask
    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        subtaskList.replace(updatedSubtask.getId(), updatedSubtask);
        Epic epic = epicList.get(updatedSubtask.getEpicId());
        epic.checkEpicStatus(subtaskList);

    }

    //Метод удаляет задачу Subtask по ее идентификатору
    @Override
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
    @Override
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    //Метод удаляет все задачи Epic
    @Override
    public void removeAllEpic() {
        epicList.clear();
        subtaskList.clear();
    }

    //Метод создает задачу Epic
    @Override
    public void createEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    //Метод возвращает задачу Epic по ее идентификатору
    @Override
    public Task getEpicById(int id) {
        historyInMemory.add(epicList.get(id));
        return epicList.get(id);
    }

    //Метод обновляет задачу Epic
    @Override
    public void updateEpic(Epic updatedEpic) {
        epicList.replace(updatedEpic.getId(), updatedEpic);
    }

    //Метод удаляет задачу Epic по ее идентификатору
    @Override
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

}
