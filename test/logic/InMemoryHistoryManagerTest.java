package logic;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryHistoryManagerTest extends TaskManagerTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Override
    public void createManager() {
        File file = new File("resources/test.csv");
        fileTaskManager = new FileBackedTasksManager(file);
    }

    @BeforeEach
    void beforeEach() {
        createManager();
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

        File file = new File("resources/test.csv");
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

        File file = new File("resources/test.csv");
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

        File file = new File("resources/test.csv");
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

        File file = new File("resources/test.csv");
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

        File file = new File("resources/test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(0).getDescription(), "Subtask description");
        assertEquals(historyList.get(1).getDescription(), "Epic description");
        System.setOut(null);
    }

    @Test
    void checkRemoveFromEndOfHistory() {
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

        File file = new File("resources/test.csv");
        FileBackedTasksManager fb = loadFromFile(file);
        List<Task> historyList = new ArrayList<>(fb.getHistoryInMemory().getHistory());

        assertEquals(historyList.get(0).getDescription(), "Task description");
        assertEquals(historyList.get(1).getDescription(), "Epic description");
    }
}
