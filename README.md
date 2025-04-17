DS1 - Week3


# Building Custom Data Structures in Java

Creating your own implementations of fundamental data structures is an excellent way to understand how they work internally. Here's a comprehensive guide for implementing LinkedList, ArrayList, Stack, and Queue without using Java's built-in collection classes:

## 1. LinkedList Implementation

A LinkedList consists of nodes where each node contains data and a reference to the next node.
### Built-in LinkedList
- **Type**: List, Deque
- **Implementation**: Doubly linked list
- **Order**: Maintains the order of insertion
- **Access**: Elements are accessed by iterating from the beginning or end, which takes linear time `O(n)`
- **Insertion/Deletion**: Inserting or deleting elements is efficient, especially at the beginning or end, taking constant time `O(1)`
- **Usage**: Suitable for scenarios where frequent insertions/deletions are needed and access by index is infrequent

Example:
```java
LinkedList<String> list = new LinkedList<>();
list.add("apple");
list.add("banana");
list.add("cherry");
System.out.println(list.get(1)); // Output: banana
```
---

### Custom Linked List

### Node Class
````java
public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> prev; // Added previous node reference
    
    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null; // Initialize prev reference to null
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public Node<T> getNext() {
        return next;
    }
    
    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    public Node<T> getPrev() {
        return prev;
    }
    
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
````

### LinkedList Implementation
````java
public class CustomDoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail; // Added tail reference for efficient operations at the end
    private int size;
    
    public CustomDoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    // Add element to the end of the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail); // Set new node's prev to current tail
            tail.setNext(newNode); // Set current tail's next to new node
            tail = newNode;        // Update tail to new node
        }
        size++;
    }
    
    // Add element at the beginning of the list
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head); // Set new node's next to current head
            head.setPrev(newNode); // Set current head's prev to new node
            head = newNode;        // Update head to new node
        }
        size++;
    }
    
    // Add element at specified index
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        if (index == size) {
            add(data);
            return;
        }
        
        // Find the node at position index
        Node<T> current = getNodeAt(index);
        
        // Create new node and update references
        Node<T> newNode = new Node<>(data);
        newNode.setNext(current);
        newNode.setPrev(current.getPrev());
        current.getPrev().setNext(newNode);
        current.setPrev(newNode);
        
        size++;
    }
    
    // Get element at specified index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        return getNodeAt(index).getData();
    }
    
    // Helper method to get node at specified index
    private Node<T> getNodeAt(int index) {
        Node<T> current;
        
        // Optimize traversal by starting from the closest end
        if (index < size / 2) {
            // Start from head for indices in first half
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            // Start from tail for indices in second half
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        return current;
    }
    
    // Remove element at specified index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> nodeToRemove;
        
        if (index == 0) {
            // Remove head
            nodeToRemove = head;
            head = head.getNext();
            if (head == null) {
                tail = null; // List is now empty
            } else {
                head.setPrev(null);
            }
        } else if (index == size - 1) {
            // Remove tail
            nodeToRemove = tail;
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            // Remove from middle
            nodeToRemove = getNodeAt(index);
            nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
            nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
        }
        
        size--;
        return nodeToRemove.getData();
    }
    
    // Get size of the list
    public int size() {
        return size;
    }
    
    // Check if list is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Clear the list
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
````

## 2. ArrayList Implementation

ArrayLists use a dynamically resizing array under the hood.

---

### ArrayList
- **Type**: List
- **Implementation**: Resizable array
- **Order**: Maintains the order of insertion
- **Access**: Elements can be accessed by their index
- **Duplicates**: Allows duplicate elements
- **Usage**: Suitable for storing a list of items where order matters and quick access by index is needed

Example:
```java
ArrayList<String> list = new ArrayList<>();
list.add("apple");
list.add("banana");
list.add("apple"); // Duplicates are allowed
System.out.println(list.get(1)); // Output: banana
```
---

### Custom
````java
public class CustomArrayList<T> {
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
        
        elements = newElements;
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
````

## 3. Stack Implementation

Stacks follow Last-In-First-Out (LIFO) principle. We can implement a stack using an array or a linked list.

### Stack
- **Type**: Stack
- **Implementation**: Uses a dynamic array or linked list internally
- **Order**: Follows Last-In-First-Out (LIFO) principle
- **Access**: Elements are accessed by pushing and popping from the top of the stack
- **Duplicates**: Allows duplicate elements
- **Usage**: Suitable for scenarios like expression evaluation, backtracking, function call management, undo mechanisms, and syntax parsing

---
Example:
```java
Stack<String> stack = new Stack<>();
stack.push("apple");
stack.push("banana");
stack.push("cherry");
System.out.println(stack.peek()); // Output: cherry
System.out.println(stack.pop()); // Output: cherry
System.out.println(stack.pop()); // Output: banana
System.out.println(stack.isEmpty()); // Output: false
```
---
### Array-based Stack
````java
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
````

## 4. Queue Implementation

Queues follow First-In-First-Out (FIFO) principle.
---
### Queue
- **Type**: Queue
- **Implementation**: Uses a linked list or array internally
- **Order**: Follows First-In-First-Out (FIFO) principle
- **Access**: Elements are accessed by adding to the end and removing from the front of the queue
- **Duplicates**: Allows duplicate elements
- **Usage**: Suitable for scenarios like task scheduling, breadth-first search in graphs, and buffering data streams

Example:
```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.add("apple");
        queue.add("banana");
        queue.add("cherry");
        System.out.println(queue.peek()); // Output: apple
        System.out.println(queue.poll()); // Output: apple
        System.out.println(queue.poll()); // Output: banana
        System.out.println(queue.isEmpty()); // Output: false
    }
}
```
---

### Array-based Queue
````java
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
````

1. **Encapsulation**: Note how each data structure encapsulates its internal representation.

2. **Time Complexity**: Discuss the time complexity of operations:
   - LinkedList: O(1) for add/remove at ends, O(n) for access by index
   - ArrayList: O(1) for access by index, O(n) for insertions/deletions in the middle
   - Stack: O(1) for push/pop/peek
   - Queue: O(1) for enqueue/dequeue/peek

3. **Space Complexity**: Discuss memory usage differences between linked and array-based structures.

4. **Generics**: Demonstrate how Java's generic system allows for type-safe collections.

5. **Edge Cases**: Cover how each implementation handles exceptions and edge cases.

---

# Custom HashSet and HashMap Implementations in Java

## 1. Custom HashSet Implementation

A HashSet is a collection that uses hash codes to store unique elements in a way that allows for very fast retrieval.

---
### HashSet
- **Type**: Set
- **Implementation**: Hash table
- **Order**: Does not maintain any order (from Java 8 onwards, insertion order is maintained in some cases)
- **Access**: Elements are accessed via iteration
- **Duplicates**: Does not allow duplicate elements
- **Usage**: Suitable for storing unique elements and provides constant-time performance for basic operations like add, remove, and contains

Example:
```java
HashSet<String> set = new HashSet<>();
set.add("apple");
set.add("banana");
set.add("apple"); // Duplicate, will not be added
System.out.println(set.contains("banana")); // Output: true
System.out.println(set); // Output: [banana, apple] (order may vary)
---

    ````java
    public class CustomHashSet<E> {
        private static final int DEFAULT_CAPACITY = 16;
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;
        
        private Object[] buckets;
        private int size;
        private float loadFactor;
        
        // Node class for chaining to resolve collisions
        private static class Node<E> {
            private E element;
            private Node<E> next;
            
            public Node(E element) {
                this.element = element;
                this.next = null;
            }
        }
        
        public CustomHashSet() {
            this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        }
        
        public CustomHashSet(int initialCapacity) {
            this(initialCapacity, DEFAULT_LOAD_FACTOR);
        }
        
        public CustomHashSet(int initialCapacity, float loadFactor) {
            if (initialCapacity <= 0) {
                throw new IllegalArgumentException("Initial capacity must be positive");
            }
            if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
                throw new IllegalArgumentException("Load factor must be positive");
            }
            
            this.buckets = new Object[initialCapacity];
            this.size = 0;
            this.loadFactor = loadFactor;
        }
        
        // Add an element to the set
        public boolean add(E element) {
            if (element == null) {
                throw new NullPointerException("HashSet does not allow null elements");
            }
            
            // Check if we need to resize
            if (size >= buckets.length * loadFactor) {
                resize(buckets.length * 2);
            }
            
            int index = getBucketIndex(element);
            
            // Check if element already exists
            @SuppressWarnings("unchecked")
            Node<E> current = (Node<E>) buckets[index];
            while (current != null) {
                if (current.element.equals(element)) {
                    return false; // Element already exists
                }
                current = current.next;
            }
            
            // Add new element at the beginning of the linked list for this bucket
            @SuppressWarnings("unchecked")
            Node<E> head = (Node<E>) buckets[index];
            Node<E> newNode = new Node<>(element);
            newNode.next = head;
            buckets[index] = newNode;
            
            size++;
            return true;
        }
        
        // Remove an element from the set
        public boolean remove(Object element) {
            if (element == null) {
                return false;
            }
            
            int index = getBucketIndex(element);
            
            @SuppressWarnings("unchecked")
            Node<E> current = (Node<E>) buckets[index];
            Node<E> prev = null;
            
            while (current != null) {
                if (current.element.equals(element)) {
                    // Element found, remove it
                    if (prev == null) {
                        buckets[index] = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    size--;
                    return true;
                }
                prev = current;
                current = current.next;
            }
            
            return false; // Element not found
        }
        
        // Check if an element exists in the set
        public boolean contains(Object element) {
            if (element == null) {
                return false;
            }
            
            int index = getBucketIndex(element);
            
            @SuppressWarnings("unchecked")
            Node<E> current = (Node<E>) buckets[index];
            
            while (current != null) {
                if (current.element.equals(element)) {
                    return true;
                }
                current = current.next;
            }
            
            return false;
        }
        
        // Get the bucket index for an element
        private int getBucketIndex(Object element) {
            int hashCode = element.hashCode();
            // Handle negative hash codes
            hashCode = hashCode < 0 ? -hashCode : hashCode;
            return hashCode % buckets.length;
        }
        
        // Resize the buckets array
        private void resize(int newCapacity) {
            Object[] oldBuckets = buckets;
            buckets = new Object[newCapacity];
            
            // Rehash all elements into the new buckets
            for (int i = 0; i < oldBuckets.length; i++) {
                @SuppressWarnings("unchecked")
                Node<E> current = (Node<E>) oldBuckets[i];
                
                while (current != null) {
                    Node<E> next = current.next;
                    
                    // Recompute index for the element
                    int newIndex = getBucketIndex(current.element);
                    
                    // Add to the beginning of the linked list in the new bucket
                    @SuppressWarnings("unchecked")
                    Node<E> head = (Node<E>) buckets[newIndex];
                    current.next = head;
                    buckets[newIndex] = current;
                    
                    current = next;
                }
            }
        }
        
        // Get the number of elements in the set
        public int size() {
            return size;
        }
        
        // Check if the set is empty
        public boolean isEmpty() {
            return size == 0;
        }
        
        // Clear the set
        public void clear() {
            buckets = new Object[DEFAULT_CAPACITY];
            size = 0;
        }
    }
    ````

 ## 2. Custom HashMap Implementation

A HashMap uses key-value pairs and hash codes for efficient storage and retrieval.

---
### HashMap
- **Type**: Map
- **Implementation**: Hash table
- **Order**: Does not maintain any order (from Java 8 onwards, insertion order is maintained in some cases)
- **Access**: Elements are accessed by their key
- **Duplicates**: Keys must be unique, but values can be duplicated
- **Usage**: Suitable for storing key-value pairs where quick lookup by key is needed

Example:
```java
HashMap<String, Integer> map = new HashMap<>();
map.put("apple", 1);
map.put("banana", 2);
map.put("apple", 3); // The value for key "apple" is updated
System.out.println(map.get("apple")); // Output: 3
```
---

````java
public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private Entry<K, V>[] buckets;
    private int size;
    private float loadFactor;
    
    // Entry class for storing key-value pairs
    private static class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.buckets = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }
    
    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.buckets = new Entry[initialCapacity];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }
    
    public CustomHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Load factor must be positive");
        }
        
        @SuppressWarnings("unchecked")
        Entry<K, V>[] newBuckets = new Entry[initialCapacity];
        this.buckets = newBuckets;
        this.size = 0;
        this.loadFactor = loadFactor;
    }
    
    // Put a key-value pair in the map
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("HashMap does not allow null keys");
        }
        
        // Check if we need to resize
        if (size >= buckets.length * loadFactor) {
            resize(buckets.length * 2);
        }
        
        int index = getBucketIndex(key);
        
        // Check if key already exists
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                V oldValue = current.value;
                current.value = value; // Update value
                return oldValue;
            }
            current = current.next;
        }
        
        // Add new entry at the beginning of the linked list for this bucket
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        
        size++;
        return null;
    }
    
    // Get a value by key
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        
        int index = getBucketIndex(key);
        
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        
        return null; // Key not found
    }
    
    // Remove a key-value pair from the map
    public V remove(Object key) {
        if (key == null) {
            return null;
        }
        
        int index = getBucketIndex(key);
        
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;
        
        while (current != null) {
            if (current.key.equals(key)) {
                // Key found, remove the entry
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        
        return null; // Key not found
    }
    
    // Check if the map contains a key
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        
        int index = getBucketIndex(key);
        
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    // Get the bucket index for a key
    private int getBucketIndex(Object key) {
        int hashCode = key.hashCode();
        // Handle negative hash codes
        hashCode = hashCode < 0 ? -hashCode : hashCode;
        return hashCode % buckets.length;
    }
    
    // Resize the buckets array
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldBuckets = buckets;
        Entry<K, V>[] newBuckets = new Entry[newCapacity];
        buckets = newBuckets;
        
        // Rehash all entries into the new buckets
        for (int i = 0; i < oldBuckets.length; i++) {
            Entry<K, V> current = oldBuckets[i];
            
            while (current != null) {
                Entry<K, V> next = current.next;
                
                // Recompute index for the entry
                int newIndex = getBucketIndex(current.key);
                
                // Add to the beginning of the linked list in the new bucket
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                
                current = next;
            }
        }
    }
    
    // Get the number of key-value pairs in the map
    public int size() {
        return size;
    }
    
    // Check if the map is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Clear the map
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }
}
````

## Key Concepts for HashSet and HashMap

1. **Hashing**: Both implementations use the `hashCode()` method to determine where to store elements.

2. **Collision Resolution**: We've implemented chaining (using linked lists) to handle hash collisions.

3. **Load Factor**: Controls when to resize the underlying array to maintain efficiency.

4. **Time Complexity**:
   - Average case: O(1) for add, remove, contains/get operations
   - Worst case: O(n) if many elements hash to the same bucket

5. **Resizing**: When the number of elements exceeds the load factor threshold, the underlying array is resized to maintain performance.

6. **Differences between HashSet and HashMap**:
   - HashSet stores single elements with no duplicates
   - HashMap stores key-value pairs with unique keys

7. **Implementation Challenge**: Proper handling of hash collisions is critical for maintaining good performance.


### Summary
- **ArrayList**: Ordered collection, allows duplicates, accessed by index.
- **LinkedList**: Slower random access, fast insertions/deletions.
- **Stack**: Ordered collection, follows Last-In-First-Out (LIFO) principle, accessed via push 
and pop operations.
- **Queue**: Ordered collection, follows First-In-First-Out (FIFO) principle, accessed via add and remove operations.
- **PriorityQueue**: Ordered by priority, elements accessed based on priority.
- **Deque**: Double-ended queue, elements can be added/removed from both ends.
- **ArrayDeque**: Double-ended queue implemented using a resizable array, elements can be added/removed from both ends.
- **HashMap**: Key-value pairs, keys are unique, accessed by key.
- **HashSet**: Unordered collection, unique elements, accessed via iteration.

```java


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Deque;
import java.util.ArrayDeque;

public class App {
    public static void main(String[] args) {
        BeingsInMyHouse BriansCircus = new BeingsInMyHouse();
        BriansCircus.addAnimal("Golden Retriever Walnut");
        BriansCircus.addAnimal("Golden Retriever Cashew");
        BriansCircus.addPerson("Brian");
        BriansCircus.addPerson("Brian's Wife");
        BriansCircus.addPerson("Brian's Daughter");
        BriansCircus.addPerson("Brian's Son");
        BriansCircus.addPerson("Brian's Daughter's Friend");

        // ArrayList
        ArrayList<String> list = new ArrayList<String>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Programming");
        list.add("Language");
        list.add("Data Structures");

        // LinkedList
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("Hello");
        linkedList.add("World");
        linkedList.add("Java");
        linkedList.add("Programming");
        linkedList.add("Language");

        // Stack
        Stack<String> stack = new Stack<>();
        stack.push("apple");
        stack.push("banana");
        stack.push("cherry");
        System.out.println(stack.peek()); // Output: cherry
        System.out.println(stack.pop()); // Output: cherry
        System.out.println(stack.pop()); // Output: banana
        System.out.println(stack.isEmpty()); // Output: false

        // PriorityQueue
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(1);
        pq.add(3);
        System.out.println(pq.poll()); // Output: 1 (smallest element)
        System.out.println(pq.poll()); // Output: 3
        System.out.println(pq.poll()); // Output: 5

        // Deque
        Deque<String> deque = new LinkedList<>();
        deque.addFirst("apple");
        deque.addLast("banana");
        deque.addFirst("cherry");
        System.out.println(deque.pollFirst()); // Output: cherry
        System.out.println(deque.pollLast()); // Output: banana

        // ArrayDeque
        Deque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst("apple");
        arrayDeque.addLast("banana");
        arrayDeque.addFirst("cherry");
        System.out.println(arrayDeque.pollFirst()); // Output: cherry
        System.out.println(arrayDeque.pollLast()); // Output: banana

        // HashMap
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("apple", 3); // The value for key "apple" is updated
        System.out.println(map.get("apple")); // Output: 3

        // HashSet
        HashSet<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");
        set.add("apple"); // Duplicate, will not be added
        System.out.println(set.contains("banana")); // Output: true
        System.out.println(set); // Output: [banana, apple] (order may vary)

        System.out.println("Hello World!");
    }
}
```
