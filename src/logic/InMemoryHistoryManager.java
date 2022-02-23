package logic;

import data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static Node<Task> historyNode;
    private final MyLinkedList<Task> history = new MyLinkedList<>();
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        history.linkLast(task);
        historyMap.put(task.getId(), historyNode);

    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            history.removeNode(historyMap.get(id));
            historyMap.remove(id);
        }

    }

    @Override
    public List<Task> getHistory() {
        return history.getTask();
    }

    static class MyLinkedList<E> {
        int size = 0;
        Node<Task> first;
        Node<Task> last;

        public void linkLast(Task e) {
            final Node<Task> l = last;
            final Node<Task> newNode = new Node<>(l, e, null);
            historyNode = newNode;
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;
        }

        public List<Task> getTask() {
            List<Task> arrayList = new ArrayList<>();
            for (Node<Task> x = first; x != null; x = x.next) {
                arrayList.add(x.item);
            }
            return arrayList;
        }

        public void removeNode(Node<Task> node) {
            final Node<Task> next = node.next;
            final Node<Task> prev = node.prev;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                node.prev = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }

            node.item = null;
            size--;
        }

    }

}
