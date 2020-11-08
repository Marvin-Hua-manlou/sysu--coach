package MazeBug;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<Location> crossLocation = new Stack<Location>();
	public Set<Location> flowerLocations = new HashSet<Location>();;
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		isEnd = reachRedRock();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				showPath();
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		}
		else {//获得当前的位置
			ArrayList<Location> temp = getUnvisitedLocation();
			if (temp.size() == 0) {
				next = crossLocation.pop();
				move();
			}
			else {//将位置放到列表当中
				crossLocation.push(getLocation());
				//int index = (int) (Math.random() * temp.size());
				//next = temp.get(index);
				next = selectLocation(SelectDirection(crossLocation), temp);
				move();
				
			}
			//increase step count when move 
			stepCount++;
		} 
	}

	

	
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {//重载move()函数
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else
			removeSelfFromGrid();
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
		flowerLocations.add(loc);
	}
	

	//Reach the red Rock
	public boolean reachRedRock() {//判断是否到达了红色的石头
		ArrayList<Location> locs = getValidAdjacentLocations();
		Grid<Actor> gr = getGrid();
		if (locs.size() == 0) {
			return false;
		}
		for (Location temp: locs) {
			if ((gr.get(temp) instanceof Rock) && (gr.get(temp).getColor()).equals(Color.RED)) {
				return true;
			}
		}
		return false;
	}
	
	//select the location that wasn't visited from the getValidAdjacentLocations() method
	public ArrayList<Location> getUnvisitedLocation() {//寻找邻近的可能的位置的函数
		ArrayList<Location> locs = getValidAdjacentLocations();
		ArrayList<Location> unvisited = new ArrayList<Location>();
		Grid<Actor> gr = getGrid();
		if (locs.size() == 0) {
			return unvisited;
		}
		for (Location temp:locs)  {
			if (!(gr.get(temp) instanceof Rock) && !(gr.get(temp) instanceof Flower)) {
			    unvisited.add(temp);
			}
		}
		return unvisited;
	}
	
	//get all valid locations in east , west, south, north
	 public ArrayList<Location> getValidAdjacentLocations()//获得当前的位置可用的位置
     {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.RIGHT; i++)//转向的设置
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.RIGHT;
        }
        return locs;
     }
	 
	 //show the correct path
	 public void showPath() {
		 if (flowerLocations.size() == 0 || crossLocation.size() == 0) {
			 return;
		 }
		 //clear all the flower
		 Grid<Actor> gr = getGrid();
		 for (Location temp:flowerLocations) {
			 gr.get(temp).removeSelfFromGrid();
		 }
		 //put flower in the correct path
		 for (Location temp:crossLocation) {
			 gr.put(temp, new Flower(Color.GREEN));
		 }	
	 }
	 
	 
	 
	 public int[] SelectDirection(Stack<Location> crossPath) {//选择转向的函数
		 //North(0), East(90), South(180), West(270)
		 int[] direction = {1, 1, 1, 1};
		 if (crossPath.size() == 0) {
			 /*
			 int temp = (int)((Math.random() * 4));
			 */
			 return direction;
		 }
		 else {
			 int sizeOfStack = crossPath.size();//选择是一个
			 for (int i = 0; i < sizeOfStack-1; i++) {
			     int tempDir = crossPath.elementAt(i).getDirectionToward(crossPath.elementAt(i+1));
			     if (tempDir == 0) {
			    	 direction[0]++;//没选择一次，该函数数组的模型就递增
			     }
			     if (tempDir == 90) {
			    	 direction[1]++;
			     }
			     if (tempDir == 180) {
			    	 direction[2]++;
			     }
			     if (tempDir == 270) {
			    	 direction[3]++;
			     }
			 }
			 int tempDir = crossPath.elementAt(sizeOfStack-1).getDirectionToward(this.getLocation());
			 if (tempDir == 0) {
		    	 direction[0]++;//from china abord.
		     }
		     if (tempDir == 90) {
		    	 direction[1]++;
		     }
		     if (tempDir == 180) {
		    	 direction[2]++;
		     }
		     if (tempDir == 270) {//wode shiazo mxiefne deihde 
		    	 direction[3]++;
		     }
		     return direction;
		 }
	 }
	 
	 public  Location selectLocation(int[] dirs, ArrayList<Location> locs) {
		 if (locs.size() == 1)
			 return locs.get(0);
		 Location temp = locs.get(0);//获得我们可能的位置
		 for (Location i : locs) {
			 int index = this.getLocation().getDirectionToward(i)/90;//四哥方向每个方向是90
			 int index2 = this.getLocation().getDirectionToward(temp)/90;
			 if (dirs[index] > dirs[index2]) {
				 temp = i;
			 }
		 }
		 return temp;
	 }
	 
	 
	 
}


