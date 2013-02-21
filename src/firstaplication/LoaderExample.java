package br.com.abaco.treinamento.java.noturno.mouseEvents;

import java.applet.Applet;
import javax.swing.*;
import java.net.URL;
import java.awt.*;
import java.io.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.loaders.objectfile.ObjectFile; 
import com.sun.j3d.loaders.ParsingErrorException; 
import com.sun.j3d.loaders.IncorrectFormatException; 
import com.sun.j3d.loaders.Scene;
import javax.media.j3d.*;
import javax.vecmath.*;

public class LoaderExample extends Applet {

    ///////////////////////////////////////////////////////////////////////
    // Atributo da classe HelloUniverseBehavior
    //
    private SimpleUniverse universe = null;
    

    ///////////////////////////////////////////////////////////////////////
    // M�todo init da applet
    //
    public void init() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        add("Center", canvas);

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
        view.setFieldOfView(view.getFieldOfView()*1.4);
        
        // Adiciona "mouse behaviors" � "viewingPlatform" 
        // (equivale a trocar a posi��o do "observador virtual")
        OrbitBehavior orbit = 
                       new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds = 
                       new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);
     
        // Anexa o sub-grafo no universo virtual
        universe.addBranchGraph(scene);
        
        setSize(350,350);
        setVisible(true);
    }
    
    
    ///////////////////////////////////////////////////////////////////////
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

        // Cria um "bounds" para o background 
        BoundingSphere bounds =
           new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        // Especifica um background azul e adiciona-o no grafo
        Color3f bgColor = new Color3f(0.2f, 0.2f, 0.7f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        objRaiz.addChild(bg);

        // Especifica as luzes do "ambiente"
        
        // Luz Ambiente
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(bounds);
        objRaiz.addChild(luzAmb);        
        
        // Luz Pontual (Color3f c, Point3f position, Point3f attenuation)
        Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);        
        Point3f posicaoLuz1   = new Point3f(0.6f, 2.0f, 0.2f); 
        Point3f posicaoLuz2   = new Point3f(-0.6f, 2.0f, -0.2f); 
        Point3f atenuacaoLuz  = new Point3f(0.1f, 0.1f, 0.1f);       
        PointLight luzPont = new PointLight(corLuz, posicaoLuz1, atenuacaoLuz);
        luzPont.setInfluencingBounds(bounds);
        objRaiz.addChild(luzPont);              
        PointLight luzPont2 = new PointLight(corLuz, posicaoLuz2, atenuacaoLuz);
        luzPont2.setInfluencingBounds(bounds);
        objRaiz.addChild(luzPont2);  
        
        ObjectFile f =
                  new ObjectFile(ObjectFile.RESIZE,(float)(60.0*Math.PI/180.0));        
        Scene s = null;
        
        try {
            s=f.load(new java.net.URL(getCodeBase().toString() + "./galleon.obj")); 
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        catch (ParsingErrorException e) {
            System.err.println(e);
            System.exit(1);
        }
        catch (IncorrectFormatException e) {
            System.err.println(e);
            System.exit(1);
        }
        catch (java.net.MalformedURLException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }         

        objRaiz.addChild(s.getSceneGroup());
    
        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();

        return objRaiz;
    }
	 
}