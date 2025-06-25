package org.example.interf;

import java.util.ArrayList;
import java.util.List;

public class Cola<T> {
    private Nodo<T> front;
    private Nodo<T> rear;

    public Cola() {
        this.front = null;
        this.rear = null;
    }

    public Nodo<T> getHead() {
        return front;
    }

    public Nodo<T> getTail() {
        return rear;
    }

    public void enqueue(T data) {
        Nodo<T> newNode = new Nodo<>(data);
        if (rear == null) {
            rear = newNode;
            front = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
    }

    public T dequeue() {
        if (front == null) return null;
        T dato = front.getData();
        front = front.getNext();
        if (front == null) rear = null;
        return dato;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public T peek() {
        if (front == null) {
            return null;
        } else {
            return this.front.getData();
        }
    }

    public List<T> toList() {
        List<T> lista = new ArrayList<>();
        Nodo<T> actual = front;
        while (actual != null) {
            lista.add(actual.getData());
            actual = actual.getNext();
        }
        return lista;
    }
}
