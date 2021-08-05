/** This Class acts as the heart of the program as it reads in files of cities and creates objects 
 * for each of them. It contains arrays of those given cities and contains a CompressedArray containing
 * the distance between them
 * 
 * @author Lisa Jia; Student Number 251140790
 * Completed on: 02/05/2021
 *
 */

public class Program {
	private int cityCount;
	private City[] cityArray;
	private CompressedArray array;
	// Private variables are defined 

	public Program(String fileName, boolean showMap) {
		this.cityArray = new City[3]; //Initializes cityArray with three cells 
		this.cityCount = 0;

		  MyFileReader fileReader = new MyFileReader(fileName);
		  
		  fileReader.readString();
		  // Each string in the file is read 
		  
		  while (fileReader.endOfFile() == false) {
		  // Iterates through the program until the end of the file is reached 
			  
		  String cityName = fileReader.readString(); int x = fileReader.readInt(); int
		  y = fileReader.readInt();
		  
		  // File is read in with MyFileReader and file is loaded
		  
		  City city = new City(cityName, x, y);
		  // City object is created containing the city name and it's X and Y coordinates
		  
		  if (this.cityCount == this.cityArray.length) { 
			  this.expandCapacity();
			  }
		  // If the cityCount reaches the length of the array, the capacity of the array is expanded 
		  // Calls the method expandCapacity 
		  
		  this.cityArray[this.cityCount] = city; this.cityCount++; }

		if (showMap) {
			Map display = new Map();
			for (int i = 0; i < this.cityCount; i++) {
				display.addCity(this.cityArray[i]);
				// If the boolean showMap is trye, a marker icon is added to the map for each city
			}
		}
	}

	public City[] getCityList() {
		return this.cityArray;
	// Method returns cityArray

	}

	private void expandCapacity() {
		City[] addedArray = new City[this.cityCount + 3];
		// Expands the capacity of the Array by adding 3 additional slots to the array

		for (int i = 0; i < this.cityCount; i++)
			addedArray[i] = this.cityArray[i];

		this.cityArray = addedArray;

	}

	public double distBetweenCities(City city1, City city2) {
		double yCordDistance = Math.abs(city2.getY() - city1.getY());
		double xCordDistance = Math.abs(city2.getX() - city1.getX());

		return Math.hypot(yCordDistance, xCordDistance);
		// Calculates the Euclidean distance between two cities; pythogrean theorem 

	}

	public void compareDistances() {
		double[][] newCityArray = new double[this.cityCount][this.cityCount];

		for (int i = 0; i < this.cityCount; i++) {
			for (int j = 0; j < this.cityCount; j++) {
				newCityArray[i][j] = distBetweenCities(this.cityArray[i], this.cityArray[j]);
			}
			// Loops through every combination of city paires and calls distBetweenCities() for each pair
		}
		this.array = new CompressedArray(newCityArray);
	}

	public CompressedArray getArray() {
		return array;
	}

	public static void main(String[] args) {
		Program prog = new Program(args[0], true);
		// Main program 
	}
}
