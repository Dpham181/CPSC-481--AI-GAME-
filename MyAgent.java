import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

import aicapn.AiCapn;
import aicapn.bot.Bot;
import aicapn.exceptions.OutOfThisWorldException;
public class MyAgent extends Bot
{
	
    public static void main(String[] args)
    {
    	// Create the AiCapn game object
        AiCapn game=new AiCapn();
        // Attach your agent to the game
        game.attach(new MyAgent());
        // Begin the game
        game.begin();
    }

	
 
    /**
     * Retrieves the path from a start to and end location
     * @param start x and y coordinate of starting location represented as a Point object
     * @param end x and y coordinate of end location represented as a Point object
     * @return path from the start to end location represented as an ArrayList of Point objects
     */
    public ArrayList<Point> getPath(Point start, Point end)
    {
    	ArrayList<Point> closed = new ArrayList<>();
    	ArrayList<Point> open =new ArrayList<>();
        HashMap<Point, Point> inheritance = new HashMap<>();

    	ArrayList<Point> pathtotarget =new ArrayList<>();

    	try
    	{
        	//breadth first search

        	    closed.add(start);   ///get start location of bot  and insert last  into arraylist 
        		Point begin = closed.get(0); // push out the first Point of arraylist which is (11,16)
        		
        		/* define all the move of Bot
        		 * 
        		 *  
        		 *  moving left x-1 
        		 *  moving right x+1
        		 *  moving up y -1 
        		 *  moving down y+ 1
        		 *  
        		 *  
        		 */
    		    Point left = null ;
    		    Point right = null ;
    		    Point up = null ;
    		    Point down = null ;
    		    Point notclosed = null;
    		    
    		    
    		   // checking at (11,16)  then 10,16  moving left, add that to Point left
           	  if(begin.x-1>0 && world.isPassable(begin.x-1,begin.y)) {
    		     left = new Point(begin.x-1,begin.y);
    		        }
    		      // so on with right up down 
  		    if(begin.x+1< world.getMaxWidth()&&world.isPassable(begin.x+1,begin.y)) {
    			     right = new Point(begin.x+1,begin.y);
        		 
    			        }
    		    
		    if(begin.y-1>0 && world.isPassable(begin.x,begin.y-1)) {
    			     up = new Point(begin.x,begin.y-1);

    			        }
    		    
		    if(begin.y+1<world.getMaxHeight()&&world.isPassable(begin.x,begin.y+1)) {
    		    	down = new Point(begin.x,begin.y+1);
       		    

    			        }
    		 
    	    	// as 10,16 the bot can still move generate more childrens and adding it to open arraylist 
    		    if(left!=null && !closed.contains(left)) {
    		    	open.add(left);

    		    }
    	        if(up!=null && !closed.contains(up)) {
    	        	
    	        
    	        	open.add(up);

    	        }
    	        if(right!=null&&!closed.contains(right)) {
    	        	open.add(right);
    	        }
    	        if(down!=null&&!closed.contains(down)) {
    	        	
    	        
    	        	open.add(down);
    	        }

    	        
    	        
        			boolean found = false;
        			
        			// now open arraylist  contains 4 childrens of 11,16 
    	        	while (open.size()>0 ) {
    	        	
    	        		// get the first one which is 10,16 
    	    		notclosed = open.get(0);
    	    			// add to closed bfs al rule 
    	    			// add left node then generating children to the right list 
        		    closed.add(notclosed);
        		    
       		        // remove the the current node that already checking (10,16) from open arraylist 
 	                open.remove(0);
 	                
 	                
 	                // checking the end piont which it the goal if it current point = end piont (5,5)
 	                // then jump to found statement outside the while loop.
	          	  if(notclosed.x == end.x  &&notclosed.y == end.y && world.isPassable(end.x,end.y))
	          	  {
	          		  
	    		    	found = true;
	    		    	ship.fire();
	    		    	break;

	          	  }
	          	  // all the rest just same thing at the inital if statement above to keep generate the children of the root 
	          	  
	             	  if(notclosed.x-1>0 && world.isPassable(notclosed.x-1,notclosed.y)) {
	         		     left = new Point(notclosed.x-1,notclosed.y);
	         		    if(left!=null&&!closed.contains(left)&&!open.contains(left)) {
	         		    	open.add(left);
	         		    	// hashmap to mapping the current with childrens for tracebacking 
	                	    inheritance.put(left,notclosed);

    	    		    }
	         		        }
	         	
	             	  
	             	  if(notclosed.x+1>0 && world.isPassable(notclosed.x+1,notclosed.y)) {
		         		     right = new Point(notclosed.x+1,notclosed.y);
		         		    if(right!=null&&!closed.contains(right)&&!open.contains(right)) {
		         		    	open.add(right);
		                	    inheritance.put(right,notclosed);

	    	    		    }
		         		        }
	       		    
	     		    if(notclosed.y-1>0 && world.isPassable(notclosed.x,notclosed.y-1)) {
	         			     up = new Point(notclosed.x,notclosed.y-1);
	         			
	         			    if(up!=null && !closed.contains(up)&&!open.contains(up)) {
		         		    	open.add(up);
		                	    inheritance.put(up,notclosed);

	         			    	}
	    	    	
	         			        }
	     		    
	     		    
	     		    
	     		    
	     		    
	     		   if(notclosed.y+1>0 && world.isPassable(notclosed.x,notclosed.y+1)) {
       			     down = new Point(notclosed.x,notclosed.y-1);
       			
       			    if(down!=null && !closed.contains(down)&&!open.contains(down)) {
	         		    	open.add(down);
	                	    inheritance.put(down,notclosed);

       			    	}
  	    	
       			        }
	     		   
    	        	}
    	        	if (found) {
    	        	// found 5,5 on open list then point out as the goal for checking back which 5,5 parents 
    	        	// keep going until the goal is null exit the loop
    		    	Point goal = notclosed;
    		    		while (goal!= null) {
    		    	
    		    		pathtotarget.add(0,goal);
    		    		goal = inheritance.get(goal);

    		    		}
        	    		System.out.println(pathtotarget);

    	    	        return pathtotarget;

    	        	}
    	        	else 
    	        		return null;


    	}catch(OutOfThisWorldException o)
    	{
    		System.out.println("Invalid location");
    	}

    	    return new ArrayList<Point>();
    		
    }
    
    /**;
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
    	Point endLocation = new Point(world.getTargets().get(0).x-1, world.getTargets().get(0).y);
    	
    	// Retrieve the path from the start to end location (beginently these are closed variables)
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