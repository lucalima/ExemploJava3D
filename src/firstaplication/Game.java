/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstaplication;

/**
 *
 * @author Lucas Lima
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements MouseListener, MouseMotionListener {

    BufferedImage backBuffer;
    int FPS = 30;
    int janelaW = 500;
    int janelaH = 500;
    ImageIcon mira = new ImageIcon("src/tutor13/mira.png");
    int xMira;
    int yMira;
    long tempoInicial;
    long tempoFinal;
    long tempoInicialParado;
    long tempoFinalParado;

    public void atualizar() {
    }

    public void desenharGraficos() {
        Graphics g = getGraphics(); //ISSO JÁ ESTAVA AQUI
        Graphics bbg = backBuffer.getGraphics();//ISSO TAMBÉM JÁ ESTAVA AQUI...
        bbg.setColor(Color.WHITE);
        bbg.fillRect(0, 0, janelaW, janelaH); //PINTA O FUNDO COM UM QUADRADO BRANCO
        bbg.setColor(Color.RED);
        bbg.setFont(new Font("helvica", Font.BOLD, 20));
        bbg.drawString("X=: " + xMira + "  Y=" + yMira, 50, 100); //EXIBE UM TEXTO + O VALOR DA TECLA PRESSIONADA



        bbg.drawImage(mira.getImage(), xMira - 25, yMira - 25, this);


        g.drawImage(backBuffer, 0, 0, this);//OBS: ISSO DEVE FICAR SEMPRE NO FINAL!
    }

    public void inicializar() {
        setTitle("Titulo do Jogo!");
        setSize(janelaW, janelaH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        backBuffer = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);


        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void run() {
        inicializar();
        while (true) {
            atualizar();
            desenharGraficos();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                System.out.println("Thread interrompida!");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicou");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entro");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Saiu");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tempoInicial = System.currentTimeMillis();
        System.out.println("precionou");
        tempoFinalParado = System.currentTimeMillis();
        System.out.println("Tempo parado "+tempoParado(tempoInicialParado, tempoFinalParado));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        tempoFinal = System.currentTimeMillis();
        tempoInicialParado = tempoFinal;
        System.out.println("soltou " + tempoFinal);
        System.out.println("Tempo decorrido foi de " + tempoDecorrido(tempoInicial, tempoFinal));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("Arrastando " + tempoInicial);
    }
    
    public long tempoDecorrido(long tempoInicial,long tempoFinal){
        long tempoDecorrido = tempoFinal - tempoInicial;
        return tempoDecorrido/1000;
    }
    
    public long tempoParado(long tempoInicialParado, long tempoFinalParado){
        long tempoDecorridoParado = tempoFinalParado - tempoInicialParado;
        return tempoDecorridoParado/1000;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        //AQUI X e Y DA MIRA RECEBE AS COORDENADAS DO CURSOR
        xMira = e.getX();
        yMira = e.getY();
    }

    /**
     * @return the tempoInicial
     */
    public long getTempoInicial() {
        return tempoInicial;
    }

    /**
     * @param tempoInicial the tempoInicial to set
     */
    public void setTempoInicial(long tempoInicial) {
        this.tempoInicial = tempoInicial;
    }

    /**
     * @return the tempoFinal
     */
    public long getTempoFinal() {
        return tempoFinal;
    }

    /**
     * @param tempoFinal the tempoFinal to set
     */
    public void setTempoFinal(long tempoFinal) {
        this.tempoFinal = tempoFinal;
    }
}
