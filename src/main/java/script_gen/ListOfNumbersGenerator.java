package script_gen;

public class ListOfNumbersGenerator {
	
	public static void main (String [ ] args) {

		String result = "[ ";		
		
		for (int id_reg = 1; id_reg < 200; id_reg++) {
			
			System.out.println("Doing reg " + id_reg + " ...");
			
			result = result + id_reg + ", ";
		}

		result = result + "200 ]";
		
		System.out.println(result);
	}

}
