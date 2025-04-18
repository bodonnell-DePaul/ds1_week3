package csc402.week3;

public class CustomQueue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int front; // Index of the front element
    private int rear;  // Index where next element will be inserted
    private int size;
    
    public CustomQueue() {
        elements = new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = 0;
        size = 0;
    }
    
    // Add element to the queue (enqueue)
    public void enqueue(T element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        
        elements[rear] = element;
        rear = (rear + 1) % elements.length; // Circular array
        size++;
    }
    
    // Remove and return the front element (dequeue)
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        T element = (T) elements[front];
        elements[front] = null; // Help garbage collection
        front = (front + 1) % elements.length; // Circular array
        size--;
        return element;
    }
    
    // Peek at the front element without removing it
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        return (T) elements[front];
    }
    
    // Increase capacity when array is full
    private void ensureCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        
        // Copy elements from potentially wrapped array to new array
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % elements.length];
        }
        
        elements = newElements;
        front = 0;
        rear = size;
    }
    
    // Get size of the queue
    public int size() {
        return size;
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }
}