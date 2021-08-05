/** The class takes the original array size and compresses it to remove 
 * duplicate integer values. It prints the value out in a triangle shaped array
 * 
 * @author Lisa Jia, Student Number 251140790
 * Completed: 02/05/2021
*/
public class CompressedArray {
	private int origArraySize;
	private double[] myArray;
	// Declares the private variables origArraySize and myArray
	
	public CompressedArray(double[][] originalArray) {
		int n = originalArray.length;
		int myArraylength = (n*n -n)/2; //Calculates the amount of elements in a new array
		this.origArraySize = n; //initializes origArraySize
		int index = 0; // initializes index  
		/**
		 * @param program takes the original array length to calculate 
		 * the new array size (original length*original length - diagonal)/2
		 */

		this.myArray = new double[myArraylength];
		for (int i = 0; i < originalArray.length; i++) {
			for (int j = 0; j < originalArray.length; j++) {
				if (i > j) {
					this.myArray[index] = originalArray[i][j];
					index++;
				// Elements are added for the new CompressedArray 
				}
			}
		} 
	}
		public int getLength() {
			int n = this.origArraySize;
			return (n*n -n) / 2;
			// Returns the length of the new CompressedArray
		}

		public double getElement(int index) {
			return this.myArray[index];
			// Returns an element of the new CompressedArray stored at a given element 
		}
		
		public boolean equals(CompressedArray other) {
			if (this.getLength() != other.getLength()) {
				return false; 
				// Compares the length of two CompressedArray objects  
			}
			else {
				for(int i=0; i < this.getLength(); i++) {
					if (this.getElement(i) != other.getElement(i)) {
						return false;
						// Compares the elements of two CompressedArrays stored at a given index
					}
				}
				return true;
				// If both length and elements are the same; returns true 
			}
		}
		
		public String toString() {
			String format = "\n";
			int index = 0;
			// Formats the CompressedArray in Triangular shape 
			
			for (int i = 1; i < origArraySize; i++) {
				for (int j = 0; j < origArraySize; j++) {
					if (i > j) {
						format += String.format("%8.2f", this.myArray[index]);
						index++;
					}
				}
				format += "\n";
			}
		return format;
	}
}


