public class Cola<T> {
    Nodo<T> front;
    Nodo<T> rear;

    public Cola(){
        this.front=null;
        this.rear=null;
    }

    public Nodo<T> getHead() {
        return front;
    }

    public Nodo<T> getTail() {
        return rear;
    }


    void enqueue(T data){
        Nodo<T> newNode= new Nodo<>(data);
        if (rear==null){
            rear=newNode;
            front=newNode;
        }
        else{
            rear.setNext(newNode);
            rear=newNode;
        }
    }

    void dequeue(){

        if (front!=null) front=front.getNext();
        if (front==null) rear=null;
    }

    public boolean isEmpty(){
        return front==null;
    }

    T peek(){
        if (front==null) {
            return null;
        }
        else {
            return this.front.getData();
        }
    }
}
