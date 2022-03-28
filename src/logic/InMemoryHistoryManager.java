package logic;

import data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    //Метод добавляет элемент в конец "списка" (создает ее Node)
    private void linkLast(Task task) {
        Node<Task> lastNode = last;
        Node<Task> newNode = new Node<>(lastNode, task, null);
        historyMap.put(task.getId(), newNode);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.setNext(newNode);
        }
    }

    //Метод собирает все задачи в ArrayList и возвращает его
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Node<Task> node = first; node != null; node = node.getNext()) {
            tasks.add(node.getItem());
        }
        return tasks;
    }

    //Метод вырезает Node
    private void removeNode(Node<Task> node) {
        Node<Task> next = node.getNext();
        Node<Task> prev = node.getPrev();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        node.setItem(null);
    }
}