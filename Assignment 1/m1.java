
public class m1 {

	public void m1() {
		// TODO Auto-generated constructor stub
		try {
			int a = 7;
			int b= 3;
			int[] c = new int[a];
			int[] d = new int[] {5,2,3};
			for (int i=0; i < a; i++) {
				if (i >= b)
					return;
				c[i] = d[b-i-1];
			
		}
		System.out.print("A");
		
	}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.print("B");
		}
		catch (NullPointerException e) {
			System.out.print("C");
		}
		catch (Exception e) {
			System.out.print("D");
		}
		finally {
			System.out.print("E");
		}

}
}
