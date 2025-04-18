package csc402.week3;

public class CustomStack<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;
    
    public CustomStack() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    
    // Push element onto the stack
    public void push(T element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = element;
    }
    
    // Pop element from the stack
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        T element = (T) elements[--size];
        elements[size] = null; // Help garbage collection
        return element;
    }
    
    // Peek at the top element without removing it
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        return (T) elements[size - 1];
    }
    
    // Double the capacity when array is full
    private void ensureCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        
        // Copy old elements to new array
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        
        elements = newElements;
    }
    
    // Get size of the stack
    public int size() {
        return size;
    }
    
    // Check if stack is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
