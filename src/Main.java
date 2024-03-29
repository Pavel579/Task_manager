import logic.FileBackedTasksManager;
import logic.HTTPTaskManager;
import logic.TaskManager;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utils.Managers;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Пришло время практики!");
        new KVServer().start();
        HttpTaskServer.createServer();

        TaskManager fb = Managers.getHttpManager();
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description Epic 2");
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Task task1 = new Task(6, "Task 1", "Description Task 1", Duration.ofDays(5),
                LocalDateTime.of(199, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1999, 6, 1, 0, 0, 0, 0));
        fb.createTask(task1);
        fb.createTask(task2);
        fb.createEpic(epic1);
        fb.createEpic(epic2);
        fb.createSubtask(subtask1);
        fb.createSubtask(subtask2);
        fb.getTaskById(6);
        fb.getTaskById(7);
        fb.getSubtaskById(3);
        fb.getEpicById(1);
        fb.getTaskById(6);
    }
}