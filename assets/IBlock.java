import javax.swing.*;
import java.awt.*;
public class IBlock extends Block
    {
           private Color color = new Color(0,191,255);
           private Cell a = new Cell(new Coordinate(1,4), false, color),
                               b= new Cell(new Coordinate(2,4), false, color),
                               c= new Cell(new Coordinate(3,4), false, color),
                               d= new Cell(new Coordinate(4,4), false, color);
           public IBlock()
           {
                super();
                fillCells();
            }
           public void fillCells()
           {
               super.fillCells(a,b,c,d);
            }
    }