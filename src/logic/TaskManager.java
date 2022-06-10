package logic;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TaskManager {
    //Метод возвращает список задач Task
    ArrayList<Task> getTaskList();

    //Метод удаляет все задачи Task
    void removeAllTasks();

    //Метод создает задачу Task
    void createTask(Task task);

    //Метод возвращает задачу Task по ее идентификатору
    Task getTaskById(int id);

    //Метод обновляет задачу Task
    void updateTask(Task updatedTask);

    //Метод удаляет задачу Task по ее идентификатору
    void removeTaskById(int id);

    //Метод возвращает список задач Subtask
    ArrayList<Subtask> getSubtaskList();

    //Метод удаляет все задачи Subtask
    void removeAllSubtasks();

    //Метод создает задачу Subtask
    void createSubtask(Subtask subtask);

    //Метод возвращает задачу Subtask по ее идентификатору
    Subtask getSubtaskById(int id);

    //Метод обновляет задачу Subtask
    void updateSubtask(Subtask updatedSubtask);

    //Метод удаляет задачу Subtask по ее идентификатору
    void removeSubtaskById(int id);

    //Метод возвращает список задач Epic
    ArrayList<Epic> getEpicList();

    //Метод удаляет все задачи Epic
    void removeAllEpic();

    //Метод создает задачу Epic
    void createEpic(Epic epic);

    //Метод возвращает задачу Epic по ее идентификатору
    Task getEpicById(int id);

    //Метод обновляет задачу Epic
    void updateEpic(Epic updatedEpic);

    //Метод удаляет задачу Epic по ее идентификатору
    void removeEpicById(int id);

    //Метод проверяет пересечение задач по времени
    boolean isTaskNotCrossed(Task task);

    //Метод возвращает задачи в порядке приоритета
    Set<Task> getPrioritizedTasks();

    //Метод возвращает список подзадач в Эпике
    List<Task> getEpicSubtasks(int id);

    //Метод возвращает объект Менеджера Истории
    HistoryManager getHistoryInMemory();
}
