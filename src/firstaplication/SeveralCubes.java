package firstAplication;

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class SeveralCubes extends JFrame {

    /////////////////////////////////////////////////////////////////
    // Atributo da classe SeveralCubes
    //
    private SimpleUniverse universe = null;
    

    /////////////////////////////////////////////////////////////////
    // Construtor da classe SeveralCubes 
    //
    public SeveralCubes () {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        container.add("Center", canvas);

        // Cria sub-grafos de conte�do 
        BranchGroup cena1 = criaGrafoDeCena(0.2,0.3,0.0,0.0,
                           Math.toRadians(10),Math.toRadians(30),0.0);
        BranchGroup cena2 = criaGrafoDeCena(0.2,-0.3,0.0,0.0,
                           Math.toRadians(10),Math.toRadians(-30),0.0);
 
        universe = new SimpleUniverse(canvas);

        // O c�digo abaixo faz com que a ViewPlatform seja movida
        // um pouco para tr�s, para que os objetos possam ser
        // visualizados.
        universe.getViewingPlatform().setNominalViewingTransform();

        // Anexa os sub-grafos de conte�do no universo virtual
        universe.addBranchGraph(cena1);
        universe.addBranchGraph(cena2);
        
        setSize(350,350);
        setVisible(true);
    }
    
    
    /////////////////////////////////////////////////////////////////
    // M�todo respons�vel pela cria��o do grafo de cena (ou sub-grafo)
    //    
    public BranchGroup criaGrafoDeCena(double e, double tx, double ty, 
                                       double tz, double rx, double ry, 
                                       double rz) {
        // Cria o nodo raiz 
        BranchGroup objRaiz = new BranchGroup();

        // Cria o nodo TransformGroup e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo de cena.
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(objTrans);
    
        // Cria um cubo colorido (Shape3D) e o adiciona no grafo.
        objTrans.addChild(new ColorCube(0.5));
    
        // Cria um novo objeto que ir� aplicar as transforma��es
        // geom�tricas sobre o cubo e o adicina no grafo.
        Transform3D trans = new Transform3D();
        Transform3D t1 = new Transform3D();
        Transform3D t2 = new Transform3D();
        // Aplica rota��o
        t1.rotX(rx);
        t2.rotY(ry);
        trans.rotZ(rz);
        trans.mul(t1);
        trans.mul(t2);
        // Aplica escala
        trans.setScale(e);
        // Aplica transla��o
        trans.setTranslation(new Vector3d(tx,ty,tz));
        objTrans.setTransform(trans);

        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();

        return objRaiz;
    }


    /////////////////////////////////////////////////////////////////
    // M�todo principal que permite executar a aplica��o
    //
    public static void main(String[] args) 
    {
        SeveralCubes h = new SeveralCubes();
    }

}