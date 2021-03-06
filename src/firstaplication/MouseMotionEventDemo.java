package firstAplication;

import com.sun.xml.internal.ws.api.message.Message;
import firstaplication.Chronometer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MouseMotionEventDemo extends JPanel implements MouseMotionListener {

    private static final long TEMPO_INICIAL_CLASSE = System.currentTimeMillis();
    Chronometer cronometro = new Chronometer();
    BlankArea blankArea;
    JTextArea textArea;
    static final String newline = "\n";

    public MouseMotionEventDemo() {
        super(new GridBagLayout());
        GridBagLayout gridbag = (GridBagLayout) getLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(1, 1, 1, 1);
        blankArea = new BlankArea(new Color(0.98f, 0.97f, 0.85f));
        gridbag.setConstraints(blankArea, c);
        add(blankArea);
        c.insets = new Insets(0, 0, 0, 0);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        gridbag.setConstraints(scrollPane, c);
        add(scrollPane);
        //Register for mouse events on blankArea and panel.
        blankArea.addMouseMotionListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(450, 450));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }
 
    public void mouseEntered(MouseEvent e) {
       saySomething("Mouse entro", e);
    }
   
    public void mouseExited(MouseEvent e) {
       saySomething("Mouse saiu", e);
    }

    public void mousePressed(MouseEvent e) {
        saySomething("Mouse Nao sei", e);
    }
    
    public void mouseReleased(MouseEvent e) {
       saySomething("Botao solto", e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        saySomething("Mouse moved", e);
        String metodo = "mouseMoved";
        timeMouse(metodo);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
      saySomething("Botao apertado", e);
    }

    void saySomething(String eventDescription, MouseEvent e) {
        textArea.append(eventDescription + " (" + e.getX() + "," + e.getY() + ")" + " detected on "
                + e.getComponent().getClass().getName() + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.addMouseMotionListener(this);
    }

    public void timeMouse(String metodo) {
        long tempInicial = System.currentTimeMillis();
        for (int i = 0; i <= 1000; i++) {
            System.out.println("Esperando o tempo passar ..");
        }
        long tempFinal = System.currentTimeMillis();
        long dif = (tempFinal - tempInicial);
       
        System.out.println(String.format("%02d segundos  e %02d milisegundos", dif / 1000, dif % 60) + " metodo");
    }

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        //Create and set up the window.
        JFrame frame = new JFrame("MouseMotionEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new MouseMotionEventDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();

            }
        });
    }
}
