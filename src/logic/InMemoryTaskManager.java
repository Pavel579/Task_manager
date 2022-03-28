package logic;

import data.Epic;
import data.Subtask;
import data.Task;
import utils.Managers;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    static int id = 0;
    final HashMap<Integer, Task> taskList = new HashMap<>();
    final HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    final HashMap<Integer, Epic> epicList = new HashMap<>();
    final HistoryManager historyInMemory = Managers.getDefaultHistory();

    //Метод для присваивания уникального id классу Task и его наследников
    public static int assignId() {
        return ++id;
    }

    //Метод возвращает список задач Task
    @Override
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    //Метод удаляет все задачи Task
    @Override
    public void removeAllTasks() {
        for (Task task : taskList.values()) {
            historyInMemory.remove(task.getId());
        }
        taskList.clear();
    }

    //Метод создает задачу Task
    @Override
    public void createTask(Task task) {
        if (task != null) {
            taskList.put(task.getId(), task);
        } else {
            System.out.println("Задача не существует.");
        }

    }

    //Метод возвращает задачу Task по ее идентификатору
    @Override
    public Task getTaskById(int id) {
        if (taskList.containsKey(id)) {
            historyInMemory.add(taskList.get(id));
            return taskList.get(id);
        } else {
            System.out.println("Такой id не существует");
            return null;
        }

    }

    //Метод обновляет задачу Task
    @Override
    public void updateTask(Task updatedTask) {
        if (updatedTask != null) {
            taskList.replace(updatedTask.getId(), updatedTask);
        } else {
            System.out.println("Задача не существует");
        }

    }

    //Метод удаляет задачу Task по ее идентификатору
    @Override
    public void removeTaskById(int id) {
        if (taskList.containsKey(id)) {
            historyInMemory.remove(id);
            taskList.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("Задачи с таким id нет. Задача не удалена");
        }

    }

    //Метод возвращает список задач Subtask
    @Override
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    //Метод удаляет все задачи Subtask
    @Override
    public void removeAllSubtasks() {
        for (Task task : subtaskList.values()) {
            historyInMemory.remove(task.getId());
        }
        subtaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtaskIdInEpic().clear();
            epic.checkEpicStatus(subtaskList);
        }
    }

    //Метод создает задачу Subtask
    @Override
    public void createSubtask(Subtask subtask) {
        if (subtask != null) {
            Epic epic = epicList.get(subtask.getEpicId());
            if (epic != null) {
                subtaskList.put(subtask.getId(), subtask);
                epic.addSubtaskIdInEpic(subtask.getId());
                epic.checkEpicStatus(subtaskList);
            } else {
                System.out.println("Эпика для подзадачи не существует");
            }

        } else {
            System.out.println("Такой подзадачи не существует");
        }

    }

    //Метод возвращает задачу Subtask по ее идентификатору
    @Override
    public Subtask getSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
            historyInMemory.add(subtaskList.get(id));
            return subtaskList.get(id);
        } else {
            System.out.println("Подзадачи с таким id нет.");
            return null;
        }

    }

    //Метод обновляет задачу Subtask
    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        if (updatedSubtask != null) {
            Epic epic = epicList.get(updatedSubtask.getEpicId());
            if (epic != null) {
                subtaskList.replace(updatedSubtask.getId(), updatedSubtask);
                epic.checkEpicStatus(subtaskList);
            } else {
                System.out.println("Эпика для подзадачи не существует");
            }
        } else {
            System.out.println("Такой подзадачи не существует");
        }

    }

    //Метод удаляет задачу Subtask по ее идентификатору
    @Override
    public void removeSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
            historyInMemory.remove(id);
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
        } else {
            System.out.println("Подзадачи с таким id нет. Задача не удалена");
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
        for (Task task : subtaskList.values()) {
            historyInMemory.remove(task.getId());
        }
        epicList.clear();
        subtaskList.clear();
    }

    //Метод создает задачу Epic
    @Override
    public void createEpic(Epic epic) {
        if (epic != null) {
            epicList.put(epic.getId(), epic);
        } else {
            System.out.println("Такого эпика не существует");
        }
    }

    //Метод возвращает задачу Epic по ее идентификатору
    @Override
    public Task getEpicById(int id) {
        if (epicList.containsKey(id)) {
            historyInMemory.add(epicList.get(id));
            return epicList.get(id);
        } else {
            System.out.println("Эпика с таким id нет");
            return null;
        }
    }

    //Метод обновляет задачу Epic
    @Override
    public void updateEpic(Epic updatedEpic) {
        if (updatedEpic != null) {
            epicList.replace(updatedEpic.getId(), updatedEpic);
        } else {
            System.out.println("Такого эпика не существует");
        }
    }

    //Метод удаляет задачу Epic по ее идентификатору
    @Override
    public void removeEpicById(int id) {
        if (epicList.containsKey(id)) {
            for (Integer subtaskId : epicList.get(id).getSubtaskIdInEpic()) {
                historyInMemory.remove(subtaskId);
            }
            historyInMemory.remove(id);
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
        } else {
            System.out.println("Эпика с таким id не существует. Эпик не удален");
        }
    }
}