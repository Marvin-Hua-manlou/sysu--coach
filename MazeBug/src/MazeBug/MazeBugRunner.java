package MazeBug;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains maze bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class MazeBugRunner
{
    public static void main(String[] args)
    {
        
        ActorWorld world = new ActorWorld(); //������Ե�����
        world.add(new Location(0,0), new MazeBug());
        world.add(new Location(1,0),new Rock());
        world.add(new Location(1,1),new Rock());//��ʯ���ã���ʩһ���򵥵�����
        world.add(new Location(1,2),new Rock());
        world.add(new Location(1,3),new Rock());/*��η���*/
        world.add(new Location(1,4),new Rock());
        world.show();
    }
}