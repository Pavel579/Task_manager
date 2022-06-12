package logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPTaskManagerTest extends TaskManagerTest {
    KVServer kvServer = new KVServer();

    public HTTPTaskManagerTest() throws IOException {
    }

    @Override
    public void createManager() throws IOException, InterruptedException {
        httpTaskManager = new HTTPTaskManager("http://localhost:8078", false);
    }

    @BeforeEach
    void beforeEach() throws IOException, InterruptedException {
        kvServer.start();
        createManager();
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
    }

    @Test
    void checkHistoryAndTasksWritingReadingInServer() throws IOException, InterruptedException {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Epic epic1 = new Epic(2, "name", "Epic description");
        Subtask subtask1 = new Subtask(3, "name", "Subtask description", 2, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        httpTaskManager.createTask(task1);
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.getTaskById(1);
        httpTaskManager.getSubtaskById(3);
        httpTaskManager.getEpicById(2);

        HTTPTaskManager taskManagerFromServer = new HTTPTaskManager("http://localhost:8078", true);
        List<Task> historyList = new ArrayList<>(taskManagerFromServer.getHistoryInMemory().getHistory());
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(taskManagerFromServer.getTaskList().get(0));
        tasksList.add(taskManagerFromServer.getEpicList().get(0));
        tasksList.add(taskManagerFromServer.getSubtaskList().get(0));

        assertEquals(historyList.get(0).getDescription(), "Task description");
        assertEquals(historyList.get(1).getDescription(), "Subtask description");
        assertEquals(historyList.get(2).getDescription(), "Epic description");
        assertEquals(tasksList.get(0).getDescription(), "Task description");
        assertEquals(tasksList.get(2).getDescription(), "Subtask description");
        assertEquals(tasksList.get(1).getDescription(), "Epic description");
    }


}
