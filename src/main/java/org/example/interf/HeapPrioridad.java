package org.example.interf;

import java.util.ArrayList;
import java.util.List;

public class HeapPrioridad<T extends Comparable<T>> {
    private List<T> heap;
    private int size;

    public HeapPrioridad() {
        this.heap = new ArrayList<>();
        this.size = 0;
    }

    // Métodos principales para manejar el heap
    public void insertar(T elemento) {
        heap.add(elemento);
        size++;
        heapifyUp(size - 1);
    }

    public T extraerMinimo() {
        if (isEmpty()) {
            return null;
        }

        T minimo = heap.get(0);
        //Movemos el ultimo elemento a la raiz
        T ultimo = heap.get(size - 1);
        heap.set(0, ultimo);
        heap.remove(size - 1);
        size--;

        // Reestablecemos la propiedad del heap
        if (!isEmpty()) {
            heapifyDown(0);
        }

        return minimo;
    }

    public T verMinimo() {
        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Método para obtener una lista de todos los elementos ordenados
    public List<T> obtenerTodosOrdenados() {
        List<T> resultado = new ArrayList<>();
        HeapPrioridad<T> heapTemporal = new HeapPrioridad<>();

        // Copiamos todos los elementos al heap temporal
        for (int i = 0; i < size; i++) {
            heapTemporal.insertar(heap.get(i));
        }

        // Extraemos elementos del heap temporal en orden
        while (!heapTemporal.isEmpty()) {
            resultado.add(heapTemporal.extraerMinimo());
        }

        return resultado;
    }

    // Método para obtener una lista de los elementos sin ordenar
    public List<T> obtenerElementos() {
        return new ArrayList<>(heap.subList(0, size));
    }

    // Eliminamos una tarea específica
    public boolean eliminar(T elemento) {
        int indice = -1;

        // Buscamos el elemento
        for (int i = 0; i < size; i++) {
            if (heap.get(i).equals(elemento)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return false; // Elemento no encontrado
        }

        // Movemos el último elemento a la posición del elemento que vamos a eliminar
        T ultimo = heap.get(size - 1);
        heap.set(indice, ultimo);
        heap.remove(size - 1);
        size--;

        if (size > 0 && indice < size) {
            // Reestablecemos la propiedad del heap
            heapifyUp(indice);
            heapifyDown(indice);
        }

        return true;
    }

    // Método para limpiar el heap
    public void limpiar() {
        heap.clear();
        size = 0;
    }

    // Métodos para mantener la propiedad del heap
    private void heapifyUp(int indice) {
        if (indice == 0) return;

        int indicePadre = (indice - 1) / 2;

        if (heap.get(indice).compareTo(heap.get(indicePadre)) < 0) {
            intercambiar(indice, indicePadre);
            heapifyUp(indicePadre);
        }
    }

    private void heapifyDown(int indice) {
        int indiceHijoIzq = 2 * indice + 1;
        int indiceHijoDer = 2 * indice + 2;
        int indiceMenor = indice;

        // Encontramos el hijo con menor valor
        if (indiceHijoIzq < size &&
                heap.get(indiceHijoIzq).compareTo(heap.get(indiceMenor)) < 0) {
            indiceMenor = indiceHijoIzq;
        }

        if (indiceHijoDer < size &&
                heap.get(indiceHijoDer).compareTo(heap.get(indiceMenor)) < 0) {
            indiceMenor = indiceHijoDer;
        }

        // Si el menor no es el nodo actual, intercambiar y continuar
        if (indiceMenor != indice) {
            intercambiar(indice, indiceMenor);
            heapifyDown(indiceMenor);
        }
    }

    private void intercambiar(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Método para imprimir el heap (en caso de ser necesario)
    public void imprimirHeap() {
        if (isEmpty()) {
            System.out.println("Heap vacío");
            return;
        }

        System.out.println("Heap de Prioridad");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + heap.get(i));
        }
    }

    //Metodo para imprimir el heap(como estructura)
    public void imprimirEstructura() {
        if (isEmpty()) {
            System.out.println("Heap vacío");
            return;
        }

        System.out.println("Estructura del Heap");
        imprimirNivel(0, "", true);
    }

    private void imprimirNivel(int indice, String prefijo, boolean esUltimo) {
        if (indice >= size) return;

        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + heap.get(indice));

        int hijoIzq = 2 * indice + 1;
        int hijoDer = 2 * indice + 2;

        if (hijoIzq < size || hijoDer < size) {
            if (hijoDer < size) {
                imprimirNivel(hijoDer, prefijo + (esUltimo ? "    " : "│   "), false);
            }
            if (hijoIzq < size) {
                imprimirNivel(hijoIzq, prefijo + (esUltimo ? "    " : "│   "), true);
            }
        }
    }
}