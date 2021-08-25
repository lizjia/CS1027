/** The class sets the basic variables needed for the assignment
 * 
 * @author Lisa Jia
 * Completed 02/05/2021
*/
public class City {
	private String name; 
	private int x; 
	private int y; 
	private CityMarker marker; //Variable marker
	
	public City(String name, int x, int y) {
		this.name = name; 
		this.x = x;
		this.y =y;
		
		}
	/**
	 *  @param name; name of the city
	 *  @param x; Integer X coordinates of a city
	 *  @param y; Integer Y coordinates of a city
	 */
	
	// gets city name, the values of x and y, and the City marker 
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public CityMarker getMarker() {
		return marker;
	}
	
	//sets the City name, values of x and y, and the city marker 
	public void setName(String newName) {
		name = newName;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public void setMarker(CityMarker newMarker) {
		marker = newMarker;
	}
	// Returns the City Name 
	public String toString() {
		
		return name;
	}
}
