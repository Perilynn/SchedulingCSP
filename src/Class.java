import java.util.ArrayList;
import java.util.Stack;


public class Class {
	
	int fallPrice; //fall price of the class
	int springPrice; //spring price of the class
	int hours; //credit hours of the class
	int index;
	boolean taken;
	boolean hasPR; //has pre-reqs or not
	ArrayList<Integer> preReqs; //list of preReqs
	ArrayList<Integer> preReqsLeft;

	
	public Class(int fp, int sp, int h, int ind){
		fallPrice = fp;
		springPrice = sp;
		hours = h;
		preReqs=new ArrayList<Integer>();
		index = ind;
		taken = false;
		
	}
	
	public boolean hasPRleft(){
		return !preReqsLeft.isEmpty();
	}
	
	public String toString(){
		String prString = "";
		if(!hasPR){
			prString = "no PreReqs";
		}
		else{
			for(int i = 0; i < preReqs.size(); i++) {
				prString += preReqs.get(i) + " ";
			} 
		}
		 
		return "Index is " + index + ", Fall price is " + fallPrice + ", Spring price is " + springPrice + ", Hours are " + hours + ", PreReqs are " + prString;
	}
	
	public void resetPRLeft(){
		if(hasPR){
			preReqsLeft = new ArrayList<Integer>(preReqs);
			
		}
		else{
			preReqsLeft = new ArrayList<Integer>();
		}
	}
	


}
