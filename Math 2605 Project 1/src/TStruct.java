
public class TStruct {
private double theta;
private int hits;
private int[] circlearray;

	public TStruct(double theta){
		this.theta = theta;
		circlearray = new int[30];
	}
	public void addC(int circle, int hit){
		circlearray[hit] = circle;
	}
	public void print(){
			System.out.println(theta + " " + hits+ " ");
	        for (int i =0;i<15;i++ ){
	        	System.out.print(circlearray[i]);
	        }
        	System.out.println();

	}
	public void addHits(int hits){
		this.hits = hits;
	}
}
