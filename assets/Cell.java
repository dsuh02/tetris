import javax.swing.*;
import java.awt.*;

public class Cell
{
    private boolean set;
    private Coordinate coord;
    private Color color;
    public Cell(Coordinate coord, boolean set, Color color)
    {
        this.coord=coord;
        this.color=color;
        this.set=set;
    }

    public Coordinate getCoord(){
        return coord;
    }
    public void setColor(Color color){
        this.color=color;
    }
    public Color getColor(){
        return color;
    }
    public void makeSet(boolean set){
        this.set=set;
    }
    public boolean getSet()
        {
        return set;
        }
}


