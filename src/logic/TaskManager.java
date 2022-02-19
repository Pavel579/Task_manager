package logic;

import data.Epic;
import data.Subtask;
import data.Task;

import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getTaskList();

    void removeAllTasks();

    void createTask(Task task);

    Task getTaskById(int id);

    void updateTask(Task updatedTask);

    void removeTaskById(int id);

    ArrayList<Subtask> getSubtaskList();

    void removeAllSubtasks();

    void createSubtask(Subtask subtask);

    Subtask getSubtaskById(int id);

    void updateSubtask(Subtask updatedSubtask);

    void removeSubtaskById(int id);

    ArrayList<Epic> getEpicList();

    void removeAllEpic();

    void createEpic(Epic epic);

    Task getEpicById(int id);

    void updateEpic(Epic updatedEpic);

    void removeEpicById(int id);

}
