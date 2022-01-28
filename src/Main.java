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

        //Помещаем в эпики подзадачи
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic2.addSubtask(subtask3);

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

        //выводим списки задач, подзадач и эпиков
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println(manager.getTaskList());

        //Меняем статус задачи на IN_PROGRESS и выводим заново
        System.out.println(manager.getTaskById(6).getStatus());
        manager.getTaskById(6).setStatus("IN_PROGRESS");
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getTaskById(6).getStatus());


        System.out.println(manager.getSubtaskById(3).getStatus());
        //manager.getSubtaskById(3).setStatus("DONE");
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubtaskList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskById(3).getStatus());

        System.out.println(manager.getEpicById(1).getStatus());
        System.out.println("Epic status - " + manager.getEpicById(1).getStatus());

        //Меняем статус подзадач которые в эпике
        manager.getSubtaskById(3).setStatus("IN_PROGRESS");
        manager.getSubtaskById(4).setStatus("NEW");

        //Обновляем список подзадач в эпике
        epic1.setSubtask(manager.getSubtaskById(3));
        epic1.setSubtask(manager.getSubtaskById(4));


        System.out.println(manager.getEpicList());
        System.out.println("Epic status - " + manager.getEpicById(1).getStatus());



        //Выводим содержание класса менеджер
        System.out.println(manager);


    }
}
