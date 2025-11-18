package blackholegame;

import java.util.Objects;

public class Pos {
    
    public final int r, c;
    
    public Pos(int r, int c){
        this.r = r; 
        this.c = c; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;   
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Pos other = (Pos) obj;
        return this.r == other.r && this.c == other.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }

}
