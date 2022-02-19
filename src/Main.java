import data.Epic;
import data.Subtask;
import data.Task;
import logic.HistoryManager;
import logic.TaskManager;
import utils.Managers;
import utils.TaskStatus;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        //Создаем 2 эпика
        Epic epic1 = new Epic();
        Epic epic2 = new Epic();

        //Создаем 3 подзадачи
        Subtask subtask1 = new Subtask("Sub1", "Description1", 1);
        Subtask subtask2 = new Subtask("Sub2", "Description2", 1);
        Subtask subtask3 = new Subtask("Sub3", "Description3", 2);

        //Создаем 2 задачи
        Task task1 = new Task();
        Task task2 = new Task();

        //Помещаем в менеджер эпики
        manager.createEpic(epic1);
        manager.createEpic(epic2);

        //Помещаем в менеджер задачи
        manager.createTask(task1);
        manager.createTask(task2);

        //Помещаем в менеджер подзадачи
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);

        manager.getEpicById(1);
        manager.getEpicById(2);
        System.out.println("history   " + historyManager.getHistory());
        manager.getSubtaskById(4);
        manager.getSubtaskById(3);
        System.out.println("history   " + historyManager.getHistory());
        manager.getTaskById(6);
        manager.getTaskById(6);
        manager.getTaskById(6);
        manager.getTaskById(6);
        manager.getTaskById(6);
        manager.getTaskById(6);
        manager.getTaskById(6);
        System.out.println("history   " + historyManager.getHistory());
        System.out.println();

        //выводим списки задач, подзадач и эпиков
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println(manager.getTaskList());
        System.out.println();

        manager.removeAllTasks();
        System.out.println(manager.getTaskList());
        System.out.println();
        manager.createTask(task1);
        manager.createTask(task2);
        System.out.println(manager.getTaskList());
        System.out.println();

        task2.setName("Name task2");
        manager.updateTask(task2);
        System.out.println(manager.getTaskList());
        System.out.println();

        //Меняем статус подзадач
        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.DONE);
        System.out.println(manager.getSubtaskList());


        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        epic1.setStatus(TaskStatus.NEW);
        System.out.println(manager.getEpicList());

        //Удаляем подзадачу и проверяем статус эпика
        //manager.removeSubtaskById(3);
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());

        manager.removeEpicById(1);
        System.out.println(manager.getEpicById(1));
        System.out.println(manager.getSubtaskList());

    }
}
