package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import logic.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utils.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServerTest {
    private final String URL = "http://localhost:8080";
    TaskManager httpTaskManager;
    Gson gson;
    KVServer kvServer = new KVServer();

    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer.start();
        HttpTaskServer.createServer();
        httpTaskManager = Managers.getHttpManager();
        gson = new Gson();
        httpTaskManager.removeAllTasks();
        httpTaskManager.removeAllSubtasks();
        httpTaskManager.removeAllEpic();
    }

    @AfterEach
    void AfterEach() {
        kvServer.stop();
        HttpTaskServer.stopServer();
    }

    @Test
    void checkEndpointGETTasks() throws IOException, InterruptedException {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1999, 6, 1, 0, 0, 0, 0));
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        URI uri = URI.create(URL + "/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Task.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Task.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Task description");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description Task 2");
    }

    @Test
    void checkEndpointGETTaskById() throws IOException, InterruptedException {
        Task task1 = new Task(1, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1999, 6, 1, 0, 0, 0, 0));
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        URI uri = URI.create(URL + "/tasks/task?id=7");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Task receivedTask = gson.fromJson(response.body(), Task.class);
        Assertions.assertEquals(receivedTask.getDescription(), "Description Task 2");
    }

    @Test
    void checkEndpointGETSubtasks() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        URI uri = URI.create(URL + "/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Subtask.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Subtask.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Description1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description2");
    }

    @Test
    void checkEndpointGETSubtaskById() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        URI uri = URI.create(URL + "/tasks/subtask?id=4");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Task receivedTask = gson.fromJson(response.body(), Subtask.class);
        Assertions.assertEquals(receivedTask.getDescription(), "Description2");
    }

    @Test
    void checkEndpointGETEpics() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description Epic 2");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Epic.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Epic.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Description Epic 1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description Epic 2");
    }

    @Test
    void checkEndpointGETEpicById() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description Epic 2");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        URI uri = URI.create(URL + "/tasks/epic?id=2");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Task receivedTask = gson.fromJson(response.body(), Epic.class);
        Assertions.assertEquals(receivedTask.getDescription(), "Description Epic 2");
        httpTaskManager.getHistoryInMemory().remove(2);
    }

    @Test
    void checkEndpointGETEpicSubtasks() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        URI uri = URI.create(URL + "/tasks/subtask/epic?id=1");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Subtask.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Subtask.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Description1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description2");
    }

    @Test
    void checkEndpointGETTasksHistory() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1999, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.getEpicById(1);
        httpTaskManager.getSubtaskById(4);
        httpTaskManager.getSubtaskById(3);
        URI uri = URI.create(URL + "/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Epic.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Subtask.class);
        Task receivedTask3 = gson.fromJson(jsonArray.get(2), Subtask.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Description Epic 1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description2");
        Assertions.assertEquals(receivedTask3.getDescription(), "Description1");
    }

    @Test
    void checkEndpointGETPrioritizedTasks() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1993, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Task task1 = new Task(6, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1995, 6, 1, 0, 0, 0, 0));
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);

        URI uri = URI.create(URL + "/tasks");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask1 = gson.fromJson(jsonArray.get(0), Subtask.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Subtask.class);
        Task receivedTask3 = gson.fromJson(jsonArray.get(2), Task.class);
        Task receivedTask4 = gson.fromJson(jsonArray.get(3), Task.class);
        Assertions.assertEquals(receivedTask1.getDescription(), "Description1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description2");
        Assertions.assertEquals(receivedTask3.getDescription(), "Description Task 2");
        Assertions.assertEquals(receivedTask4.getDescription(), "Task description");
    }

    @Test
    void checkEndpointDeleteTasks() throws IOException, InterruptedException {
        Task task1 = new Task(6, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1995, 6, 1, 0, 0, 0, 0));
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);

        URI uri = URI.create(URL + "/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/task");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        Assertions.assertEquals(response2.body(), "[]");
    }

    @Test
    void checkEndpointDeleteSubtasks() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1993, 2, 1, 0, 0, 0, 0));
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);

        URI uri = URI.create(URL + "/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/subtask");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        Assertions.assertEquals(response2.body(), "[]");
    }

    @Test
    void checkEndpointDeleteEpics() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description Epic 2");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);

        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/epic");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        Assertions.assertEquals(response2.body(), "[]");
    }

    @Test
    void checkEndpointDeleteTaskById() throws IOException, InterruptedException {
        Task task1 = new Task(6, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(7, "Task 2", "Description Task 2", Duration.ofDays(5),
                LocalDateTime.of(1995, 6, 1, 0, 0, 0, 0));
        Task task3 = new Task(8, "Task 2", "Description Task 3", Duration.ofDays(5),
                LocalDateTime.of(1996, 6, 1, 0, 0, 0, 0));
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);

        URI uri = URI.create(URL + "/tasks/task?id=7");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/task");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask = gson.fromJson(jsonArray.get(0), Task.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Task.class);

        Assertions.assertEquals(receivedTask.getDescription(), "Task description");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description Task 3");
        Assertions.assertEquals(jsonArray.size(), 2);
    }

    @Test
    void checkEndpointDeleteSubtaskById() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(4, "Sub2", "Description2", 1, Duration.ofDays(5),
                LocalDateTime.of(1993, 2, 1, 0, 0, 0, 0));
        Subtask subtask3 = new Subtask(5, "Sub2", "Description3", 1, Duration.ofDays(5),
                LocalDateTime.of(1994, 2, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);

        URI uri = URI.create(URL + "/tasks/subtask?id=4");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/subtask");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask = gson.fromJson(jsonArray.get(0), Subtask.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Subtask.class);

        Assertions.assertEquals(receivedTask.getDescription(), "Description1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description3");
        Assertions.assertEquals(jsonArray.size(), 2);
    }

    @Test
    void checkEndpointDeleteEpicById() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description Epic 2");
        Epic epic3 = new Epic(3, "Epic 2", "Description Epic 3");
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);

        URI uri = URI.create(URL + "/tasks/epic?id=2");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();

        URI uri2 = URI.create(URL + "/tasks/epic");
        HttpRequest request2 = HttpRequest.newBuilder().GET().uri(uri2).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        HttpResponse<String> response2 = client.send(request2, handler);

        JsonElement jsonElement = JsonParser.parseString(response2.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Task receivedTask = gson.fromJson(jsonArray.get(0), Epic.class);
        Task receivedTask2 = gson.fromJson(jsonArray.get(1), Epic.class);

        Assertions.assertEquals(receivedTask.getDescription(), "Description Epic 1");
        Assertions.assertEquals(receivedTask2.getDescription(), "Description Epic 3");
        Assertions.assertEquals(jsonArray.size(), 2);
    }

    @Test
    void checkEndpointPostCreateTask() throws IOException, InterruptedException {
        Task task1 = new Task(6, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        String task = gson.toJson(task1);

        URI uri = URI.create(URL + "/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(task)).uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);

        Assertions.assertEquals(httpTaskManager.getTaskById(6).getDescription(), "Task description");
    }

    @Test
    void checkEndpointPostUpdateTask() throws IOException, InterruptedException {
        Task task1 = new Task(6, "name", "Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        Task task2 = new Task(6, "name", "Updated Task description", Duration.ofDays(5),
                LocalDateTime.of(2021, 5, 1, 0, 0));
        String task = gson.toJson(task1);
        String updatedTask = gson.toJson(task2);

        URI uri = URI.create(URL + "/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(task)).uri(uri).build();

        HttpRequest request2 = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(updatedTask))
                .uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        client.send(request2, handler);

        Assertions.assertEquals(httpTaskManager.getTaskById(6).getDescription(), "Updated Task description");
    }

    @Test
    void checkEndpointPostCreateSubtask() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        String subtask = gson.toJson(subtask1);
        String epic = gson.toJson(epic1);

        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epic))
                .uri(uri)
                .build();

        URI uri2 = URI.create(URL + "/tasks/subtask");
        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(subtask))
                .uri(uri2)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        client.send(request2, handler);

        Assertions.assertEquals(httpTaskManager.getSubtaskById(3).getDescription(), "Description1");
    }

    @Test
    void checkEndpointPostUpdateSubtask() throws IOException, InterruptedException {
        Subtask subtask1 = new Subtask(3, "Sub1", "Description1", 1, Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Subtask subtask2 = new Subtask(3, "Sub1", "Updated Description1", 1,
                Duration.ofDays(5),
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0));
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        String subtask = gson.toJson(subtask1);
        String updatedSubtask = gson.toJson(subtask2);
        String epic = gson.toJson(epic1);

        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epic))
                .uri(uri)
                .build();

        URI uri2 = URI.create(URL + "/tasks/subtask");
        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(subtask))
                .uri(uri2)
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(updatedSubtask))
                .uri(uri2)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        client.send(request2, handler);
        client.send(request3, handler);

        Assertions.assertEquals(httpTaskManager.getSubtaskById(3).getDescription(), "Updated Description1");
    }

    @Test
    void checkEndpointPostCreateEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        String epic = gson.toJson(epic1);

        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(epic)).uri(uri).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);

        Assertions.assertEquals(httpTaskManager.getEpicById(1).getDescription(), "Description Epic 1");
    }

    @Test
    void checkEndpointPostUpdateEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, "Epic 1", "Description Epic 1");
        Epic epic2 = new Epic(1, "Epic 1", "Updated Description Epic 1");
        String epic = gson.toJson(epic1);
        String updatedEpic = gson.toJson(epic2);

        URI uri = URI.create(URL + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epic))
                .uri(uri)
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(updatedEpic))
                .uri(uri)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
        client.send(request2, handler);

        Assertions.assertEquals(httpTaskManager.getEpicById(1).getDescription(), "Updated Description Epic 1");
    }
}
