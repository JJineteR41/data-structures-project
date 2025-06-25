package org.example.interf;

public class Nodo<T> {
    protected T data;
    public Nodo<T> next;

    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }

    public void setNext(Nodo<T> next) {
        this.next = next;
    }

    public Nodo<T> getNext() {
        return next;
    }

    public T getData() {
        return data;
    }
}
