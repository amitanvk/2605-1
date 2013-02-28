import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Main main = new Main();
		Scanner scan = new Scanner(System.in);
		System.out.println("Pinball Simulator.");
		boolean quit = true;
		while(quit){
			System.out.println("Please enter the triangle side length:");
			double s = scan.nextDouble();
			System.out.println("Please enter the circle radius:");
			double r = scan.nextDouble();
			System.out.println("Use number selection for choice. Custom angle (x<0), random angles (x=0), or step through angles (x>0)?");
			int x = scan.nextInt();
			if(x<0){
				System.out.println("Please select an angle between 0 and 2pi.");
				double theta = scan.nextDouble();
				if(theta>2*Math.PI||theta<0){
					throw new IllegalArgumentException();
				}
				System.out.println("Angle: " + theta);
				System.out.println("Hits: " + main.singlerun(theta, r, s));
				System.out.println("Restarting program.");
				}

			else{
				System.out.println("Indicate number of iterations of angles between 0 an 2PI:" );
				int iterations = scan.nextInt();
				double steps =  (2 * Math.PI) / iterations;
				double[][] results = new double[iterations][3];
				int[] hits = new int[1000];
				int highest = 0;
				Random random = new Random();
				double highTheta = 0;
				double angle = 0;
				for(int i = 0; i < iterations; i ++){
					angle = (x == 0) ? random.nextDouble() * 2 * Math.PI : angle + steps;
					 int hit = main.run(angle,r,s);
					 results[(int)i][0] = angle;
					 results[(int)i][1] = hit;
					 hits[hit]++;
					 if(hit>highest){
						 highest = hit;
						 highTheta = angle;
					 }

				}
				int sum = 0;
				for(int i = 0; i < 1000; i++)
				{
					sum += hits[i];
				}
				for(int i=0;i<steps;i++){
					double check = results[i][1];
					if(check>0){
						System.out.println(results[i][1]);
					}
				}
				for(int i=0;i<1000;i++){
					if(hits[i] != 0)
					{
						System.out.println(i + ": " + hits[i] + " - " + (double)(100 * (double)hits[i] / (double)sum) + "%");
					}
				}
				
				System.out.println(highest);
				System.out.println(highTheta);

			}
			System.out.println("Quit? Y/N");
			String select = scan.next();
			if(select.equals("y")||select.equals("Y")){
				quit = false;
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Main() {
	}
	
	public double intersection(Vector v, Vector x, Vector c, double r){
		Vector cSubtractX = c.minus(x);
		double f = v.dot(cSubtractX);
		double D = Math.pow(f, 2.0) - (cSubtractX).dot(cSubtractX) + Math.pow(r, 2.0);
		if(D<=0){
			return -1.0;
		}
		else{
			return f-Math.sqrt(D);
		}
	}
	
	public Vector reflection(Vector v, Vector c, Vector x){
		Vector cSubtractX = c.minus(x);
		double cxcx = cSubtractX.dot(cSubtractX);
		Vector twoV = v.scale(2);
		return v.minus(cSubtractX.scale((twoV.dot(cSubtractX))/cxcx));
	}
	
	public int run(double theta, double r,double s){
		int hits = 0;
		int stop = 0;
		double t = 0;
		TStruct tstruct = new TStruct(theta);
		Vector x = new Vector(0,0);
		Vector v = new Vector(Math.cos(theta),Math.sin(theta));
		Vector[] circles = new Vector[3];
		circles[0] = new Vector(s/2,-s*Math.sqrt(3)/6);
		circles[1] = new Vector(-s/2,-s*Math.sqrt(3)/6);
		circles[2] = new Vector(0,s*Math.sqrt(3)/3);
		int currentCircle = -1;
		while(stop<3){
			stop = 0;
			for(int i=0;i<3;i++){
				if(currentCircle ==i){
					stop++;
					continue;
				}
				else{
					t = intersection(v, x, circles[i], r);
				}
				if(t<0){
					stop++;
				}
				else{
					tstruct.addC(i, hits);
					hits++;
					currentCircle = i;
					x = x.add(v.scale(t));
					v =  reflection(v, circles[i], x);
				}
			}
		}
		tstruct.addHits(hits);
		if(hits>6){
			tstruct.print();
		}
		return hits;
	}
	
	public int singlerun(double theta, double r,double s){
		int hits = 0;
		int stop = 0;
		double t = 0;
		Vector x = new Vector(0,0);
		Vector v = new Vector(Math.cos(theta),Math.sin(theta));
		Vector[] circles = new Vector[3];
		circles[0] = new Vector(s/2,-s*Math.sqrt(3)/6);
		circles[1] = new Vector(-s/2,-s*Math.sqrt(3)/6);
		circles[2] = new Vector(0,s*Math.sqrt(3)/3);
		TStruct tstruct = new TStruct(theta);
		int currentCircle = -1;
		System.out.println("High run. Angle: " + theta);
		System.out.println();
		while(stop<3){
			stop = 0;
			for(int i=0;i<3;i++){
				if(currentCircle ==i){
					stop++;
					continue;
				}
				else{
					t = intersection(v, x, circles[i], r);
				}
				if(t<0){
					stop++;
				}
				else{
					tstruct.addC(i, hits);
					hits++;
					currentCircle = i;
					System.out.print(i);
					x = x.add(v.scale(t));
					v =  reflection(v, circles[i], x);
				}
			}
		}
		tstruct.addHits(hits);
		tstruct.print();
		return hits;
	}

}
