package firstAplication;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.BranchGroup;



/**
 *
 * @author Lucas
 */
public class Hello3d 
{
    public Hello3d()
    {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup group = new BranchGroup();
        group.addChild(new ColorCube(0.3));
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }
    public static void main(String[] args)
    {
        new Hello3d();
    }
}