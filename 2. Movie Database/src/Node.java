public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }

    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) {
		//question make a new node with the obj T, set the next node of T as the next
        //question set the next node of the current node to obj
        Node<T> nextNode = new Node<T>(obj);
        Node<T> currNext = this.getNext();
        nextNode.setNext(currNext);
        this.setNext(nextNode);
        //throw new UnsupportedOperationException("not implemented yet");
    }
    
    public final void removeNext() {
		//question get the next node of the current node, and set the next node to the next node of the next node.
        Node<T> nextNextNode = this.getNext().getNext();
        this.setNext(nextNextNode);
        //throw new UnsupportedOperationException("not implemented yet");
    }

}