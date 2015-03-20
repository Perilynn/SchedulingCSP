import java.io.*;
import java.util.ArrayList;
public class Simulation {
	int N = -1; //Number of classes, changed in constructor
	int CMin; //Minimum credit hours
	int CMax; //Maximum credit hours
	int budget; //budget
	ArrayList<Class> classes; //ArrayList of classes
	ArrayList<Integer> intCourses; //contains the indices of the interesting courses
	
	public Simulation(String name) throws FileNotFoundException, IOException{
		classes = new ArrayList<Class>();
		intCourses = new ArrayList<Integer>();
		process(name); //sets N, CMin, CMax, budget, classes, intCourses
	}
	
	
	
	//You should ignore everything below this
	
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	public String toString(){
		String ret = "There are " + N + " Classes, CMin is " + CMin + ", CMax is " + CMax + ", Budget is " + budget + ", Classes are \n";
		if(classes.size() == 0){
			ret += "There are no classes.";
		}
		else{
			for(int i = 0; i < classes.size(); i++) {
				ret += "Class index is " + i + ", " + classes.get(i) + "\n";
			} 
		}
		ret += "Interesting courses are ";
		for(int i = 0; i < intCourses.size(); i++) {
			ret += intCourses.get(i) + " ";
		} 
		return ret;
	}
	//DO NOT TRY TO READ THIS IT'S TERRIBLE CODE THAT I WILL MAKE LOOK NICE LATER JUST ASSUME EVERYTHING WORKS BECAUSE IT DOES, EVERYTHING IS SET IN THE CONSTRUCTOR
	public void process(String name) throws FileNotFoundException, IOException{
		String line = "line";
		File file = new File(name + ".txt");
		if ( !file.exists() ){
		    System.out.println ("File does not exist");
		    return;
			
		}	
		BufferedReader br = new BufferedReader(new FileReader(name + ".txt"));
	    try {
	    	int j = 0;
	    	int k = 0;
	    	int l = 0;
	    	int listIndex1 = 0;
	    	int listIndex2 = 0;
	    	int PR = 0;
	    	int intClasses = 0;
	        StringBuilder sb = new StringBuilder();
	        line = br.readLine();
	        
	        int i = 0;
	        while (line != null) {
	        	if(i<N+1){
	        		if (line.charAt(1) == ' ') j = 1;
	        		else if (line.charAt(2) == ' ') j = 2;	        				        		
	        		else j = 3;
	        		if (line.charAt(j+2) == ' ') k = j+2;
	        		else if (line.charAt(j+3) == ' ') k = j+3;	        				        		
	        		else k = j+4;
	        		l = k+1;
	        		classes.add(new Class(Integer.parseInt(line.substring(0, j)), Integer.parseInt(line.substring(j+1, k)), Integer.parseInt(line.substring(l))));
	        		listIndex1++;
	        	}
	        	if(i>=N+1 && i<2*N+1){
	        		if(line.charAt(0) == '0'){
	        			classes.get(listIndex2).hasPR = false;
	        		}
	        		else{
	        			classes.get(listIndex2).hasPR = true;
	        			PR = Integer.parseInt(line.substring(0, 1));
	        			int a=0;
	        			int b=2;
	        			int c = b+1;
	        			for(a = 0, b = 2; a<PR-1; a++, b+=c-b+1){
	        				c = b+1;
	        				if(line.charAt(c) != ' '){
	        					c++;
	        				}
	        				classes.get(listIndex2).preReqs.add(Integer.parseInt(line.substring(b, c)));
	        			}
	        			classes.get(listIndex2).preReqs.add(Integer.parseInt(line.substring(b)));
	        			
	        		}
	        		//System.out.println(classes.get(listIndex2));
	        		listIndex2++;
	        	}
	        	if(i==2*N+1){
	        		int d = 1;
	        		if(line.charAt(1) != ' ') d = 2;
	        		intClasses = Integer.parseInt(line.substring(0, d));
	        		//System.out.println(intClasses);
	        		int a=0;
        			int b=d+1;
        			int c = b+1;
        			for(a = 0, b = d+1; a<intClasses-1; a++, b+=c-b+1){
        				c = b+1;
        				if(line.charAt(c) != ' '){
        					c++;
        				}
        				//System.out.println("adding " + Integer.parseInt(line.substring(b, c)));
        				intCourses.add(Integer.parseInt(line.substring(b, c)));
        			}
        			intCourses.add(Integer.parseInt(line.substring(b)));
	        	}
	        	if(i==2*N+2 && N != -1){
	        		budget = Integer.parseInt(line);
	        	}

//	            sb.append(line);
//	            sb.append(System.lineSeparator());
	            //System.out.println(line);
	            
	            i++;
	            if(i==1){
	            	N =  Integer.parseInt(line.substring(0, 2));
	            	CMin = Integer.parseInt(line.substring(3, 5));
	            	CMax = Integer.parseInt(line.substring(6));
	            	//System.out.println(N);
	            	//System.out.println(CMin);
	            	//System.out.println(CMax);
	            }
	            
	            line = br.readLine();

	        }
	    } finally {
	       br.close();
	    }
	    for(int i = 0; i < intCourses.size(); i++) {   
	        //System.out.println(intCourses.get(i));
	    }  
	    //System.out.println(budget);
		return;
		
	}
}
