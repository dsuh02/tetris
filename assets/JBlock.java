import javax.swing.*;
import java.awt.*;
public class JBlock extends Block
    {
           private Color color=new Color(0,0,255);
           private Cell a,b,c,d;
           public JBlock()
           {
               super();
               a=new Cell(new Coordinate(1,5),false,color);
               b=new Cell(new Coordinate(2,5),false,color);
               c=new Cell(new Coordinate(3,4),false,color);
               d=new Cell(new Coordinate(3,5),false,color);
               fillCells();
           }
           public void fillCells()
           {
               super.fillCells(a,b,c,d);
            }
    }