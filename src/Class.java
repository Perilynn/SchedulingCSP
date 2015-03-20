import java.util.ArrayList;


public class Class {
	
	int fallPrice; //fall price of the class
	int springPrice; //spring price of the class
	int hours; //credit hours of the class
	boolean hasPR; //has pre-reqs or not
	ArrayList<Integer> preReqs; //list of preReqs
	
	public Class(){
		fallPrice = 0;
		springPrice = 0;
		hours = 0;
		preReqs=new ArrayList<Integer>();
	}
	public Class(int fp, int sp, int h){
		fallPrice = fp;
		springPrice = sp;
		hours = h;
		preReqs=new ArrayList<Integer>();
	}
	
	public String toString(){
		String prString = "";
		if(preReqs.size() == 0){
			prString = "no PreReqs";
		}
		else{
			for(int i = 0; i < preReqs.size(); i++) {
				prString += preReqs.get(i) + " ";
			} 
		}
		 
		return "Fall price is " + fallPrice + " Spring price is " + springPrice + " Hours are " + hours + ", PreReqs are " + prString;
	}
}
