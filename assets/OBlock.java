import javax.swing.*;
import java.awt.*;
public class OBlock extends Block
    {
           private Color color=new Color(255,255,0);
           private Cell a,b,c,d;
           public OBlock()
           {
               super();
               a=new Cell(new Coordinate(1,4),false,color);
               b=new Cell(new Coordinate(1,5),false,color);
               c=new Cell(new Coordinate(2,4),false,color);
               d=new Cell(new Coordinate(2,5),false,color);
               fillCells();
           }
           public void fillCells()
           {
               super.fillCells(a,b,c,d);
            }
    }