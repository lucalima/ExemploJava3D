/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstaplication;

import java.util.Comparator;

/**
 *
 * @author Luiz Brandão
 */
public class PorTempo implements Comparator<Timeline> {
    @Override
    public int compare(Timeline o1, Timeline o2) {
        return ((Long)o1.getTempo()).compareTo(o2.getTempo());
    }
}
