package firstAplication;

import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.image.TextureLoader;
import firstaplication.PorTempo;
import firstaplication.Timeline;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.TreeSet;
import javax.media.j3d.*;
import javax.vecmath.*;

public class GeometryExample extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener {

    ////////////////////////////////////////////
    /// Atributo da classe GeometryExample  ///
    //////////////////////////////////////////
    private SimpleUniverse universe = null;
    long tempo;
    long tempoInicialApp = System.currentTimeMillis();
    long tempoFinalApp;
    long tempoInicialPressed;
    long tempoFinalPressed;
    long tempoInicialParado;
    long tempoFinalParado;
    int totalClique = 0;
    //ArrayList lista = new ArrayList();
    
    TreeSet<Timeline> lista = new TreeSet<Timeline>(new PorTempo());
    //////////////////////////////////////////////
    /// Construtor da classe GeometryExample  ///
    ////////////////////////////////////////////

    public GeometryExample() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        container.add("Center", canvas);

        // Cria um sub-grafo de conte�do
        BranchGroup scene = criaGrafoDeCena();

        universe = new SimpleUniverse(canvas);

        // O c�digo abaixo faz com que a ViewPlatform seja movida
        // um pouco para tr�s, para que os objetos possam ser
        // visualizados
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        viewingPlatform.setNominalViewingTransform();

        // O c�digo abaixo altera o field-of-view para 
        // permitir a visualiza��o de todos objetos
        View view = universe.getViewer().getView();
        view.setFieldOfView(view.getFieldOfView() * 1.4);

        // Adiciona "mouse behaviors" � "viewingPlatform" 
        // (equivale a trocar a posi��o do "observador virtual")
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        // Anexa o sub-grafo no universo virtual
        universe.addBranchGraph(scene);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////
    /// M�todo respons�vel pela cria��o do grafo de cena (ou sub-grafo) ///
    //////////////////////////////////////////////////////////////////////
    public BranchGroup criaGrafoDeCena() {
        // Cria o nodo raiz 
        BranchGroup objRaiz = new BranchGroup();
        objRaiz.setCapability(BranchGroup.ALLOW_BOUNDS_READ);

        // Cria o nodo TransformGroup e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo de cena.
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(objTrans);

        // Cria um "bounds" para o background 
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Especifica um background azul e adiciona-o no grafo
        //Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        TextureLoader texturaBg = new TextureLoader(toolkit.getImage("C:\\Users\\Luiz Brandão\\Documents\\NetBeansProjects\\FirstApplication\\src\\firstaplication\\teste.png"), this);
        Background bg = new Background(texturaBg.getImage());
        bg.setApplicationBounds(bounds);
        //bg.setImageScaleMode(Background.SCALE_REPEAT);
        objRaiz.addChild(bg);

        // Especifica as luzes do "ambiente" (ambiente e direcional)
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(bounds);
        objRaiz.addChild(luzAmb);

        Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f direcaoLuz1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Vector3f direcaoLuz2 = new Vector3f(1.0f, -1.0f, -1.0f);
        DirectionalLight luzDir1 = new DirectionalLight(corLuz, direcaoLuz1);
        DirectionalLight luzDir2 = new DirectionalLight(corLuz, direcaoLuz2);
        luzDir1.setInfluencingBounds(bounds);
        luzDir2.setInfluencingBounds(bounds);
        objRaiz.addChild(luzDir1);
        objRaiz.addChild(luzDir2);

        // Especifica a apar�ncia do material
        Appearance app = new Appearance();
        Material material = new Material(new Color3f(0.7f, 0.1f, 0.7f),
                new Color3f(0.0f, 0.0f, 0.0f),
                new Color3f(0.7f, 0.1f, 0.7f),
                new Color3f(1.0f, 1.0f, 1.0f), 60.0f);
        app.setMaterial(material);

        Shape3D s3d = new Shape3D();
        s3d.setAppearance(app);
        s3d.setGeometry(cubeGeometry());
        objTrans.addChild(s3d);

        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();
        return objRaiz;
    }

    /////////////////////////////////////////////////////////////////////////
    /// M�todo respons�vel pela cria��o de um cubo usando GeometryArray ////
    ///////////////////////////////////////////////////////////////////////
    private Geometry cubeGeometry() {
        GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
        Point3f[] pts = new Point3f[8];
        pts[0] = new Point3f(-0.07f, 0.07f, 0.07f);
        pts[1] = new Point3f(0.07f, 0.07f, 0.07f);
        pts[2] = new Point3f(0.07f, -0.07f, 0.07f);
        pts[3] = new Point3f(-0.07f, -0.07f, 0.07f);
        pts[4] = new Point3f(-0.07f, 0.07f, -0.07f);
        pts[5] = new Point3f(0.07f, 0.07f, -0.07f);
        pts[6] = new Point3f(0.07f, -0.07f, -0.07f);
        pts[7] = new Point3f(-0.07f, -0.07f, -0.07f);
        int[] indices = {
            0, 4, 7, 3, // left face   
            6, 2, 3, 7, // bottom face 
            4, 5, 6, 7, // back face   
            5, 1, 2, 6, // right face  
            5, 4, 0, 1, // top face    
            1, 0, 3, 2 // front face 
        };
        gi.setCoordinates(pts);
        gi.setCoordinateIndices(indices);
        NormalGenerator ng = new NormalGenerator();
        // Passar 100 como par�metro para a fun��o abaixo, faz com que
        // as "dobras" (uni�o das faces) fique mais suave do que se fosse  
        // passado um valor mais baixo
        ng.setCreaseAngle((float) Math.toRadians(100));
        //ng.setCreaseAngle( (float) Math.toRadians(45) ); 
        ng.generateNormals(gi);
        GeometryArray cube = gi.getGeometryArray();

        return cube;
    }

    //////////////////////////////////////////////////////////
    /// M�todo principal que permite executar a aplicacao ///
    ////////////////////////////////////////////////////////
    public static void main(String[] args) {
        GeometryExample g = new GeometryExample();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicou");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressionou");
        timeline(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Clicou");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entrou");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Saiu");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        timeline(e);
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("Moveu");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //timeline("wheel ",e);
    }

    public void percorre(TreeSet<Timeline> lista) {
        Iterator itr = lista.iterator();
        while (itr.hasNext()) {
            Timeline time = (Timeline) itr.next();
            System.out.println("Tempo " + time.getTempo() + " X " + time.getX() + " Y " + time.getY() + " Z " + time.getZ()+" etapa "+time.getClique());
        }
    }
        
    public int verificaMouseParado(MouseEvent e){
        totalClique = totalClique + e.getClickCount();
        return totalClique;
    }

    public void timeline(MouseEvent e) {
        Timeline line = new Timeline();

        line.setTempo((System.currentTimeMillis() - tempoInicialApp) / 1000);
        line.setX(e.getX());
        line.setY(e.getY());
        line.setZ(e.getX() ^ 2 + e.getY() ^ 2);
        line.setClique(verificaMouseParado(e));
        lista.add(line);
        
        percorre(lista);
    }
}