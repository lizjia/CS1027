
/**
 * CS1027 ASSIGN 2
 * @author Lisa Jia
 * Student Number: 251140790
 * 
 * This class designs an algorithm to help cupid shoot targets given the provided limitations
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public class StartSearch {
	private Map targetMap;
	private int numArrows; // tracks number of arrows that cupid has
	private int inertia; // cannot turn after going in the same direction for three cells
	private int direction; // tracks the direction that the arrow moves in
	private int maxPathLength;

	public StartSearch(String filename) {
		/**
		 * constructor imports the file
		 * 
		 * @param the name of the file
		 */
		try {
			targetMap = new Map(filename);
			// exceptions are printed if the file is not found or if the map is not valid
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, the file is not found");
		} catch (IOException e) {
			System.out.println("Sorry the file is not found");
		} catch (InvalidMapException e) {
			System.out.println("Map Invalid, please enter valid map.");
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file.");
			System.exit(0);
		}

		String mapFileName = args[0];

		StartSearch s = new StartSearch(args[0]);

		// sets the maxPathLength to any reasonably large number
		s.maxPathLength = 9999999;
		// retrieves the quiverSize from target Map
		s.numArrows = s.targetMap.quiverSize();

		if (args.length >= 2) {
			s.maxPathLength = Integer.parseInt(args[1]);
		}
		if (args.length >= 3) {
			s.numArrows = Integer.parseInt(args[2]);
		}
		// a new method is called to push and pop to the stack
		s.performSearch();

	}

	public void performSearch() {
		// initializes pathLength and targetsFound
		ArrayStack<MapCell> newStack = new ArrayStack<>();
		int targetsFound = 0;

		try {
			while (numArrows > 0) {
				MapCell startCell = targetMap.getStart();

				// start cell is pushed and then marked in the array stack
				newStack.push(startCell);
				startCell.markInStack();
				// initializes direction to negative 1; 0-3 is a direction
				direction = -1;
				inertia = 0;
				int pathLength = 0;
				int backtracks = 0;

				while (!newStack.isEmpty() && numArrows > 0 && pathLength <= maxPathLength && backtracks < 3) {
					MapCell currentCell = newStack.peek();
					MapCell nextCell = nextCell(currentCell);

					if (nextCell != null) {
						// if the next cell isn't null, the arrow is pushed into the next stack and
						// marked
						newStack.push(nextCell);
						nextCell.markInStack();
						pathLength++;

						if (nextCell.isTarget()) {
							// if the next cell is the target, arrow travels towards target
							targetsFound++;
							break;
						}

					} else {
						if (!newStack.isEmpty()) {
							// there are no cells found; backtracks to reevaluate
							newStack.pop().markOutStack();
							backtracks++;
							direction = -1;
							pathLength++;
						}
					}
				}
				reset(newStack);
				numArrows--;
			}
			reset(newStack);
			System.out.println("Cupid found " + targetsFound + " targets!");
			
		} catch (InvalidMapException e) {
			System.out.println("Map not valid");
		}
	}
	// new method is called
	public void reset(ArrayStack<MapCell> pathStack) {
		while (!pathStack.isEmpty()) {
			pathStack.pop().markOutStack();
		}
	}

	/**
	 * @param cell method finds the path to the best cell according to certain
	 *             limitations
	 */

	public MapCell nextCell(MapCell cell) {
		// variables are initialized
		MapCell currentCell = cell;
		MapCell nextCell = currentCell;
		int i;

		// 0-3 represents the north, east,south, west
		if (direction >= 0 && direction < 4) {
			nextCell = currentCell.getNeighbour(direction);
			// is the next cell is the target and is it not marked nor null, the next cell
			// is marked in stack
			if (nextCell != null && nextCell.isTarget() && !nextCell.isMarkedInStack() && !nextCell.isMarkedOutStack()) {
				inertia++;
				return nextCell;
			}
			// the next list of importance is a crossPath; if it not marked nor null, the
			// next cell is marked in stack
			if (nextCell != null && nextCell.isCrossPath() && !nextCell.isMarkedInStack()
					&& !nextCell.isMarkedOutStack()) {
				inertia++;
				return nextCell;
			}

			// the next order of importance is a vertical path; if it is not marked nor
			// null, the next cell is marked in stack
			if (nextCell != null && nextCell.isVerticalPath() && (direction == 0 || direction == 2)
					&& !nextCell.isMarkedInStack() && !nextCell.isMarkedOutStack()) {
				inertia++;
				return nextCell;
			}

			// the last order of importance is horizontal path; if it is not marked nor
			// null, the next cell is marked in stack
			if (nextCell != null && nextCell.isHorizontalPath() && (direction == 1 || direction == 3)
					&& !nextCell.isMarkedInStack() && !nextCell.isMarkedOutStack()) {
				inertia++;
				return nextCell;
			}
		}
		// inertia can't be greater than three, if it is null is returned
		if (inertia >= 3)
			return null;
		else
			inertia = 0;

		if (currentCell.isStart() || currentCell.isCrossPath()) {
			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				// loops through cells to find the target cell
				if (nextCell != null && nextCell.isTarget() && !nextCell.isMarkedInStack()
						&& !nextCell.isMarkedOutStack()) {
					direction = i;
					return nextCell;
				}
			}

			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				// loops through cells to find the crossPath
				if (nextCell != null && nextCell.isCrossPath() && !nextCell.isMarkedInStack()
						&& !nextCell.isMarkedOutStack()) {
					direction = i;
					return nextCell;
				}
			}

			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				if (i == 0 || i == 2) {
					// if the direction is north or south;loops through the cell to find a vertical
					if (nextCell != null && nextCell.isVerticalPath() && !nextCell.isMarkedInStack()
							&& !nextCell.isMarkedOutStack()) {
						direction = i;
						return nextCell;
					}
				} else {
					// otherwise it loops through the cell to see if there is a horizontal
					if (nextCell != null && nextCell.isHorizontalPath() && !nextCell.isMarkedInStack()
							&& !nextCell.isMarkedOutStack()) {
						direction = i;
						return nextCell;
					}
				}
			}

		}

		if (currentCell.isHorizontalPath()) {
			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				if (i == 1 || i == 3) {
					// if the direction is west or east, the cell traverses to find if there is a
					// horizontal path
					// if it is a target it is marked in stack.
					if (nextCell != null && nextCell.isTarget() && !nextCell.isMarkedInStack()
							&& !nextCell.isMarkedOutStack()) {
						direction = i;
						return nextCell;
					}
				}
			}

			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				if (i == 1 || i == 3) {
					// if the direction is west or east, the cell traverses to find if there is a
					// horizontal path
					// if it is a crossPath cell, it is marked in stack.
					if (nextCell != null && nextCell.isCrossPath() && !nextCell.isMarkedInStack()
							&& !nextCell.isMarkedOutStack()) {
						direction = i;
						return nextCell;
					}
				}
			}

			for (i = 0; i < 4; i++) {
				nextCell = currentCell.getNeighbour(i);
				// if the direction is west or east, the cell traverses to find if there is a
				// horizontal path
				// if it is a horizontalPath, it is marked in stack.
				if (i == 1 || i == 3) {
					if (nextCell != null && nextCell.isHorizontalPath() && !nextCell.isMarkedInStack()
							&& !nextCell.isMarkedOutStack()) {
						direction = i;
						return nextCell;
					}
				}
			}

			if (currentCell.isVerticalPath()) {
				for (i = 0; i < 4; i++) {
					nextCell = currentCell.getNeighbour(i);
					// if the direction is west or east, the cell traverses to find if there is a
					// horizontal path
					// if it is a target it is marked in stack.
					if (i == 0 || i == 2) {
						if (nextCell != null && nextCell.isTarget() && !nextCell.isMarkedInStack()
								&& !nextCell.isMarkedOutStack()) {
							direction = i;
							return nextCell;
						}
					}
				}

				for (i = 0; i < 4; i++) {
					nextCell = currentCell.getNeighbour(i);
					// if the direction is west or east, the cell traverses to find if there is a
					// horizontal path
					// if it is a crossPath it is marked in stack.
					if (i == 0 || i == 2) {
						if (nextCell != null && nextCell.isCrossPath() && !nextCell.isMarkedInStack()
								&& !nextCell.isMarkedOutStack()) {
							direction = i;
							return nextCell;
						}
					}
				}

				for (i = 0; i < 4; i++) {
					nextCell = currentCell.getNeighbour(i);
					// if the direction is west or east, the cell traverses to find if there is a
					// horizontal path
					// if it is a verticalPath it is marked in stack.
					if (i == 0 || i == 2) {
						if (nextCell != null && nextCell.isVerticalPath() && !nextCell.isMarkedInStack()
								&& !nextCell.isMarkedOutStack()) {
							direction = i;
							return nextCell;
						}
					}
				}
			}
		}
		return null;
	}
}
