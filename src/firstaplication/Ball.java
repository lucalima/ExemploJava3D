package firstAplication;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;

import javax.media.j3d.DirectionalLight;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Ball
{
	public Ball()
	{
		SimpleUniverse universe = new SimpleUniverse();
		
		BranchGroup group = new BranchGroup();
		
		Sphere sphere = new Sphere(0.8f);
		group.addChild(sphere);
		
		
		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		Vector3f lightDirection = new Vector3f(9.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, lightDirection);
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		
		universe.addBranchGraph(group);
	}
	public static void main(String [] args)
	{
		new Ball();
	}
}
