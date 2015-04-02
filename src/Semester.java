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
		if(!classes.contains(c)){
			if(c.hours+hours<=CMax){
				if(!c.hasPRleft()){
					for(Class cloop : classes){
						if (c.preReqs.contains(cloop.index)) Simulation.sameAsPR = true;
					}
					if(Simulation.sameAsPR){
						return c;
					}
					classes.add(c);
					hours += c.hours;
					
					c.taken = true;
					return c;
				}
				else{
					int PR = c.preReqsLeft.get(0);
					if(!allClasses.get(PR-1).hasPRleft()){
	
						c.preReqsLeft.remove(0);
						
					}
					return add(allClasses.get(PR-1));
					
				}
			}
		}
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
