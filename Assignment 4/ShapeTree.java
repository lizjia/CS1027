/**
 * @author Lisa Jia
 * @author Student ID: 251140790
 * 
 *         Date Completed: 04/06/2021
 * 
 *         CS1027 ASSIGN 4:This programs builds a n-ary tree based off of
 *         specifications and rules provided.This class builds the tree
 *         according the the provided specifications
 */
public class ShapeTree {
	private LinkedNaryTree<Shape> tree; // private variable is the n-ary tree

	public LinkedNaryTree<Shape> getTree() {
		return tree; // the tree is returned
	}

	public NaryTreeNode<Shape> getRoot() {
		return tree.getRoot(); // the root of the tree is returned
	}

	// adds shapes to the tree
	public void addShapeNode(Shape shape) {
		NaryTreeNode<Shape> node = new NaryTreeNode<Shape>(shape);
		// if the tree is empty, the root is set as the new node
		if (tree == null) {
			tree = new LinkedNaryTree<Shape>(node);
		} else {
			// addShapeNodeHelper is called to determine if there is a suitable place to add
			// a new node
			if (addShapeNodeHelper(shape) != null)
				// if there is a suitable place, the node is added to that place
				addShapeNodeHelper(shape).addChild(node);
		}

	}

	// a stack- based preorder traversal is performed on the tree
	public NaryTreeNode<Shape> addShapeNodeHelper(Shape shape) {
		// root variable is created
		NaryTreeNode<Shape> root = tree.getRoot();
		if (root == null) {
			return null;
		}
		ArrayStack S = new ArrayStack();
		S.push(root);
		// a new stack is created and new nodes are pushed in that stack
		while (!S.isEmpty()) {
			// each time the node is visited, the node will be popped
			NaryTreeNode<Shape> v = (NaryTreeNode<Shape>) S.pop();
			// checkNode method is called to determine whether or not the node can be added
			if (checkNode(v, shape) == true) {
				return v;
			}
			for (int i = v.getNumChildren() - 1; i >= 0; i--) {
				// performs a preorder traversal and pushes it to the stack
				if (v.getChild(i) != null) {
					S.push(v.getChild(i));
				}
			}
		}
		return null;
	}

	// method checks if the shape can be added to the node
	public boolean checkNode(NaryTreeNode<Shape> node, Shape shape) {
		// rule 1: a node can have up to x number of children where x is the number of
		// sides in the shape
		if (node.getNumChildren() >= node.getData().getNumSides()) {
			return false;
		}
		// rule 2: a child node can't be the same colour as the parent node 
		if (node.getData().getColour().equals(shape.getColour())) {
			return false;
		}
		// rule 3: sibling nodes cannot be the same colour
		for (int i = 0; i < node.getNumChildren(); i++) {
			if (node.getChild(i).getData().getColour().equals(shape.getColour())) {
				return false;
			}
		}
		return true;
	}

	// toString is returned 
	public String toString() {
		return tree.iteratorPreorder().toString();
	}

}
