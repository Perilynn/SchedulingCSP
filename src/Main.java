import java.io.*;
public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		Simulation s1 = new Simulation("firstScenario");
		Simulation s2 = new Simulation("secondScenario");
		Simulation s3 = new Simulation("thirdScenario");
		Simulation s4 = new Simulation("fourthScenario");
		
		
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		
	}
	
	

}
