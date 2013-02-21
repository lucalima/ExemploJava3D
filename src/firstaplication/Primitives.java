package firstAplication;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Primitives extends JFrame {

    /////////////////////////////////////////////////////////////////
    // Atributo da classe Primitives
    //
    private SimpleUniverse universe = null;
    

    /////////////////////////////////////////////////////////////////
    // Construtor da classe HelloUniversePrimitives
    //
    public Primitives() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        container.add("Center", canvas);

        // Cria um sub-grafo de conteudo
        BranchGroup scene = criaGrafoDeCena();
        universe = new SimpleUniverse(canvas);

        // O c�digo abaixo faz com que a ViewPlatform seja movida
        // um pouco para tr�s, para que os objetos possam ser
        // visualizados
        universe.getViewingPlatform().setNominalViewingTransform();

	  // Anexa o sub-grafo no universo virtual
        universe.addBranchGraph(scene);
        
        setSize(350,350);
        setVisible(true);
    }
    
    
    /////////////////////////////////////////////////////////////////
    // M�todo respons�vel pela cria��o do grafo de cena (ou sub-grafo)
    //    
    public BranchGroup criaGrafoDeCena() {
        
        // Cria o nodo raiz  
        BranchGroup objRaiz = new BranchGroup();

        // Cria o nodo TransformGroup e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo de cena.
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(objTrans);

	  // Cria um "bounds" 
	  BoundingSphere bounds =
	       new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
			 
		 // Especifica um background azul e adiciona-o no grafo
			  Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
			  Background bg = new Background(bgColor);
			  bg.setApplicationBounds(bounds);
			  objRaiz.addChild(bg);
	
		 // Especifica as luzes do "ambiente"
			 Color3f corLuz = new Color3f(2.0f, 2.0f, 2.0f);
			 Vector3f direcaoLuz  = new Vector3f(1.0f, -1.0f, -1.0f);
			 Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

			 AmbientLight luzAmb = new AmbientLight(corAmb);
			 luzAmb.setInfluencingBounds(bounds);
			 DirectionalLight luzDir = new DirectionalLight(corLuz, direcaoLuz);
			 luzDir.setInfluencingBounds(bounds);
			 objRaiz.addChild(luzAmb);
			 objRaiz.addChild(luzDir);
	
	
        Appearance app = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(new Color3f(0.0f, 1.0f, 0.0f));
        app.setColoringAttributes(ca);      
		  
		  
		 //Par�metros: Color3f ambientColor, Color3f emissiveColor, 
		//            Color3f diffuseColor, Color3f specularColor, 
		//            float shininess
		Material material = 
		new Material(new Color3f(0.8f, 0.8f, 0.1f), 
		new Color3f(0.0f, 0.0f, 0.0f),
		new Color3f(0.8f, 0.8f, 0.1f),
		new Color3f(1.0f, 1.0f, 1.0f), 100.0f);
		app.setMaterial(material);
		  
        
//        com.sun.j3d.utils.geometry.Box cubo = new com.sun.j3d.utils.geometry.Box(0.2f, 0.2f, 0.2f, app);
//		cubo.setAppearance(app);
//		objTrans.addChild(cubo);
			  
			  
		 // Op��o 1: cria uma esfera
//			 Sphere esfera = new Sphere(0.6f);
//			 esfera.setAppearance(app);   
//			 Sphere sphere = new Sphere(0.2f);
//			 sphere.setAppearance(app);
//			 objTrans.addChild(sphere);
			 
		 Cone cone = new Cone(0.4f, 0.8f);
		  cone.setAppearance(app);
		  objTrans.addChild(cone);
		
       
		  
		
		 
		

    
        // Cria um novo objeto Behaviour que ir� executar as 
        // opera��es desejadas no "transform" especificado  
        // e adiciona-o no grafo.
        Transform3D trans = new Transform3D();
        trans.rotZ(Math.toRadians(60));
        
        Alpha rotacaoAlpha = new Alpha(-1, 4000);
        
        RotationInterpolator rotator =
               new RotationInterpolator(rotacaoAlpha, objTrans, trans,
                                         0.0f, (float) Math.PI*2.0f);               
        rotator.setSchedulingBounds(bounds);
    
        objRaiz.addChild(rotator);    
    
        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();

        return objRaiz;
    }


    /////////////////////////////////////////////////////////////////
    // M�todo principal que permite executar a aplica��o
    //
    public static void main(String[] args) 
    {
        Primitives h = new Primitives();
    }

}