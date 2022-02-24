package logic;

public class Node<Task> {
    private Task item;
    private Node<Task> next;
    private Node<Task> prev;

    Node(Node<Task> prev, Task element, Node<Task> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }

    public Task getItem() {
        return item;
    }

    public void setItem(Task item) {
        this.item = item;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}

