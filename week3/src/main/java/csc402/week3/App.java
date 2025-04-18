package csc402.week3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LinkedList<String> list = new LinkedList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("ice cream");
        list.add("video games");
        list.add("movies");
        list.add("homework");
        list.add("summer");
        

        System.out.println(list.get(1)); // Output: banana

        //Custom Linked List
        String data = "Hooray for Custom Linked Lists";
        CustomDoublyLinkedList myCustomList = new CustomDoublyLinkedList<>();
        for(int i =0; i< list.size(); i++){
            myCustomList.add(list.get(i));
        }

        myCustomList.get(7);
        myCustomList.remove(6);
        myCustomList.addFirst(1000);
        
        for(int i=0; i< myCustomList.size(); i++)
        {
            String dataString = myCustomList.get(i).toString();
            System.out.println(myCustomList.get(i).toString());
        }
        myCustomList.clear();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("apple");
        arrayList.add("banana");
        arrayList.add("apple"); // Duplicates are allowed
        arrayList.add("canteloupe is yummy");
        arrayList.clone();
        System.out.println(arrayList.get(1)); // Output: banana

        CustomArrayList<Double> myArrayList = new CustomArrayList<>();
        myArrayList.add(10.5);
        myArrayList.add(15.0);


        Stack<String> stack = new Stack<>();
        stack.push("apple");
        stack.push("banana");
        stack.push("cherry");
        System.out.println(stack.peek()); // Output: cherry
        System.out.println(stack.pop()); // Output: cherry
        System.out.println(stack.pop()); // Output: banana
        System.out.println(stack.isEmpty()); // Output: false


        Queue<String> queue = new LinkedList();
        queue.add("apple");
        queue.add("banana");
        queue.add("cherry");
        System.out.println(queue.peek()); // Output: apple
        System.out.println(queue.poll()); // Output: apple
        System.out.println(queue.remove()); // Output: banana
        System.out.println(queue.isEmpty()); // Output: false
       

        System.out.println( "Hello World!" );
    }
}
