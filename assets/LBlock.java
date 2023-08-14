import javax.swing.*;
import java.awt.*;
public class LBlock extends Block
    {
           private Color color=new Color(218,165,32);
           private Cell a,b,c,d;
           public LBlock()
           {
               super();
               a=new Cell(new Coordinate(1,4),false,color);
               b=new Cell(new Coordinate(2,4),false,color);
               c=new Cell(new Coordinate(3,4),false,color);
               d=new Cell(new Coordinate(3,5),false,color);
               fillCells();
           }
           public void fillCells()
           {
               super.fillCells(a,b,c,d);
           }
    }