/*
This code demonstrates a simple implementation of a doubly linked list using generic programming
and following common Onject-Oriented Programming practices.

The reason for having multiple classes in one file is only to display all code in one place.
*/

class DoublyLinkedList<E> {
    // a node class for a doubly linked list containing any object
    class Node<E> {
        // initialize private variables which cannot be modified by an outside class
        private Node<E> prev, next;
        private E element;

        // default Node constructor setting all variables to null
        public Node() {
            this.prev = null;
            this.next = null;
            this.element = null;
        }
        // Node constructor with specific content
        public Node(Node<E> newPrev, Node<E> newNext, E element) {
            this.next = newNext;
            this.prev = newPrev;
            this.element = element;
        }
        // getter and setter functions available to outside classes
        public E getElement() { return this.element; }
        public Node<E> getNext() { return this.next; }
        public Node<E> getPrev() { return this.prev; }
        public void setElement(E element) { this.element = element; }
        public void setNext(Node<E> newNext) { this.next = newNext; }
        public void setPrev(Node<E> newPrev) { this.prev = newPrev; }
    }

    // initialize header and trailer and set to null
    private Node<E> header = new Node<E>();
    private Node<E> trailer = new Node<E>();
    private int length = 0;

    // default DoublyLinkedList constructor creating an empty list
    public DoublyLinkedList() {
        header.setPrev(null);
        header.setNext(trailer);
        trailer.setPrev(header);
        trailer.setNext(null);
        length = 0;
    }
    // return node at index
    private Node<E> getNode(int index) throws ArrayIndexOutOfBoundsException {
        Node<E> current = header.getNext();

        // if provided index is negative or does not meet the length requirement: throw exception
        if (index < 0 || index >= getLength()) {
            throw new ArrayIndexOutOfBoundsException("Error: Provided index is out of bounds.");
        }
        // if index is not found before reaching end of list: throw exception
        else if (current == trailer) {
            throw new ArrayIndexOutOfBoundsException("Error: Provided index is out of bounds.");
        }

        int count = 0;
        while (current != trailer) {
            if (count == index) {
                // index found: break out of loop to return node
                break;
            }
            else {
                if (current.getNext() != trailer) {
                    current = current.getNext();
                    count++;
                }
                else {
                    throw new ArrayIndexOutOfBoundsException("Error: Provided index is out of bounds.");
                }
            }
        }
        return current;
    }
    // get element at node index
    public E getAtIndex(int index) {
        return getNode(index).getElement();
    }
    // return length of doubly linked list
    public int getLength() {
        return length;
    }
    // add a node to the doubly linked list
    public void add(E element) {
        // always add new node to before trailer and set trailer as its next node
        Node<E> newNode = new Node<E>(trailer.getPrev(), trailer, element);
        trailer.getPrev().setNext(newNode);
        trailer.setPrev(newNode);
        length++;
    }
    // delete node at index
    public E deleteAtIndex(int index) {
        // make previous and next nodes point at each other
        Node<E> deletedNode = getNode(index);
        E deletedNodeElement = deletedNode.getElement();
        deletedNode.getPrev().setNext(deletedNode.getNext());
        deletedNode.getNext().setPrev(deletedNode.getPrev());
        // uninitialize the deleted node
        deletedNode.setPrev(null);
        deletedNode.setNext(null);
        // decrease length of list
        length--;
        // return element of deleted node
        return deletedNodeElement;
    }

}
// Demonstration class
public class DoublyLinkedListDemo {
    public static void main(String[] args) {
        // create a DoublyLinkedList object and array for demonstration
        DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
        int[] intArray = {1,2,3,4,5,6,7,8,9,10};

        // add nodes to the list
        for (int i = 0; i < intArray.length; i++) {
            list.add(intArray[i]);
        }

        // print the list
        System.out.println("Length of list is: " + list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
			System.out.println("Element" + i + " = " + list.getAtIndex(i));
		}

		// deleting some elements to test delete method
		System.out.println("\nDeleting elements " +
                            list.deleteAtIndex(0) + "," +
                            list.deleteAtIndex(2) + "," +
                            list.deleteAtIndex(3) + "," +
                            list.deleteAtIndex(6) + "\n"
        );

        // print the list after deleting elements
        System.out.println("Length of list is: " + list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
			System.out.println("Element" + i + " = " + list.getAtIndex(i));
		}
    }
}