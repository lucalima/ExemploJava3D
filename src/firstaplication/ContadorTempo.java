/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstaplication;

import sun.tools.jar.Main;


/**
 *
 * @author Lucas Lima
 */
public class ContadorTempo {

    public static long tempInicial;
    public static long tempFinal;

    public static void comecar() {
        tempInicial = System.currentTimeMillis();
    }

    public static void parar() {
        tempFinal = System.currentTimeMillis();

        long dif = (tempFinal - tempInicial);
        //status é um JLabel q tenho na minha aplicacao.  
        //Main.status.setText(String.format("Tempo de execução: %02d segundos  e %02d milisegundos", dif / 1000, dif % 1000));
    }
}
