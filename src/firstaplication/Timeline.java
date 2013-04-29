/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstaplication;

/**
 *
 * @author Luiz Brandão
 */
public class Timeline {
    private long tempo;
    private long x;
    private long y;
    private long z;
    private long clique;
    
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (tempo ^ (tempo >>> 32));
        return result;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Timeline)) {
            return false;
        }
        Timeline other = (Timeline) obj;
        if(tempo != other.getTempo()) {
            return false;
        }
        return true;
    }
    /**
     * @return the tempo
     */
    public long getTempo() {
        return tempo;
    }

    /**
     * @param tempo the tempo to set
     */
    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    /**
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public long getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public long getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(long z) {
        this.z = z;
    }

    /**
     * @return the clique
     */
    public long getClique() {
        return clique;
    }

    /**
     * @param clique the clique to set
     */
    public void setClique(long clique) {
        this.clique = clique;
    }
}
