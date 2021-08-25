/**
 * @author Lisa Jia
 * 
 * This program computes a path that cupid can follow to shoot arrows
 *
 * @param <T> allows for any data type 
 */
public class ArrayStack<T> implements ArrayStackADT<T> {
	private T[] stack; //this array will store the data items of a stack
	private int top; //contains the number of data items in the stack
	public static String sequence;
/**
 * creates an initial stack; top is initialized as -1 
 * @param the initial stack is initialized to have a capacity of 14
 * @param initialCapacity is the specified capacity
 */
	
	public ArrayStack() {
		top = -1;
		stack = (T[]) (new Object[14]);
		sequence = "";
	}

	public ArrayStack(int initialCapacity) {
		top = -1;
		stack = (T[]) (new Object[initialCapacity]);
		sequence = "";

	}

	public void push(T dataItem) {
		if (top + 1 == stack.length) {
			expandCapacity(); // if the stack reaches its maximum length, call expandCapacity()
		}
		stack[top + 1] = dataItem; // dataItem is added to the top of the stack
		top++;

		if (dataItem instanceof MapCell) {
			sequence += "push" + ((MapCell) dataItem).getIdentifier();
		} else {
			sequence += "push" + dataItem.toString();
		}

	}
// private helper method expandCapacity()
	private void expandCapacity() {
		int stackSize = stack.length;
		T[] larger;
		// if the stackSize is smaller than 50, the stackSize is increased by 10
		if (stackSize < 50) {
			larger = (T[]) (new Object[stackSize + 10]);
		// otherwise, the array is expanded through doubling the array size
		} else {
			larger = (T[]) (new Object[stack.length * 2]);

		}
		// elements are added to the new array 
		for (int i = 0; i < stack.length; i++)
			larger[i] = stack[i];

		stack = larger;
	}
	/**
	 * removes and returns the data items at the top o the stack
	 * 
	 * if the stack is empty, EmptyStackException is thrown
	 */
	public T pop() throws EmptyStackException {
		if (top == -1) {
			throw new EmptyStackException("stack");
		}
		top--;
		T result = (T) stack[top + 1];
		stack[top + 1] = null;

		// if the stackSize is smaller than a quarter of the length, then shrinkCapacity() is called
		if (top + 1 < stack.length / 4)
			shrinkCapacity();
		
		if (result instanceof MapCell) {
			sequence += "pop" + ((MapCell) result).getIdentifier();
		} else {
			sequence += "pop" + result.toString();
		}
		return result;
	}

	private void shrinkCapacity() {
		int halfSize = stack.length / 2;
		T[] shrinked;
		
		// the array size is decreased to half of its original size to a minimum of 14 
		if (halfSize > 14) {
			shrinked = (T[]) (new Object[halfSize]);
		} 
		else {
			halfSize = 14;
			shrinked = (T[]) (new Object[halfSize]);
		}

		for (int i = 0; i < Math.min(halfSize, stack.length/2); i++) {
			shrinked[i] = stack[i];
		}
		stack = shrinked;
	}
	// the data item at the top of the stack is returned without removing it
	public T peek() throws EmptyStackException {
		if (top == -1) {
			throw new EmptyStackException("stack");
		}
		return stack[top];
	}
	// returns true if the stack is empty 
	public boolean isEmpty() {
		return (top == -1);
	}

	// returns the number of data items in the stack
	public int size() {
		return (top + 1);
	}

	// returns the capacity of the array stack
	public int length() {
		return stack.length;

	}
	
	// returns a string representation of the stack 
	public String toString() {
		String result = "Stack: ";
		for (int index = 0; index < top + 1; index++) {
			if (index != top) {
				result = result + stack[index].toString() + ", ";
			} else {
				result = result + stack[index].toString();
			}
		}
		return result;

	}
}
