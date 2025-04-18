package csc402.week3;

public class CustomArrayList<T>  {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;
    
    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    
    // Add element to the end of the list
    public void add(T element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        
        elements[size++] = element;
    }
    
    // Get element at specified index
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }
    
    // Remove element at specified index
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        T removedElement = (T) elements[index];
        
        // Shift elements to remove the gap
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        
        elements[--size] = null; // Help garbage collection
        return removedElement;
    }
    
    // Double the capacity when array is full
    private void ensureCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        
        // Copy old elements to new array
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        
        this.elements = newElements;
    }
    
    // Get size of the list
    public int size() {
        return size;
    }
    
    // Check if list is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
