/**
 * @author Lisa Jia
 * @author Student ID: 251140790
 * 
 *         Date Completed: 04/06/2021
 * 
 *         CS1027 ASSIGN 4:This programs builds a n-ary tree based off of
 *         specifications and rules provided.This class builds the nodes of the
 *         tree.
 */
public class NaryTreeNode<T> {
	private T data; // variable to store the data of each node
	private int numChildren; // variable to count the number of children each node has
	private NaryTreeNode<T>[] children; // variable to represent the child node

	// constructor initializes data and children
	public NaryTreeNode(T data) {
		children = null;
		this.data = data;

	}

	/**
	 * method adds a child node to the existing node
	 * 
	 * @param newNode
	 */
	public void addChild(NaryTreeNode<T> newNode) {
		// if the child is null, it is initialized with 3 slots
		if (children == null) {
			children = (NaryTreeNode<T>[]) (new NaryTreeNode[3]);
			// If the children array is full, the expandCapcity method is called
		} else if ((numChildren) % 3 == 0) {
			expandCapacity();
		}

		children[numChildren] = newNode;
		numChildren++;
	}

	/**
	 * expands the capacity of the children array by adding 3 more spaces and
	 * transfers the existing children nodes into the new, larger array
	 */
	private void expandCapacity() {
		int newNumChildren = numChildren + 3;
		NaryTreeNode<T>[] newChildren = (NaryTreeNode<T>[]) (new NaryTreeNode[numChildren + 3]);
		for (int i = 0; i < numChildren; i++) {
			newChildren[i] = children[i];
		}

		children = newChildren;
	}

	// returns the number of children
	public int getNumChildren() {
		return numChildren;
	}

	// takes in an int index and returns the child stored in that index
	public NaryTreeNode<T> getChild(int index) {
		if (children == null || index < 0 || index > numChildren) {
			return null;
		}
		return children[index];
	}

	// returns the data item stored in this node
	public T getData() {
		return data;
	}

	// returns a string that says "Node containing " followed by the data item
	// stored in this node
	public String toString() {
		return "Node containing " + this.data.toString();
	}

}
