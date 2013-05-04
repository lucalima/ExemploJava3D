package firstAplication;

import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.media.j3d.*;
import javax.vecmath.*;

public class BehaviorExample extends JFrame implements MouseMotionListener, MouseWheelListener {

    //////////////////////////////////////////
    // Atributo da classe BehaviorExample ///
    ////////////////////////////////////////
    private SimpleUniverse universe = null;
    TransformGroup objTrans;
    Cylinder cilindro;
    Point3f ponto = new Point3f();
    Transform3D trans;
    ////////////////////////////////////////////
    // Construtor da classe BehaviorExample ///
    //////////////////////////////////////////
    public BehaviorExample() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        container.add("Center", canvas);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
        // Cria um grafo de cena
        BranchGroup scene = criaGrafoDeCena();
        universe = new SimpleUniverse(canvas);

        // O c�digo abaixo faz com que a ViewPlatform seja movida
        // um pouco para tr�s, para que os objetos possam ser
        // visualizados
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        viewingPlatform.setNominalViewingTransform();

        // Adiciona "mouse behaviors" � "viewingPlatform" 
        // (equivale a trocar a posi��o do "observador virtual")
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        universe.addBranchGraph(scene);
        
        setSize(350, 350);
        setVisible(true);
    }

    ///////////////////////////////////////////////////////////////////////
    // M�todo respons�vel pela cria��o do grafo de cena (ou sub-grafo)
    //    
    public BranchGroup criaGrafoDeCena() {

        // Cria o n� raiz 
        BranchGroup objRaiz = new BranchGroup();

        // Cria o nodo TransformGroup e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo de cena.
        trans = new Transform3D();
        trans.setTranslation(new Vector3d(0f, 0f, 0f));
        
        objTrans  = new TransformGroup(trans);
        
        
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
               
        // Cria um "bounds" para o background 
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Especifica um background azul e adiciona-o no grafo
        Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        objRaiz.addChild(bg);

        // Especifica as luzes do "ambiente"
        // Luz Ambiente
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(bounds);
        objRaiz.addChild(luzAmb);

        // Luz Direcional
        Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f direcaoLuz = new Vector3f(-1.0f, -1.0f, -1.0f);
        DirectionalLight luzDir = new DirectionalLight(corLuz, direcaoLuz);
        luzDir.setInfluencingBounds(bounds);
        objRaiz.addChild(luzDir);

        Appearance app = new Appearance();

        //Color3f ambientColor, Color3f emissiveColor, Color3f diffuseColor, 
        //				Color3f specularColor, float shininess
        Material material = new Material(new Color3f(0.8f, 0.2f, 0.8f),
                new Color3f(0.0f, 0.0f, 0.0f),
                new Color3f(0.8f, 0.2f, 0.8f),
                new Color3f(1.0f, 1.0f, 1.0f), 100.0f);

        app.setMaterial(material);

        cilindro = new Cylinder(0.5f, 0.8f, 1, 20, 10, app);
        
        MouseTranslate mtrans = new MouseTranslate();
        mtrans.setTransformGroup(objTrans);
        mtrans.setSchedulingBounds(new BoundingBox());
        
        objTrans.addChild(cilindro);
        objRaiz.addChild(mtrans);
        objRaiz.addChild(objTrans);
        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();

        return objRaiz;
    }

    ///////////////////////////////////////////////////////////////////////
    // M�todo principal que permite executar a aplica��o
    //
    public static void main(String[] args) {
        BehaviorExample h = new BehaviorExample();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cilindro.getLocalToVworld(trans);
        trans.transform(ponto);
        System.out.println("x = "+ponto.x+" y="+ponto.y+" z="+ponto.z);
        ponto = new Point3f();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        
        cilindro.getLocalToVworld(trans);
        trans.transform(ponto);
        Vector3d vec;
        if(e.getWheelRotation() == -1)
        {
            vec = new Vector3d(ponto.x, ponto.y, ponto.z - 0.1f);
        } else {
            vec = new Vector3d(ponto.x, ponto.y, ponto.z + 0.1f);
        }
        trans.setTranslation(vec);
        objTrans.setTransform(trans);
        
        System.out.println("x = "+ponto.x+" y="+ponto.y+" z="+ponto.z);
        ponto = new Point3f();
    }
}
