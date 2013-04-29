package firstAplication;

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class BehaviorExample extends JFrame {

    //////////////////////////////////////////
    // Atributo da classe BehaviorExample ///
    ////////////////////////////////////////
    private SimpleUniverse universe = null;

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
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(objTrans);

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

        Cylinder cilindro = new Cylinder(0.5f, 0.8f, 1, 20, 10, app);

        objTrans.addChild(cilindro);

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
}
