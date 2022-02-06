import logic.Manager;
import data.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Manager manager = new Manager();

        //Создаем 2 эпика
        Epic epic1 = new Epic();
        Epic epic2 = new Epic();
        System.out.println(epic1);

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


        //выводим списки задач, подзадач и эпиков
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        //System.out.println(manager.getTaskList());
        System.out.println();

        //Меняем статус подзадач
        subtask1.setStatus("NEW");
        subtask2.setStatus("DONE");
        System.out.println(manager.getSubtaskList());


        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        epic1.setStatus("NEW");
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
