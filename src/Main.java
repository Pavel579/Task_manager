import data.Epic;
import data.Subtask;
import data.Task;
import logic.HistoryManager;
import logic.TaskManager;
import utils.Managers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = Managers.getDefault();

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

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);

        manager.getTaskById(6);
        manager.getTaskById(7);
        manager.getTaskById(6);
        manager.getSubtaskById(3);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getEpicById(1);
        manager.getEpicById(2);
        manager.getEpicById(2);
        manager.getEpicById(1);

        System.out.println("history   " + historyManager.getHistory());

        manager.removeTaskById(6);
        System.out.println("history   " + historyManager.getHistory());
        manager.removeAllTasks();
        System.out.println("history   " + historyManager.getHistory());

        manager.removeEpicById(1);
        System.out.println("history   " + historyManager.getHistory());

    }
}