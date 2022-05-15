package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static logic.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest {
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

        File file = new File("resources/test.csv");
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
}
