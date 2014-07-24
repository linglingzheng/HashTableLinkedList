package WebTrackerAlgorithm;

/**
 * @author linglingzheng
 * create IntNode class to pass UserID, TimeStamp, ActivityCount (as integers) to the same node
 */

public class IntNode {
	
	public int user;
	public int time;
	public int count;
	
	public IntNode next;
	
	public IntNode(int user, int time, int count, IntNode next){
		this.user = user;
		this.time = time;
		this.count = count;
	}

}
