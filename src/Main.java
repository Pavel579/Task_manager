public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Manager manager = new Manager();

        //Создаем 2 эпика
        Epic epic1 = new Epic();
        Epic epic2 = new Epic();

        //Создаем 3 подзадачи
        Subtask subtask1 = new Subtask();
        Subtask subtask2 = new Subtask();
        Subtask subtask3 = new Subtask();

        //Создаем 2 задачи
        Task task1 = new Task();
        Task task2 = new Task();

        //Помещаем в менеджер задачи
        manager.createTask(task1);
        manager.createTask(task2);

        //Помещаем в менеджер подзадачи
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);

        //Помещаем в менеджер эпики
        manager.createEpic(epic1);
        manager.createEpic(epic2);

        //Добавляем подзадачи в эпики
        manager.addSubtask(epic1, subtask1);
        manager.addSubtask(epic1, subtask2);
        manager.addSubtask(epic2, subtask3);

        //выводим списки задач, подзадач и эпиков
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println(manager.getTaskList());
        System.out.println();

        //Меняем статус подзадач
        subtask1.setStatus("DONE");
        subtask2.setStatus("NEW");
        subtask1.setName("Subtask1");
        manager.updateSubtask(3, subtask1);
        manager.updateSubtask(4, subtask2);
        System.out.println(manager.getEpicList());
        System.out.println("Epic1 status - " + manager.getEpicById(1).getStatus());

        //Удаляем подзадачу и проверяем статус эпика
        manager.removeSubtaskById(3);
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println("Epic1 status - " + manager.getEpicById(1).getStatus());


        //Выводим содержание класса менеджер
        System.out.println(manager);


    }
}
