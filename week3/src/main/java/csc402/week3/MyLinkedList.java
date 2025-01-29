package csc402.week3;

public class MyLinkedList {
    private int size;
    private Node head;
    private Node tail;

    public MyLinkedList(){
        System.out.println("MyLinkedList constructor");
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(BeingsInMyHouse param){
        Node newNode = new Node(param);
        if(this.head == null){
            this.head = newNode;
            this.tail = newNode;
        }else{
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size++;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }
}

class Node{
    Object oData;
    BeingsInMyHouse data;
    Node next;
    Node(BeingsInMyHouse param){//(Object param){
        //this.data = (BeingsInMyHouse)param;
        //this.oData = param;
        this.data = param;
        this.next = null;
    }
}
