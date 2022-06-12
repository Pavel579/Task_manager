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
    static Gson gson = new Gson();

    public static void createServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/tasks/task", new TasksHandler()::handleTasks);
        httpServer.createContext("/tasks/subtask", new TasksHandler()::handleSubtasks);
        httpServer.createContext("/tasks/epic", new TasksHandler()::handleEpics);
        httpServer.createContext("/tasks/subtask/epic", new TasksHandler()::handleEpicSubtasks);
        httpServer.createContext("/tasks/history", new TasksHandler()::handleHistory);
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void stopServer() {
        httpServer.stop(1);
    }

    private static class TasksHandler implements HttpHandler {
        String method = "";
        String response = "";
        boolean isParamsInLink = false;
        int taskId = 0;
        int code = 200;
        URI requestURI;
        String params;
        String path;

        private void defineParamsInLink() {
            if (params != null) {
                HashMap<String, String> paramsMap = new HashMap<>();
                String[] splitParams = params.split("&");
                for (String splitParam : splitParams) {
                    String[] param = splitParam.split("=");
                    paramsMap.put(param[0], param[1]);
                }
                taskId = Integer.parseInt(paramsMap.get("id"));
                isParamsInLink = true;
            } else {
                isParamsInLink = false;
                taskId = 0;
            }
        }

        private void handleTasks(HttpExchange httpExchange) {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                defineParamsInLink();

                switch (method) {
                    case "GET":
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
                    case "POST":
                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        if (body.isEmpty()) {
                            code = 400;
                            response = "Отсутствует body";
                            break;
                        }
                        Task task = gson.fromJson(body, Task.class);
                        if (httpManager.getTaskList().contains(task)) {
                            httpManager.updateTask(task);
                            response = "Задача обновлена";
                        } else {
                            httpManager.createTask(task);
                            response = "Задача создана";
                        }
                        break;
                    case "DELETE":
                        if (isParamsInLink) {
                            httpManager.removeTaskById(taskId);
                        } else {
                            httpManager.removeAllTasks();
                        }
                        break;
                    default:
                        code = 405;
                        response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }

        private void handleSubtasks(HttpExchange httpExchange) {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                defineParamsInLink();

                switch (method) {
                    case "GET":
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
                        break;
                    case "POST":
                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        if (body.isEmpty()) {
                            code = 400;
                            response = "Отсутствует body";
                            break;
                        }
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        if (httpManager.getSubtaskList().contains(subtask)) {
                            httpManager.updateSubtask(subtask);
                            response = "Подзадача обновлена";
                        } else {
                            httpManager.createSubtask(subtask);
                            response = "Подзадача создана";
                        }
                        break;
                    case "DELETE":
                        if (isParamsInLink) {
                            httpManager.removeSubtaskById(taskId);
                        } else {
                            httpManager.removeAllSubtasks();
                        }
                        break;
                    default:
                        code = 405;
                        response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }

        private void handleEpics(HttpExchange httpExchange) {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                defineParamsInLink();

                switch (method) {
                    case "GET":
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
                    case "POST":
                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        if (body.isEmpty()) {
                            code = 400;
                            response = "Отсутствует body";
                            break;
                        }
                        Epic epic = gson.fromJson(body, Epic.class);
                        if (httpManager.getEpicList().contains(epic)) {
                            httpManager.updateEpic(epic);
                            response = "Эпик обновлен";
                        } else {
                            httpManager.createEpic(epic);
                            response = "Эпик создан";
                        }
                        break;
                    case "DELETE":
                        if (isParamsInLink) {
                            httpManager.removeEpicById(taskId);
                        } else {
                            httpManager.removeAllEpic();
                        }
                        break;
                    default:
                        code = 405;
                        response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }

        private void handleEpicSubtasks(HttpExchange httpExchange) {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                defineParamsInLink();

                if ("GET".equals(method)) {
                    if (isParamsInLink) {
                        List<Task> subtaskListInEpic = httpManager.getEpicSubtasks(taskId);
                        response = gson.toJson(subtaskListInEpic);
                    } else {
                        response = "Запрос не верный";
                    }
                } else {
                    code = 405;
                    response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }

        private void handleHistory(HttpExchange httpExchange) {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                if ("GET".equals(method)) {
                    List<Task> history = httpManager.getHistoryInMemory().getHistory();
                    response = gson.toJson(history);
                } else {
                    code = 405;
                    response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                method = httpExchange.getRequestMethod();
                requestURI = httpExchange.getRequestURI();
                params = requestURI.getQuery();
                path = requestURI.getPath();

                if ("GET".equals(method)) {
                    response = gson.toJson(httpManager.getPrioritizedTasks());
                } else {
                    code = 405;
                    response = "Некорректный метод!";
                }

                httpExchange.sendResponseHeaders(code, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }
    }
}
