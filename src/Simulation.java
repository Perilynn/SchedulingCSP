import java.io.*;
import java.util.ArrayList;
public class Simulation {

	static boolean sameAsPR = false;
	boolean cont = false;
	
	
	int N = -1; //Number of classes, changed in constructor
	int CMin; //Minimum credit hours
	int CMax; //Maximum credit hours
	int budget; //budget
	int cost;
	int semIterator;
	ArrayList<Class> classes; //ArrayList of classes
	ArrayList<Integer> intCourses; //contains the indices of the interesting courses
	ArrayList<Integer> intCoursesLeft;
	ArrayList<Integer> coursesLeft;
	ArrayList<Integer> coursesTaken;
	ArrayList<Semester> semesters;
	int maxPreReqs = 0;
	Integer tracker;
	
	public Simulation(String name) throws FileNotFoundException, IOException{
		cost = 0;
		classes = new ArrayList<Class>();
		intCourses = new ArrayList<Integer>();		
		coursesTaken = new ArrayList<Integer>();
		coursesLeft = new ArrayList<Integer>();
		semesters = new ArrayList<Semester>();				
		
		processClasses(name); //sets N, CMin, CMax, budget, classes, intCourses
		
		intCoursesLeft = new ArrayList<Integer>(intCourses);		
		for(Class c : classes){
			coursesLeft.add(c.index);
		}
		
		addPRs(maxPreReqs);
				
		schedule();
		
		
		
//		System.out.println("Courses left after satisfying");
//		for(Integer left : coursesLeft){
//			System.out.print(left + " ");
//		}
	}
	
	public int cost(){
		int cost = 0;
		for(Semester s : semesters){
			cost+=s.cost();
		}
		return cost;
	}
	
	public void schedule(){
		boolean keepGoing = false;
//		intCourses.add(7);
//		intCoursesLeft.add(7);
//		intCourses = new ArrayList<Integer>();
//		intCourses.add(7);
//		intCoursesLeft = new ArrayList<Integer>();
//		intCoursesLeft.add(7);
		while(!intCoursesLeft.isEmpty()){
			//System.out.println(classes.get(intCoursesLeft.get(0)-1));
			add(classes.get(intCoursesLeft.get(0)-1));			
		}
		
//		for(Semester s : semesters){
//			System.out.println(s);
//		}
		
//		System.out.println("Courses left before satisfying");
//		for(Integer left : coursesLeft){
//			System.out.print(left + " ");
//		}
//		System.out.println();
		
		for(Semester s : semesters){
//			if(s.hours < CMin){
//				for(Integer i : coursesLeft){
//					if (classes.get(i-1).hours + s.hours <= CMax){
//						add(classes.get(i-1));
//					}
//					if(s.hours>CMin) break;
//				}
//			}
			
			
			if(!s.satisfied()){
				//System.out.println("This semester is not currently satisfied:\n" + s);
				//System.out.println("Print #1 " + classes.get(coursesLeft.get(0)-1));
				if(!coursesLeft.isEmpty()) keepGoing = add(classes.get(coursesLeft.get(0)-1), s);
				while(!coursesLeft.isEmpty() && keepGoing){
					//System.out.println("Print #2 " + classes.get(coursesLeft.get(0)-1));
					keepGoing = add(classes.get(coursesLeft.get(0)-1), s);
				}
			}
			
			
		}
	}
	
	public void addPRs(int count){
		for(int i = 0; i < count; i++){
			addPRs();
		}
	}
	
	public void addPRs(){
		ArrayList<Integer> added;
		for(Class c : classes){
			added = new ArrayList<Integer>();
			for(Integer j : c.preReqsLeft){
				for(Integer k : classes.get((int) j-1).preReqsLeft){
					if(!c.preReqsLeft.contains(classes.get((int) k-1))){
						//c.preReqsLeft.add(k);
						added.add(k);
					}
				}
			}
			for(Integer i : added){
				if(!c.preReqsLeft.contains(i)){
					c.preReqsLeft.add(i);
				}
			}
		}
	}
	
	public void add(Class c){
		if(semesters.isEmpty()){
			semesters.add(new Semester(CMin, CMax, classes, 0));
			semIterator = 0;
		}
		Class c1 = semesters.get(semIterator).add(c);
		//System.out.println(c1);
		
		if(c1 == null){
			if(semesters.size() <= semIterator+1) semesters.add(new Semester(CMin, CMax, classes, semIterator+1));
			semIterator++;
			c1 = semesters.get(semIterator).add(c);
			//System.out.println(c1);
		}	
		if (sameAsPR){
			//System.out.println("Same as PR triggered");
			sameAsPR=false;
			if(semesters.size() <= (Semester.currentIndex+1)) semesters.add(new Semester(CMin, CMax, classes, Semester.currentIndex+1));
			c1 = semesters.get(Semester.currentIndex+1).add(c1);
			
			
		}
		
		if(c1 != null){
			coursesTaken.add(c1.index);
			tracker = new Integer(c1.index);
			coursesLeft.remove(tracker);
			intCoursesLeft.remove(tracker);
		}
		
//		if(semesters.get(semIterator).add(c)){
//			
//		}
	}
	
	public boolean add(Class c, Semester s){
		if (sameAsPR){
			//System.out.println("Same as PR triggered");
			sameAsPR=false;
			
			
			
		}
		//System.out.println("adding " + c.index + " to Semester " + s.index);
		if(c.hours+s.hours > CMax){
			//System.out.println("c.hours+s.hours > CMax");
			return false;
		}
		if(s.hours >= CMin){
			//System.out.println("s.hours >= CMin");
			return false;
		}
		
		for(Integer taken : coursesTaken){
			if(c.preReqsLeft.contains(taken)) c.preReqsLeft.remove(taken);
		}
		
		Class c1 = s.add(c);
		
		if(c1!=null){
			//System.out.println("added " + c1.index);
			coursesTaken.add(c1.index);
			tracker = new Integer(c1.index);
			coursesLeft.remove(tracker);
			intCoursesLeft.remove(tracker);
		}
		
		
		//System.out.println("hours are now " + s.hours);
		if(s.hours >= CMin){
			//System.out.println("s.hours >= CMin");
			return false;
		}
		
		return true;
	}
	
	
	//You should ignore everything below this
	
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	public String toString(){
		
		String ret = cost() + " " + semesters.size() + "\n";
//		ret = "There are " + N + " Classes, CMin is " + CMin + ", CMax is " + CMax + ", Budget is " + budget + ", Classes are \n";
//		if(classes.size() == 0){
//			ret += "There are no classes.";
//		}
//		else{
//			for(int i = 0; i < classes.size(); i++) {
//				ret += "Class index is " + i + ", " + classes.get(i) + "\n";
//			} 
//		}
//		ret += "Interesting courses are ";
//		for(int i = 0; i < intCourses.size(); i++) {
//			ret += intCourses.get(i) + " ";
//		}
		
		for(Semester s : semesters){
			ret += s + "\n";
		}
		
		for(Semester s : semesters){
			ret += s.cost() + " ";
		}
		return ret;
	}
	//DO NOT TRY TO READ THIS IT'S TERRIBLE CODE THAT I WILL MAKE LOOK NICE LATER JUST ASSUME EVERYTHING WORKS BECAUSE IT DOES, EVERYTHING IS SET IN THE CONSTRUCTOR
	public void processClasses(String name) throws FileNotFoundException, IOException{
		String line = "line";
		File file = new File(name + ".txt");
		if ( !file.exists() ){
		    //System.out.println ("File does not exist");
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
	        		classes.add(new Class(Integer.parseInt(line.substring(0, j)), Integer.parseInt(line.substring(j+1, k)), Integer.parseInt(line.substring(l)), listIndex1+1));
	        		listIndex1++;
	        	}
	        	if(i>=N+1 && i<2*N+1){
	        		if(line.charAt(0) == '0'){
	        			classes.get(listIndex2).hasPR = false;
	        		}
	        		else{
	        			classes.get(listIndex2).hasPR = true;
	        			PR = Integer.parseInt(line.substring(0, 1));
	        			if(PR>maxPreReqs) maxPreReqs = PR;
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
	        		classes.get(listIndex2).resetPRLeft();
	        		listIndex2++;
	        	}
	        	if(i==2*N+1){
	        		int d = 1;
	        		if(line.charAt(1) != ' ') d = 2;
	        		intClasses = Integer.parseInt(line.substring(0, d));
	        		int a=0;
        			int b=d+1;
        			int c = b+1;
        			for(a = 0, b = d+1; a<intClasses-1; a++, b+=c-b+1){
        				c = b+1;
        				if(line.charAt(c) != ' '){
        					c++;
        				}
        				intCourses.add(Integer.parseInt(line.substring(b, c)));
        			}
        			intCourses.add(Integer.parseInt(line.substring(b)));
	        	}
	        	if(i==2*N+2 && N != -1){
	        		budget = Integer.parseInt(line);
	        	}


	            
	            i++;
	            if(i==1){
	            	N =  Integer.parseInt(line.substring(0, 2));
	            	CMin = Integer.parseInt(line.substring(3, 5));
	            	CMax = Integer.parseInt(line.substring(6));

	            }
	            
	            line = br.readLine();

	        }
	    } finally {
	       br.close();
	    }
 
		return;
		
	}
}
