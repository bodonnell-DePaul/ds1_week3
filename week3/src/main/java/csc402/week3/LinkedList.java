package csc402.week3;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/* ***********************************************************************
 *  Compilation:  javac LinkedList.java
 *  Execution:    java LinkedList
 *
 *  A list implemented with a doubly linked list. The elements are stored
 *  (and iterated over) in the same order that they are inserted.
 *
 *  % java List
 *  This
 *  is
 *  a
 *  test.
 *
 *  This
 *  is
 *  a
 *  test.
 *
 *************************************************************************/

public class LinkedList<T> implements Iterable<T> {
	private int N;                 // number of elements on list
	private final Node<T> pre;     // sentinel before first item
	private final Node<T> post;    // sentinel after last item
	private long opcount;

	public LinkedList() {
		pre  = new Node<>();
		post = new Node<>();
		pre.next = post;
		post.prev = pre;
	}

	// linked list node helper data type
	private static class Node<T> {
		public Node() { }
		public T item;
		public Node<T> next;
		public Node<T> prev;
	}

	public boolean isEmpty()    { return N == 0; }
	public int size()           { return N;      }

	// add the item to the end of the list
	public void add(T item) {
		Node<T> last = post.prev;
		Node<T> x = new Node<>();
		x.item = item;
		x.next = post;
		x.prev = last;
		post.prev = x;
		last.next = x;
		N++;
		opcount++;
	}

	public ListIterator<T> iterator()  { return new LinkedListIterator(); }

	// assumes no calls to add() during iteration
	private class LinkedListIterator implements ListIterator<T> {
		private Node<T> current      = pre.next;  // the node that is returned by next()
		private Node<T> lastAccessed = null;      // the last node to be returned by prev() or next()
		// reset to null upon intervening remove() or add()
		private int index = 0;
		private long oc = opcount;
		private void ocCheck() { if (opcount != oc) throw new ConcurrentModificationException (); }
		private void ocInc()   { ocCheck(); opcount++; oc++; }

		public boolean hasNext()      { ocCheck(); return index < N; }
		public boolean hasPrevious()  { ocCheck(); return index > 0; }
		public int previousIndex()    { ocCheck(); return index - 1; }
		public int nextIndex()        { ocCheck(); return index;     }

		public T next() {
			ocCheck();
			if (!hasNext()) throw new NoSuchElementException();
			lastAccessed = current;
			T item = current.item;
			current = current.next;
			index++;
			return item;
		}

		public T previous() {
			ocCheck();
			if (!hasPrevious()) throw new NoSuchElementException();
			current = current.prev;
			lastAccessed = current;
			T item = current.item;
			index--;
			return item;
		}

		// replace the item of the element that was last accessed by next() or previous()
		// condition: no calls to remove() or add() after last call to next() or previous()
		public void set(T item) {
			ocInc();
			if (lastAccessed == null) throw new Error();
			lastAccessed.item = item;
		}

		// remove the element that was last accessed by next() or previous()
		// condition: no calls to remove() or add() after last call to next() or previous()
		public void remove() {
			ocInc();
			if (lastAccessed == null) throw new Error();
			Node<T> lastPrev = lastAccessed.prev;
			Node<T> lastNext = lastAccessed.next;
			lastPrev.next = lastNext;
			lastNext.prev = lastPrev;
			N--;
			if (current == lastAccessed)
				current = lastNext;
			else
				index--;
			lastAccessed = null;
		}

		// add element to list
		public void add(T item) {
			ocInc();
			Node<T> first  = current.prev;
			Node<T> middle = new Node<>();
			Node<T> last   = current;
			middle.item = item;
			first. next = middle;
			middle.next = last;
			last.  prev = middle;
			middle.prev = first;
			N++;
			index++;
			lastAccessed = null;
		}

	}

	public String toString () {
		StringBuilder sb = new StringBuilder ();
		Iterator<T> it = this.iterator ();
		if (it.hasNext ())
			sb.append (it.next ());
		while (it.hasNext ()) {
			sb.append (" ");
			sb.append (it.next ());
		}
		return sb.toString ();
	}

	public static <T> void printForward(String s, LinkedList<Integer> list, ListIterator<T> iterator) {
		System.out.println();
		System.out.println(s);
		System.out.println(list);
		while (iterator.hasNext())
			System.out.format ("[%d,%d] ", iterator.nextIndex (), iterator.next ());
		while (iterator.hasPrevious())
			System.out.format ("[%d,%d] ", iterator.previousIndex (), iterator.previous());
		System.out.println ();
	}
	public static <T> void printBackward(String s, LinkedList<Integer> list, ListIterator<T> iterator) {
		System.out.println();
		System.out.println(s);
		System.out.println(list);
		while (iterator.hasPrevious())
			System.out.format ("[%d,%d] ", iterator.previousIndex (), iterator.previous());
		while (iterator.hasNext())
			System.out.format ("[%d,%d] ", iterator.nextIndex (), iterator.next ());
		System.out.println ();
	}
	// a test client
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();

		list.add(93);
		list.add(48);
		list.add(94);
		list.add(4);
		list.add(48);
		list.add(94);

		ListIterator<Integer> iterator = list.iterator();
		printForward ("original", list, iterator);

		// set forwards
		while (iterator.hasNext()) {
			int x = iterator.next();
			iterator.set(x + 1);
		}
		printBackward ("after set forward", list, iterator);

		// set backwards
		while (iterator.hasPrevious()) {
			int x = iterator.previous();
			iterator.set(3 * x);
		}
		printForward ("after set backward", list, iterator);


		// remove forwards
		while (iterator.hasNext()) {
			int x = iterator.next();
			if (x % 2 == 0) iterator.remove();
		}
		printBackward ("after remove forward", list, iterator);

		// remove backwards
		while (iterator.hasPrevious()) {
			int x = iterator.previous();
			if (x % 5 == 0) iterator.remove();
		}
		printForward ("after remove backward", list, iterator);

		// add forwards
		while (iterator.hasNext()) {
			int x = iterator.next();
			iterator.add(x + 10);
		}
		printBackward ("after add forward", list, iterator);

		// add backwards
		while (iterator.hasPrevious()) {
			int x = iterator.previous();
			iterator.add(x - 1);
			iterator.previous();
		}
		printForward ("after add backward", list, iterator);
	}
}

