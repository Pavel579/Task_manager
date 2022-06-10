package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import logic.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utils.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    static TaskManager httpManager = Managers.getHttpManager();
    static HttpServer httpServer;

    public static void createServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void stopServer() {
        httpServer.stop(1);
    }

    private static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();

            String response = "";
            boolean isParamsInLink = false;
            int taskId = 0;
            int code = 200;

            // извлекаем path из запроса
            URI requestURI = httpExchange.getRequestURI();

            String params = requestURI.getQuery();
            System.out.println(params);
            String path = requestURI.getPath();
            System.out.println(path);

            if (params != null) {
                HashMap<String, String> paramsMap = new HashMap<>();
                String[] splitParams = params.split("&");
                for (String splitParam : splitParams) {
                    String[] param = splitParam.split("=");
                    paramsMap.put(param[0], param[1]);
                }
                taskId = Integer.parseInt(paramsMap.get("id"));
                isParamsInLink = true;
            }
            String[] splitPath = path.split("/");

            Gson gson = new Gson();

            switch (method) {
                case "GET":
                    if (splitPath.length == 2) {
                        response = gson.toJson(httpManager.getPrioritizedTasks());
                    } else {
                        switch (splitPath[2]) {
                            case "task":
                                if (isParamsInLink) {
                                    if (httpManager.getTaskById(taskId) != null) {
                                        response = gson.toJson(httpManager.getTaskById(taskId));
                                    } else {
                                        code = 400;
                                        response = "Задачи с таким id нет";
                                    }
                                } else {
                                    response = gson.toJson(httpManager.getTaskList());
                                }
                                break;
                            case "subtask":
                                if (splitPath.length == 3) {
                                    if (isParamsInLink) {
                                        if (httpManager.getSubtaskById(taskId) != null) {
                                            response = gson.toJson(httpManager.getSubtaskById(taskId));
                                        } else {
                                            code = 400;
                                            response = "Подзадачи с таким id нет";
                                        }
                                    } else {
                                        response = gson.toJson(httpManager.getSubtaskList());
                                    }
                                } else if (splitPath.length == 4 && splitPath[3].equals("epic") && isParamsInLink) {
                                    List<Task> subtaskListInEpic = httpManager.getEpicSubtasks(taskId);
                                    response = gson.toJson(subtaskListInEpic);
                                } else {
                                    response = "Запрос не верный";
                                }
                                break;
                            case "epic":
                                if (isParamsInLink) {
                                    if (httpManager.getEpicById(taskId) != null) {
                                        response = gson.toJson(httpManager.getEpicById(taskId));
                                    } else {
                                        code = 400;
                                        response = "Эпика с таким id нет";
                                    }
                                } else {
                                    response = gson.toJson(httpManager.getEpicList());
                                }
                                break;
                            case "history":
                                List<Task> history = httpManager.getHistoryInMemory().getHistory();
                                response = gson.toJson(history);
                                break;
                            default:
                                code = 400;
                                response = "Такого типа задач нет";
                                break;
                        }
                    }
                    break;
                case "POST":
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    if (splitPath.length == 3) {
                        switch (splitPath[2]) {
                            case "task":
                                Task task = gson.fromJson(body, Task.class);
                                if (httpManager.getTaskList().contains(task)) {
                                    httpManager.updateTask(task);
                                    response = "Задача обновлена";
                                } else {
                                    httpManager.createTask(task);
                                    response = "Задача создана";
                                }
                                break;
                            case "subtask":
                                Subtask subtask = gson.fromJson(body, Subtask.class);
                                if (httpManager.getSubtaskList().contains(subtask)) {
                                    httpManager.updateSubtask(subtask);
                                    response = "Подзадача обновлена";
                                } else {
                                    httpManager.createSubtask(subtask);
                                    response = "Подзадача создана";
                                }
                                break;
                            case "epic":
                                Epic epic = gson.fromJson(body, Epic.class);
                                if (httpManager.getEpicList().contains(epic)) {
                                    httpManager.updateEpic(epic);
                                    response = "Эпик обновлен";
                                } else {
                                    httpManager.createEpic(epic);
                                    response = "Эпик создан";
                                }
                                break;
                            default:
                                code = 400;
                                response = "Такого типа задач нет";
                                break;
                        }
                    }
                    break;
                case "DELETE":
                    if (splitPath.length == 3 && !isParamsInLink) {
                        switch (splitPath[2]) {
                            case "task":
                                httpManager.removeAllTasks();
                                break;
                            case "subtask":
                                httpManager.removeAllSubtasks();
                                break;
                            case "epic":
                                httpManager.removeAllEpic();
                                break;
                            default:
                                code = 400;
                                response = "Такого типа задач нет";
                                break;
                        }
                    } else if (splitPath.length == 3) {
                        switch (splitPath[2]) {
                            case "task":
                                httpManager.removeTaskById(taskId);
                                break;
                            case "subtask":
                                httpManager.removeSubtaskById(taskId);
                                break;
                            case "epic":
                                httpManager.removeEpicById(taskId);
                                break;
                            default:
                                code = 400;
                                response = "Такого типа задач нет";
                                break;
                        }
                    }
                    break;
                default:
                    code = 400;
                    response = "Некорректный метод!";
            }
            httpExchange.sendResponseHeaders(code, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
