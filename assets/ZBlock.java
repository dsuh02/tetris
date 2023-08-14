import javax.swing.*;
import java.awt.*;
public class ZBlock extends Block
    {
           private Color color=new Color(255,0,0);
           private Cell a,b,c,d;
           public ZBlock()
           {
               super();
               a=new Cell(new Coordinate(1,3),false,color);
               b=new Cell(new Coordinate(1,4),false,color);
               c=new Cell(new Coordinate(2,4),false,color);
               d=new Cell(new Coordinate(2,5),false,color);
               fillCells();
           }
           public void fillCells()
           {
               super.fillCells(a,b,c,d);
           }
    }