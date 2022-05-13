package test.logic;

import logic.FileBackedTasksManager;
import logic.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static logic.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TaskManagerTest<T extends TaskManager> {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    T taskManager;
    T fileTaskManager;

    public abstract void createManager();

    @BeforeEach
    void beforeEach() {
        createManager();
    }

    @Test
    void createTask() {
        Task task = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Epic epic = new Epic(10, "name", "description");
        Subtask subtask = new Subtask(2, "name", "description", 10, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);
        boolean t = taskManager.getTaskList().contains(task);
        boolean e = taskManager.getEpicList().contains(epic);
        boolean s = taskManager.getSubtaskList().contains(subtask);
        assertTrue(t);
        assertTrue(e);
        assertTrue(s);
    }

    @Test
    void createNullTask() {
        Task task = null;
        Subtask subtask = null;
        Epic epic = null;
        taskManager.createTask(task);
        taskManager.createSubtask(subtask);
        taskManager.createEpic(epic);
        boolean t = taskManager.getTaskList().contains(task);
        boolean e = taskManager.getEpicList().contains(epic);
        boolean s = taskManager.getSubtaskList().contains(subtask);
        assertFalse(t);
        assertFalse(e);
        assertFalse(s);
    }

    @Test
    void createTaskWithIncorrectId() {
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Task task2 = new Task(0, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Task task3 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        List<Task> listTask = new ArrayList<>();
        listTask.add(task1);

        Epic epic1 = new Epic(2, "name", "description");
        Epic epic2 = new Epic(0, "name", "description");
        Epic epic3 = new Epic(2, "name", "description");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
        List<Epic> listEpic = new ArrayList<>();
        listEpic.add(epic1);

        Subtask subtask1 = new Subtask(3, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(0, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask3 = new Subtask(3, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        List<Subtask> listSubtask = new ArrayList<>();
        listSubtask.add(subtask1);

        assertEquals(taskManager.getTaskList(), listTask);
        assertEquals(taskManager.getEpicList(), listEpic);
        assertEquals(taskManager.getSubtaskList(), listSubtask);
    }

    @Test
    void shouldReturnTaskById() {
        System.setOut(new PrintStream(output));
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);

        Epic epic1 = new Epic(2, "name", "description");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask(3, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createSubtask(subtask1);

        assertEquals(taskManager.getTaskById(1), task1);
        assertEquals(taskManager.getEpicById(2), epic1);
        assertEquals(taskManager.getSubtaskById(3), subtask1);
        assertNotEquals(taskManager.getTaskById(4), task1);
        assertNotEquals(taskManager.getEpicById(5), epic1);
        assertNotEquals(taskManager.getSubtaskById(6), subtask1);
        assertEquals("Такой id не существует\r\nЭпика с таким id нет\r\nПодзадачи с таким id нет.\r\n",
                output.toString());
        System.setOut(null);
    }

    @Test
    void shouldRemoveTaskById() {
        System.setOut(new PrintStream(output));
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        taskManager.removeTaskById(1);
        assertEquals("Задача удалена\r\n", output.toString());

        Epic epic1 = new Epic(2, "name", "description");
        taskManager.createEpic(epic1);
        taskManager.removeEpicById(2);
        assertEquals("Задача удалена\r\n", output.toString());

        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(3, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createSubtask(subtask1);
        taskManager.removeSubtaskById(3);
        assertEquals("Задача удалена\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldNotRemoveTaskByIncorrectId() {
        System.setOut(new PrintStream(output));
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        taskManager.removeTaskById(2);
        assertEquals("Задачи с таким id нет. Задача не удалена\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldNotRemoveEpicByIncorrectId() {
        System.setOut(new PrintStream(output));
        Epic epic1 = new Epic(2, "name", "description");
        taskManager.createEpic(epic1);
        taskManager.removeEpicById(3);
        assertEquals("Эпика с таким id не существует. Эпик не удален\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldNotRemoveSubtaskByIncorrectId() {
        System.setOut(new PrintStream(output));
        Epic epic1 = new Epic(2, "name", "description");
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(3, "name", "description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createSubtask(subtask1);
        taskManager.removeSubtaskById(4);
        assertEquals("Подзадачи с таким id нет. Задача не удалена\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldRemoveAllTasks() {
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Task task2 = new Task(2, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Task task3 = new Task(3, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.removeAllTasks();
        assertTrue(taskManager.getTaskList().isEmpty());
    }

    @Test
    void shouldRemoveAllEpics() {
        Epic epic1 = new Epic(1, "name", "description");
        Epic epic2 = new Epic(2, "name", "description");
        Epic epic3 = new Epic(3, "name", "description");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
        taskManager.removeAllEpic();
        assertTrue(taskManager.getEpicList().isEmpty());
    }

    @Test
    void shouldRemoveAllSubtasks() {
        Epic epic1 = new Epic(1, "name", "description");
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(2, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(3, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask3 = new Subtask(4, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        taskManager.removeAllSubtasks();
        assertTrue(taskManager.getSubtaskList().isEmpty());
    }

    @Test
    void shouldUpdateTask() {
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        Task task2 = new Task(1, "name2", "description2", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.updateTask(task2);
        assertEquals(taskManager.getTaskById(1).getDescription(), "description2");
    }

    @Test
    void shouldNotUpdateTaskWhenNull() {
        System.setOut(new PrintStream(output));
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createTask(task1);
        Task task2 = null;
        taskManager.updateTask(task2);
        assertEquals("Задача не существует\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldUpdateEpic() {
        Epic epic1 = new Epic(1, "name", "description");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic(1, "name", "description2");
        taskManager.updateEpic(epic2);
        assertEquals(taskManager.getEpicById(1).getDescription(), "description2");
    }

    @Test
    void shouldNotUpdateEpcWhenNull() {
        System.setOut(new PrintStream(output));
        Epic epic1 = new Epic(1, "name", "description");
        taskManager.createEpic(epic1);
        Epic epic2 = null;
        taskManager.updateEpic(epic2);
        assertEquals("Такого эпика не существует\r\n", output.toString());
        System.setOut(null);
    }

    @Test
    void shouldUpdateSubtask() {
        Epic epic1 = new Epic(1, "name", "description");
        Subtask subtask1 = new Subtask(2, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask(2, "name", "description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.updateSubtask(subtask2);
        assertEquals(taskManager.getSubtaskById(2).getDescription(), "description2");
    }

    @Test
    void shouldNotUpdateSubtaskWhenNull() {
        System.setOut(new PrintStream(output));
        Epic epic1 = new Epic(1, "name", "description");
        Subtask subtask1 = new Subtask(2, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = null;
        taskManager.updateSubtask(subtask2);
        assertEquals("Такой подзадачи не существует\r\n", output.toString());
    }

    @Test
    void shouldNotUpdateSubtaskWhenNoEpic() {
        System.setOut(new PrintStream(output));
        Epic epic1 = new Epic(1, "name", "description");
        Subtask subtask1 = new Subtask(2, "name", "description", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask(2, "name", "description2", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        taskManager.updateSubtask(subtask2);
        assertEquals("Эпика для подзадачи не существует\r\n", output.toString());
    }

    @Test
    void isTaskNotCrossed() {
        Task task1 = new Task(1, "name", "description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(2, "name", "description", Duration.ofDays(3),
                LocalDateTime.of(2021, 5, 8, 0, 0));
        Task task3 = new Task(3, "name", "description", Duration.ofDays(2),
                LocalDateTime.of(2021, 5, 3, 0, 0));
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        assertFalse(taskManager.isTaskNotCrossed(task3));
    }

    @Test
    void checkHistoryAndTasksWritingReadingInFile() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getTaskById(1);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.getEpicById(2);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(fb.getTaskList().get(0));
        tasksList.add(fb.getEpicList().get(0));
        tasksList.add(fb.getSubtaskList().get(0));

        assertEquals(historyList.get(0).getDescription(), "Task description");
        assertEquals(historyList.get(1).getDescription(), "Subtask description");
        assertEquals(historyList.get(2).getDescription(), "Epic description");
        assertEquals(tasksList.get(0).getDescription(), "Task description");
        assertEquals(tasksList.get(2).getDescription(), "Subtask description");
        assertEquals(tasksList.get(1).getDescription(), "Epic description");
    }

    @Test
    void checkRepeatInHistory() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getTaskById(1);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.getEpicById(2);
        fileTaskManager.getTaskById(1);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(2).getDescription(), "Task description");
        assertEquals(historyList.get(0).getDescription(), "Subtask description");
        assertEquals(historyList.get(1).getDescription(), "Epic description");
    }

    @Test
    void checkEmptyHistory() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertTrue(historyList.isEmpty());
    }

    @Test
    void checkRemoveFirstFromHistory() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getTaskById(1);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.getEpicById(2);
        fileTaskManager.getTaskById(1);
        fileTaskManager.removeSubtaskById(3);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(1).getDescription(), "Task description");
        assertEquals(historyList.get(0).getDescription(), "Epic description");
    }

    @Test
    void checkRemoveEpicFromHistory() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.getEpicById(2);
        fileTaskManager.getTaskById(1);
        fileTaskManager.removeEpicById(2);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(0).getDescription(), "Task description");
    }

    @Test
    void checkRemoveMiddleFromHistory() {
        System.setOut(new PrintStream(output));
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.getTaskById(1);
        fileTaskManager.getEpicById(2);
        fileTaskManager.removeTaskById(1);
        assertEquals("Задача удалена\r\n", output.toString());

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(0).getDescription(), "Subtask description");
        assertEquals(historyList.get(1).getDescription(), "Epic description");
        System.setOut(null);
    }

    @Test
    void checkRemoveFromEnfOfHistory() {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        fileTaskManager.createTask(task1);
        fileTaskManager.createEpic(epic1);
        fileTaskManager.createSubtask(subtask1);
        fileTaskManager.getTaskById(1);
        fileTaskManager.getEpicById(2);
        fileTaskManager.getSubtaskById(3);
        fileTaskManager.removeSubtaskById(3);

        File file = new File("test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(0).getDescription(), "Task description");
        assertEquals(historyList.get(1).getDescription(), "Epic description");
    }
}
