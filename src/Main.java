import data.Epic;
import data.Subtask;
import data.Task;
import logic.FileBackedTasksManager;
import logic.HistoryManager;
import logic.TaskManager;
import utils.Managers;
import utils.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = Managers.getDefault();

        FileBackedTasksManager fb = new FileBackedTasksManager(new File("test.csv"));

        //Создаем 2 эпика
        Epic epic1 = new Epic("Epic 1", "Description Epic 1");
        Epic epic2 = new Epic("Epic 2", "Description Epic 2");

        //Создаем 3 подзадачи
        Subtask subtask1 = new Subtask("Sub1", "Description1", 1);
        Subtask subtask2 = new Subtask("Sub2", "Description2", 1);
        Subtask subtask3 = new Subtask("Sub3", "Description3", 1);

        //Создаем 2 задачи
        Task task1 = new Task("Task 1", "Description Task 1");
        Task task2 = new Task("Task 2", "Description Task 2");

        task1.setName("New task1 name");
        task1.setDescription("New task1 description");

        subtask1.setStatus(TaskStatus.DONE);
        fb.createTask(task1);
        fb.createTask(task2);
        fb.createEpic(epic1);
        fb.createSubtask(subtask1);
        fb.createSubtask(subtask2);

        fb.getTaskById(6);
        fb.getTaskById(6);
        fb.getTaskById(7);
        fb.getTaskById(7);
        fb.getTaskById(7);
        fb.getTaskById(6);
        fb.getEpicById(1);
        fb.getTaskById(6);
        fb.getSubtaskById(3);

    }
}