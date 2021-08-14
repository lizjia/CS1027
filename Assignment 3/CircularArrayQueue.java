/**
 * @author Lisa Jia
 * @author Student Number: 251140790
 * 
 *         Date Completed: 03/16/2021
 * 
 *         CS1027 ASSIGN 3: This Program computes a program that allows the use
 *         to encode and decode any string of characters using a set of rules
 *         and limitations that was provided. This class programs the queue that
 *         the data is stored in.
 *
 * @param <T> allows for any data type
 */
public class CircularArrayQueue<T> implements QueueADT<T> {
	private int front;
	private int rear;
	private int count; // initializes the front, count, and rear variables
	private T[] queue; // creates a queue that will store the data item
	private final int DEFAULT_CAPACITY = 20; // initializes the default capacity to 20

	/**
	 * creates an initial stack where the front is initialized to be -1 rear is
	 * initialized to the default capacity of 20
	 */
	public CircularArrayQueue() {
		front = 1;
		rear = DEFAULT_CAPACITY;
		count = 0;
		queue = (T[]) (new Object[DEFAULT_CAPACITY]);
	}

	/**
	 * creates an initial stack where the front is initialized to be -1 rear is
	 * initialized to the default capacity of 20
	 * 
	 * @param initialCapacity
	 */

	public CircularArrayQueue(int initialCapacity) {
		front = 1;
		rear = initialCapacity;
		count = 0;
		queue = (T[]) (new Object[initialCapacity]);
	}

	/**
	 * method will enqueue data to the queue
	 * 
	 * @param element
	 */
	public void enqueue(T element) {
		if (size() == queue.length) {
			expandCapacity(); // if the size exceeds the length of the capacity, the expandCapacity() method
								// is called
		}
		queue[rear % queue.length] = element;
		rear = rear % queue.length + 1; // value is enqueued to the rear of the queue
		count++; // count is incremented
	}

	// method will dequeue data from the queue
	public T dequeue() throws EmptyCollectionException {
		if (isEmpty())
			throw new EmptyCollectionException("queue"); // if the queue is empty, an exception is thrown
		T result = (T) queue[front - 1];
		queue[front - 1] = null; // front value is taken and dequeued; then set to null

		front = front % queue.length + 1;
		count--; // count is decremented

		return result; // decremented value is returned
	}

	// method will return the item at the front of the queue
	public T first() throws EmptyCollectionException {
		if (isEmpty())
			throw new EmptyCollectionException("queue"); // if the queue is empty, an exception is thrown

		return queue[front - 1];
	}

	// will return true if the queue is empty
	public boolean isEmpty() {
		return (count == 0);
	}

	// returns the number of items on the queue.
	public int size() {
		return count;
	}

	// returns the front index value
	public int getFront() {
		return front;

	}

	// returns the rear index value
	public int getRear() {
		return rear;
	}

	// returns the current length (capacity) of the array
	public int getLength() {
		return queue.length;
	}

	// method is called to expand the capacity of the queue when the max is reached
	private void expandCapacity() {
		T[] larger = (T[]) (new Object[queue.length + 20]); // is expanded by 20 additional slots

		for (int scan = 0; scan < count; scan++) {
			larger[scan] = queue[scan];
		}
		front = 1;
		rear = count;
		queue = larger; // elements are transferred to the new larger array
	}

	/**
	 * returns the string containing "QUEUE: " followed by each of the queue's items
	 * in order from front to rear with ", " between each of the items and a period
	 * at the end of the last item.
	 */
	public String toString() {
		String result = "QUEUE: ";
		int scan = 0;
		if (isEmpty()) {
			return "The queue is empty"; // returns "The queue is empty" if the queue is empty
		}

		for (int i = 0; i < count - 1; i++) {
			result += queue[i] + ", ";
		}

		result += queue[count - 1] + ".";
		return result;

	}

}
