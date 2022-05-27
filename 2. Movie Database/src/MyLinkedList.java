
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements ListInterface<T> {
	// dummy head
	Node<T> head;
	int numItems;

	public MyLinkedList() {
		head = new Node<T>(null);
	}

	public final Iterator<T> iterator() {
		return new MyLinkedListIterator<T>(this);
	}

	public void add2(T item){
		Node <T> newItem = new Node<T>(item);
		Node <T> last = head;
		String new_data = newItem.getItem().toString();
		if(this.isEmpty() || last.getNext().getItem().toString().compareTo(new_data)>0){
			newItem.setNext(last.getNext());
			last.setNext(newItem);
		}else{
			while (last.getNext()!=null && last.getNext().getItem().toString().compareTo(new_data)<0){
				last = last.getNext();
			}
			newItem.setNext(last.getNext());
			last.setNext(newItem);
		}
		numItems += 1;
	}


	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) {
		Node<T> last = head;
		if (!this.isEmpty()) {
			while (last.getNext() != null) {
				last = last.getNext();
			}
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}

}


class MyLinkedListIterator<T> implements Iterator<T> {

	public MyLinkedList<T> list;
	private Node<T> curr;
	private Node<T> prev;

	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}


	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}
