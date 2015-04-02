import java.util.ArrayList;


public class Semester {
	static int currentIndex;
	ArrayList<Class> allClasses;
	ArrayList<Class> classes;
	int index;
	int hours;
	int CMin; //Minimum credit hours
	int CMax; //Maximum credit hours
	public Semester(int min, int max, ArrayList<Class> AC, int i){
		allClasses = AC;
		classes = new ArrayList<Class>();
		hours = 0;
		CMin = min;
		CMax = max;
		index = i;
	}
	
	public int cost(){
		int cost = 0;
		for(Class c : classes){
			if(index%2 == 1){
				cost+=c.fallPrice;
			}
			else{
				cost+=c.springPrice;
			}
		}
		return cost;
	}
	
	public Class add(Class c){
		currentIndex = index;
		//System.out.println("attempting 1 to add " + c.index);
		//System.out.println(currentIndex);
		//System.out.println(c.hasPRleft());
		if(!classes.contains(c)){
			//System.out.println("attempting 2 to add " + c.index);
			if(c.hours+hours<=CMax){
				//System.out.println("attempting 3 to add " + c.index);
				if(!c.hasPRleft()){
					//System.out.println("attempting 4 to add " + c.index);
					for(Class cloop : classes){
						if (c.preReqs.contains(cloop.index)) Simulation.sameAsPR = true;
					}
					if(Simulation.sameAsPR){
						//System.out.println("same as PR");
						//System.out.println("returning " + c.index + " without adding");
						return c;
					}
					classes.add(c);
					//System.out.println("added " + c.index + " from Semester");
					hours += c.hours;
					
					c.taken = true;
					//System.out.println("returning " + c);
					return c;
				}
				else{
					int PR = c.preReqsLeft.get(0);
					if(!allClasses.get(PR-1).hasPRleft()){
	
						c.preReqsLeft.remove(0);
						
					}
					//System.out.println("returning add(" + allClasses.get(PR-1) + ")");
					return add(allClasses.get(PR-1));
					
				}
			}
		}
		//System.out.println("returning null");
		return null;
	}
	
	public boolean satisfied(){
		return (hours > CMin);
	}
	
	public String toString(){
//		String str1 = "Hours are " + hours + ", Number of classes: " + classes.size() + ", Classes:\n";
//		for (Class c : classes){
//			str1 += c + "\n";
//		}
		String str1 = "" + classes.size();
		for (Class c : classes){
			str1+= " " + c.index;
		}
		return str1;
		
	}
}
