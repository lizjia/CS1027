import java.util.Iterator;

/**
 * @author Lisa Jia
 * @author Student ID: 251140790
 * 
 *         Date Completed: 04/06/2021
 * 
 *         CS1027 ASSIGN 4:This programs builds a n-ary tree based off of
 *         specifications and rules provided.This class builds the n-ary tree
 */

public class LinkedNaryTree<T> {
	NaryTreeNode<T> root; // variable to express the root of the tree

	public LinkedNaryTree() {
		root = null; // initializes the root to null
	}

	public LinkedNaryTree(NaryTreeNode<T> input) {
		root = input; // initializes the root the the NaryTreeNode<T> parameter
	}

	public void addNode(NaryTreeNode<T> child, NaryTreeNode<T> parent) {
		parent.addChild(child); // addChild() is called to add a child node to the parent node
	}

	public NaryTreeNode<T> getRoot() {
		return root; // root is returned
	}

	public T getRootElement() {
		return root.getData(); // the data element that is stored in the root is returned
	}

	// if the tree is empty, method will return false
	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else
			return false;
	}

	// method will calculate the number of elements in the tree
	public int size(NaryTreeNode<T> node) {
		if (node == null)
			return 0;

		// counter is initialized as one to account for the root
		int counter = 1;

		for (int i = 0; i < node.getNumChildren(); i++) {
			counter += size(node.getChild(i));
		}

		return counter;
	}

	// the tree is recursively traversed using preorder traversal
	public void preorder(NaryTreeNode<T> node, ArrayUnorderedList<T> list) {
		if (node == null)
			return;
		// each node is added to the rear
		list.addToRear(node.getData());
		for (int i = 0; i < node.getNumChildren(); i++)
			preorder(node.getChild(i), list);

	}

	// the iterator of the preordered list is returned
	public Iterator<T> iteratorPreorder() {
		ArrayUnorderedList<T> list = new ArrayUnorderedList<T>();
		preorder(root, list);
		return list.iterator();
	}

	// toString method returns a string with each of the preorder elements
	public String toString() {
		if (root == null) {
			return "Tree is empty.";
		} else
			return root.getData().toString();
	}

}
