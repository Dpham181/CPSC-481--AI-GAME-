import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import aicapn.AiCapn;

import aicapn.bot.BeBot;
import aicapn.bot.Bot;
import aicapn.exceptions.OutOfThisWorldException;
import aicapn.resources.Items;
import aicapn.resources.Resources;
import aicapn.world.CrateState;
import aicapn.world.ShipState;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
//import java.lang.Math.sqrt;
public class MyAgent extends Bot
{
	



	public static void main(String[] args)
    {
    	// Create the AiCapn game object
        AiCapn game=new AiCapn();
        game.setGamemode(Resources.GAMEMODE_DEATHMATCH);

        // Attach your agent to the game
        game.attach(new MyAgent());
        
        //game.setHasItems(true);
      
     game.attach(new BeBot());
     game.attach(new BeBot());
     game.attach(new BeBot());


        //game.attach(new Bobot());

     


        game.begin();
    }
    
    public MyAgent()
    {
    	super();
    	// assigns the name of your agent
    	name = "Za Warudo";
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
	   
    	    	
    	

    	Point endLocation = null;

    	int d = 0;
    	
    	 while(ship.isAlive()) 
         {	
    		 // getting all targets on the map 
    		 ArrayList<ShipState> shipList=world.getShips(ship); 
    		 // shortest path 
    	     ArrayList<Point> path = new ArrayList<>();
    	       	     // if there are some targets 
    	     if (shipList.size()>0) {  
    	    	 
    	    	 	// current point of our agent and the first enimies
    	    	 	Point startLocation = new Point(ship.getX(), ship.getY());
    	    	 	endLocation= new Point ((int)shipList.get(0).getX(),(int)shipList.get(0).getY());    		
    	    	 	ArrayList<CrateState> crateList=world.getCrates(); 
    	    	 	
    	    	 	
    	    	 	/*
    	    	 	 * JFuzzylogic evaluation by reading from block rule (fire.fcl) 
    	    	 	 * 
    	    	 	 */
	    			String FCL_FILE_NAME = "src/fire.fcl";
	    	        FIS fis = FIS.load(FCL_FILE_NAME,true);
	    	        FunctionBlock functionBlock = fis.getFunctionBlock("FIRE_DECISION");
	    	        fis.setVariable("oponent_heath", shipList.get(0).getHealth());
			    	fis.setVariable("agent_heath", ship.getHealth());
			        fis.setVariable("item_nearby",0);
			        fis.evaluate();
			        Variable fire = functionBlock.getVariable("fire");
			        Variable oponent_heath = functionBlock.getVariable("oponent_heath");
			        oponent_heath.getLinguisticTerms();
			        Variable agent_heath = functionBlock.getVariable("agent_heath");
			        agent_heath.getLinguisticTerms();
			        Variable item_nearby = functionBlock.getVariable("item_nearby");
			        item_nearby.getLinguisticTerms();
			        
			        
			        // getting the last Linguistic then give center gravity as an integer
			        
			        d  = (int)fire.getLatestDefuzzifiedValue(); 
			  		System.out.println("The last value that getting from fuzzy: " +d);
		  		
		  		
	  		/*
	  		 *   Situation that smaller than 10 then running for heath item. 
	  		 */
			  		
	  		if ( d < 10)
	  		{

	  			if (crateList.size() > 0)
	  	    	{
	  	    	    
	  	    	        switch(crateList.get(0).getType())
	  	    	        {
	  	    	        	case Items.MINE:                  
		  	  	  				endLocation =  new Point (29,27);
		  	  	  				
		  	  	  					break;
	  	    	            case Items.SHIELD:           
		  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	         break;
	  	    	          case Items.CONFUSE: 
		  	  	  				endLocation =  new Point (5,5);
		  	  	  							break;
		  	  	  							
	  	    	        	case Items.SPEEDDOWN:                  
		  	  	  				endLocation =  new Point (22,17);

	  	    	        			break;
	  	    	          
	  	    	            case Items.SPEEDUP:    
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	  
	  	    	            case Items.HEAL:       
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	        
	  	    	            case Items.RANGE:    
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	        
	  	    	            case Items.INVULNERABLE:  
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            case Items.SWAP:      
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            case Items.WINGS:          
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            
	  	    	          

	  	    	        }
	  	    	    

	  	    	    
	  	    	    
	  	    	    
		    	      crateList.remove(0);

	  	    	
	  	    	}
	  			else
	  			{
	  				endLocation =  new Point(5,5);
	  	    		path = getPath(startLocation, endLocation);
	  	    		
	  	    	   for(Point p : path)
			    	{
			    	
			    		
		    					sailTo(p);
		    					System.out.format("Ship path: [%d,%d]\n\n", ship.getX(), ship.getY());
			    				
			    		
			    	}	 
	  	    	
	  	    	 int fireC = ship.getDamageDealt() + shipList.get(0).getHealth();
			    	
			    	if (ship.getDamageDealt() < fireC )
			    	{
			    		
			                 ship.fire();
			                			
			  		
			    	}
	  			}

	  		}
	  		
	  		/*
	  		 * middle range in 10 and 20 
	  		 * design the situation for waiting at 29,29 or 5,6 and keeping get all items that show up on the map.
	  		 * 
	  		 */
	  		
	  		
	  		if ( d >= 10 && d < 20) {
	  			// run for items
	  			System.out.format("Ship current location: [%d, %d]\n\n", ship.getX(), ship.getY());
	 
	  			 
	  			if (crateList.size() > 0)
	  	    	{
	  	    	    
	  	    	        switch(crateList.get(0).getType())
	  	    	        {
	  	    	      case Items.MINE:                  
	  	  	  				endLocation =  new Point (29,27);
	  	  	  				
	  	  	  					break;
	    	            case Items.SHIELD:           
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	    	            	         break;
	    	          case Items.CONFUSE: 
	  	  	  				endLocation =  new Point (5,5);
	  	  	  							break;
	  	  	  							
	    	        	case Items.SPEEDDOWN:                  
	  	  	  				endLocation =  new Point (22,17);

	    	        			break;
	  	    	          
	  	    	            case Items.SPEEDUP:    
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	  
	  	    	            case Items.HEAL:       
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	        
	  	    	            case Items.RANGE:    
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	        
	  	    	            case Items.INVULNERABLE:  
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            case Items.SWAP:      
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            case Items.WINGS:          
	  	  	  				endLocation =  new Point (crateList.get(0).getX(),crateList.get(0).getY());

	  	    	            	break;
	  	    	            
	  	    	          

	  	    	        }
	  	    	    

	  	    	      if (path == null) {
		      	    	 	startLocation = new Point(ship.getX(), ship.getY());

		  	  				Point default_end =  new Point (22,16);
		  	   	    	    
		  	   	    	    path = getPath(startLocation, default_end);
		      				
		  	  			}
		  	    	     else { 
		  	  				
		  	    	    path = getPath(startLocation, endLocation);
		  	    	     }
		  		    		  	    	  	
	  	    	    for(Point p : path)
			    	{
			    	
			    		
		    					sailTo(p);
		    					System.out.format("Ship path: [%d,%d]\n\n", ship.getX(), ship.getY());
			    				
			    		
			    	}	
	  	    	    
	  	    	    
		    	      crateList.remove(0);

	  	    	
	  	    	}
	  			else
	  			{
	  				

	  				endLocation = new Point(29,29);
	  	    		path = getPath(startLocation, endLocation);
	  	    		
	  	    	   for(Point p : path)
			    	{
			    	
			    		
		    					sailTo(p);
		    					System.out.format("Ship path: [%d,%d]\n\n", ship.getX(), ship.getY());
			    				
			    		
			    	}	 
	  	    	   
	  	    	 int fireC = ship.getDamageDealt() + shipList.get(0).getHealth();
			    	
			    	if (ship.getDamageDealt() < fireC )
			    	{
			    		if(shipList.get(0).getX() > ship.getX() || shipList.get(0).getX() < ship.getX()) 
							ship.setDirection(Resources.TOP);
				    	
						else if(shipList.get(0).getY() > ship.getY() || shipList.get(0).getY() < ship.getY()) 
							ship.setDirection(Resources.LEFT);
			    		ship.fire();
				    	
			                			
			  		
			    	}
	  			}
  	    	   

		    	
	    		

	  			}
	  			
	  		
	  		/*
	  		 * 
	  		 * situation that greater than 20 
	  		 * engage to fire 
	  		 * setting direction for our agent that facing the opponent during battle. 
	  		 * 
	  		 */
	  		
	  		if (d >= 20 ) {
     			shipList=world.getShips(ship);
    			
		    	path = getPath(startLocation, endLocation);
		    	
		 		    	
		    	for(Point p : path)
		    	{
		    		
		    		sailTo(p);

		    	}	 
		    	
		    	if(shipList.get(0).getX() > ship.getX() || shipList.get(0).getX() < ship.getX()) 
					ship.setDirection(Resources.TOP);
		    	
				else if(shipList.get(0).getY() > ship.getY() || shipList.get(0).getY() < ship.getY()) 
					ship.setDirection(Resources.LEFT);
	    		ship.fire();
		    	
		    	
		    
		    	if (ship.getX() == endLocation.getX() && ship.getY() == endLocation.getY())
		    	{
		    		shipList.remove(0);
		    	}
		    	
		    	
		    	
		    	shipList.remove(0);
         }
	  	

      }	  		
    		
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