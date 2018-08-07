import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import aicapn.AiCapn;
import aicapn.bot.Bot;
import aicapn.exceptions.OutOfThisWorldException;
//import java.lang.Math.sqrt;
public class MyAgent extends Bot
{
	
    public static void main(String[] args)
    {
    	// Create the AiCapn game object
        AiCapn game=new AiCapn();
        // Attach your agent to the game
        game.attach(new MyAgent());
        game.begin();
    }
    
    public MyAgent()
    {
    	super();
    	// assigns the name of your agent
    	name = "My Agent";
    }
    public double heuristic_value (Point innital , Point goal) {
    	
    	double xinnital = innital.x;
    	double yinnital = innital.y;

    	double xgoal = goal.x;
    	double ygoal = goal.y;
    	
    	
    	
    	return Math.sqrt((xinnital - xgoal)*(xinnital - xgoal) + (yinnital - ygoal)*(yinnital - ygoal));        
    }
    
    public Point bestheuristic_piont (ArrayList<Double> comparevalue,HashMap<Double, Point> getPoint ) {
    	
    	
    	Point best= null;
    	Collections.sort(comparevalue);
    	double value= comparevalue.get(0);
    	
    	for(java.util.Map.Entry<Double, Point> entry : getPoint.entrySet()){
    	    if (entry.getKey() == value )
    	    {
    	    	best= entry.getValue();
    	  }
    
    	}
    	
    	return best;        
    }
 public double getFn (double value ,Point s, HashMap<Double, Point> getCost) {
    	
	 
	 double best = 0.0;
	 for(java.util.Map.Entry<Double, Point> entry : getCost.entrySet()){
 	    if (entry.getValue() == s )
 	    {
 	    	best= entry.getKey() + value;
 	  }
 
 	}
    	 return best;    
    }
    /**
     * Retrieves the path from a start to and end location
     * @param start x and y coordinate of starting location represented as a Point object
     * @param end x and y coordinate of end location represented as a Point object
     * @return path from the start to end location represented as an ArrayList of Point objects
     */
    public ArrayList<Point> getPath(Point start, Point end)
    {
    	
    	ArrayList<Point> open = new ArrayList<>();
    	ArrayList<Point> closed = new ArrayList<>();

    	ArrayList<Double> hValue = new ArrayList<>();
    	ArrayList<Point> bestpath = new ArrayList<>();
        HashMap<Double, Point> inheritance = new HashMap<>();
        HashMap<Double, Point> cost = new HashMap<>();
        
        HashMap<Point, Point> inheritance1 = new HashMap<>();
    	
    	double fn = 0.0;
    	boolean found = false;
    	//left heuristic    	
    	
    	
    	try
    	{
    	  	
    		if(world.isPassable(start.x, start.y))
    		{
    			open.add(start);
    		}
    		
    		Point left = null;
    		Point up= null;        		            	
        	Point down = null;            	
        	Point right = null;
        	Point best = null;
        	Point visit = null;
        	Point goal= null;
        	double pointcost = 0.0;          

    		while (open.size() >0)
    		{
	
    			visit = open.get(0);
    			open.remove(0);
	    		if (visit.x == end.x  &&visit.y == end.y && world.isPassable(end.x,end.y))
				{
	    			found = true;	   
	    			goal = visit;
	    			break;
				}
	    		  if(visit.x-1>0 && world.isPassable(visit.x-1,visit.y) && !closed.contains(visit) ) {
	        		     left = new Point(visit.x-1,visit.y);
	        		     	
        		     		 
	        		     		
	        		    	if (!closed.contains(left) && !open.contains(left)) {
	        		    	double dleft= heuristic_value(left,end);
			        		  pointcost = pointcost + 1;
	        		     	 cost.put(pointcost, left);

        		     		 fn =getFn(dleft,left,cost);
	        		    		hValue.add(fn);

	        				  inheritance.put(fn,left);

	        				  
	        		    		best =  bestheuristic_piont(hValue,inheritance);

	        		    	  inheritance1.put(left,visit);
	        		    	  open.add(left);
	        		    	  
	        		    	}
	        		    	if (open.contains(left) ) {
	        		    		
	        		    		if ( best == left ) {
	        		    			open.indexOf(left);
	        		    		}
	        		    		
	        		    	}
	        		    	if (closed.contains(left) ) {
	        		    		
	        		    		if (best == left) {
	        		    			closed.remove(closed.indexOf(left));
	        		    			open.add(left);
	        		    		}
	        		    		
	        		    	}
	        		    	
	        		    		
	        		    	
	        		    	
			         		}
	    		 
	        		   
	    			    if(visit.y-1>0 && world.isPassable(visit.x,visit.y-1)) {
	        			     up = new Point(visit.x,visit.y-1);
		        		     	

		        		     	if (!closed.contains(up)&& !open.contains(up)) {
		        		     		
		        		     		double dup= heuristic_value(up,end);
	        		     		 pointcost = pointcost + 1;
	        		     		 cost.put(pointcost, up);
	        		     		 fn =getFn(dup,up,cost);
		        		     		
					        		 hValue.add(fn);

					        		 inheritance.put(fn,up);
			        		    	  inheritance1.put(up,visit);
			        		    	  open.add(up);
			        		    	 best =  bestheuristic_piont(hValue,inheritance);

		        		     	}
		        		     	if (open.contains(up) ) {
		        		    		
		        		    		if ( best == up ) {
		        		    			open.indexOf(up);
		        		    		}
		        		    		
		        		    	}
		        		    	if (closed.contains(up) ) {
		        		    		
		        		    		if (best == up) {
		        		    			closed.remove(closed.indexOf(up));
		        		    			open.add(up);
		        		    		}
		        		    		
		        		    	}
		        		    	
		        		     	
		        		      } 
	    			    if(visit.x+1< world.getMaxWidth()&&world.isPassable(visit.x+1,visit.y)) {
	        			     right = new Point(visit.x+1,visit.y);
		        		     
		        		    	if (!closed.contains(right)&& !open.contains(right)) {
		        		    	double dright= heuristic_value(right,end);
		        		     	pointcost = pointcost + 1;
	        		     		 cost.put(pointcost, right);
	        		     		 fn =getFn(dright,right,cost);
					        		  hValue.add(fn);

					        		  inheritance.put(fn,right);
			        		    	  inheritance1.put(right,visit);
			        		    	  open.add(right);
			        		    	  best =  bestheuristic_piont(hValue,inheritance);


		        		    	}
		        		    	if (open.contains(right) ) {
		        		    		
		        		    		if ( best == right ) {
		        		    			open.indexOf(right);
		        		    		}
		        		    		
		        		    	}
		        		    	if (closed.contains(right) ) {
		        		    		
		        		    		if (best == right) {
		        		    			closed.remove(closed.indexOf(right));
		        		    			open.add(right);
		        		    		}
		        		    		
		        		    	}
		        		    	
	        			   
	        			        }
	    			  if(visit.y+1<world.getMaxHeight()&&world.isPassable(visit.x,visit.y+1)) {
	        		    	down = new Point(visit.x,visit.y+1);
	        		    
	        		     	if ( !closed.contains(down)&& !open.contains(down)) {
	        		     		double ddown= heuristic_value(down,end);
	        		     	pointcost = pointcost + 1;
       		     		 cost.put(pointcost, down);
    		     		 fn =getFn(ddown,down,cost);
        		    		 hValue.add(fn);

				        		  inheritance.put(fn,down);
		        		    	  inheritance1.put(down,visit);
		        		    	  open.add(down);
		        		    	  best =  bestheuristic_piont(hValue,inheritance);

	        		    
	        		     	}
	        		     		if (open.contains(down) ) {
	        		    		
	        		    		if ( best == down ) {
	        		    			open.indexOf(down);
	        		    		}
	        		    		
	        		    	}
	        		    	if (closed.contains(down) ) {
	        		    		
	        		    		if (best == down) {
	        		    			closed.remove(closed.indexOf(down));
	        		    			open.add(down);
	        		    		}
	        		    		
	        		    	}
	        		    	
	        			        }	
	    		
	        		 hValue.clear();
		    	  
	    	    	closed.add(visit);

	        		
	    		    
	    		  
		} //end of while

		
    	if(found) {
    		
    	
    		while (goal !=null ) {
    			bestpath.add(0,goal);
    				  
	    		goal = inheritance1.get(goal);

    		}
    		System.out.println(bestpath);

    		return bestpath;


    		}
    	else return null;
		
	
    		
    	}catch(OutOfThisWorldException o)
    	{
    		System.out.println("Invalid location");
    	}
    	return new ArrayList<Point>();
    }
    
    /**
     * Controller for the agent's behavior
     */
    public void action()
    {
    	// Creates a Point object to specify the start location. The x and y
    	// coordinates are the ship's location
    	Point startLocation = new Point(ship.getX(), ship.getY());
    	
    	// Create a Point object to specify the end location.
    	// world.getTargets() retrieves an ArrayList of Point objects
    	// .get(0) retrieves the first object in the ArrayList. There is 
    	//		only 1 target in the map so this is what's returned
    	// .x and .y are attributes of a Point object, which store the x and y location of the target    	
    	//Point endLocation = new Point(11, 17);
    	
    	//Point endLocation = new Point(22,18);
    	
    	
    	Point endLocation = new Point(world.getTargets().get(0).x-1, world.getTargets().get(0).y);
    	
    	// Retrieve the path from the start to end location (currently these are dummy variables)
    	ArrayList<Point> path = getPath(startLocation, endLocation);
    	
    	
    		
    	for(Point p : path)
    	{
    		sailTo(p);
    	}
    }

    /**
     * Moves a ship closer to the given x and y coordinate
     * @param x
     * @param y
     */
    public void sailTo(Point loc)
    {               
        //Keep moving toward the x,y location
        if(ship.getX()>loc.x)
       	 ship.moveLeft();
    	else
       	 if(ship.getX()<loc.x)
       		 ship.moveRight();
        	else
            	if(ship.getY()>loc.y)
       	     	ship.moveUp();
            	else
       			 if(ship.getY()<loc.y)
                   	 ship.moveDown();
    }
}
