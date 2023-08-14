import javax.swing.*;
import java.awt.*;

public abstract class Block
{
    private Cell[] cells;
    public Block()
    {
        cells = new Cell[4];
        cells[0]=null;
        cells[1]=null;
        cells[2]=null;
        cells[3]=null;
    }
    public Cell[] getCells(){
        return cells;
    }
    public void fillCells(Cell a, Cell b, Cell c, Cell d){
        cells[0]=a;
        cells[1]=b;
        cells[2]=c;
        cells[3]=d;
    }
}
    


