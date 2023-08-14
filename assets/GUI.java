import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.event.*; 
import java.util.Date;
import java.util.concurrent.TimeUnit;



public class GUI extends JFrame 
{
    private GridView view;
    Cell[][] cells;
    final Color WHITE = new Color(255,255,255);
    
    
    //cover
    JLabel cover = new JLabel();
    
    //rules cover
    JLabel rules = new JLabel();
    
    //game
    JTextField jtf1 = new JTextField();
    JLabel title = new JLabel();
    JButton reset = new JButton();
    JLabel pointCount = new JLabel("Points: 0");
    
    //loss page
    JLabel congrats = new JLabel("Good job! You had a score of: ",SwingConstants.CENTER);
    JLabel congratsPic = new JLabel();
    JLayeredPane layer = new JLayeredPane();
    JLabel pressKey = new JLabel("Press any key to restart...", SwingConstants.CENTER);
    
    Block[] b = new Block[7];

    int points=0;
    boolean cover1Cont=false, cover2Cont=false, cont=false;
    public GUI()
    {
        super("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(1000,15);
        setupGrid();
        pack();
        setLayout(null);
        setBackground(Color.black);
        setResizable(false);
        setSize(410,1000);
        setVisible(true);
        
        //textfield stuff
        jtf1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jtf1.setVisible(true);
        jtf1.addKeyListener(listener);
        this.add(jtf1);
        jtf1.setBounds(0,0,1,1);
        
        //cover stuff
        cover.setIcon(new ImageIcon("coverpage.png"));
        this.add(cover);
        cover.setRequestFocusEnabled(false);
        cover.setBounds(0,0,410,1000);
        cover.setVisible(true);
        cover1Cont=false;
        while (cover1Cont ==false){
            jtf1.requestFocus();
        }
        cover.setVisible(false);
        
        //rules stuff
        rules.setIcon(new ImageIcon("rules.png"));
        this.add(rules);
        rules.setRequestFocusEnabled(false);
        rules.setBounds(0,0,410,1000);
        rules.setVisible(true);
        cover2Cont=false;
        while (cover2Cont==false){
            jtf1.requestFocus();
        }
        rules.setVisible(false);
        
        setSize(305,900);
        
        //"title" = top banner stuff
        title.setIcon( new ImageIcon("Tetris.png"));
        this.add(title);
        title.setBounds(0,0,300,146);
        
        //button stuff
        reset.setIcon(new ImageIcon("reset.png"));
        this.add(reset);
        reset.setBounds(150,760,100,100);
        reset.setBorderPainted(false);
        reset.setRequestFocusEnabled(false);
        reset.setToolTipText("Resets the game");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource().equals(reset)){
                    resetGame();
                    points=0;
                }
            }
        });
        
        //points stuff
        this.add(pointCount);
        pointCount.setFont(new Font("Georgia", Font.PLAIN, 20));
        pointCount.setBounds(50,760,200,50);
        
        //loss stuff
        congratsPic.setIcon(new ImageIcon("loss.jpg"));
        congratsPic.setBounds(0,0,512,480);
        //
        congrats.setForeground(Color.white);
        congrats.setFont(new Font("Georgia", Font.PLAIN, 25));
        //
        pressKey.setForeground(Color.white);
        pressKey.setFont(new Font("Georgia", Font.PLAIN,15));
        //
        layer.setLayout(null);
        layer.add(congrats,0,0);
        layer.add(pressKey,0,0);
        layer.add(congratsPic,0,-1);
        //
        pressKey.setBounds(65,350,400,30);
        congrats.setBounds(65,250,400,30);
        layer.setBounds(0,0,512,480);
        //
        this.add(layer);
        congratsPic.setVisible(false);
        congrats.setVisible(false);
        layer.setVisible(false);
        
        //grid stuff
        view.setLocation(0,146);
        
        setSize(305,900);
        cells =new Cell[20][10];
        resetGame();
        
        
        b[0] = new SBlock();
        b[1] = new TBlock();
        b[2] = new JBlock();
        b[3] = new ZBlock();
        b[4] = new LBlock();
        b[5] = new OBlock();
        b[6] = new IBlock();
        
        //int count =0;
        while(1==1){
            pointCount.setText("Points: "+ points);
            jtf1.requestFocus();
            try{
                Thread.sleep(250);
                step();
                showCells();
                if(canSpawn()){
                      Thread.sleep(200);
                      int ind = (int)(Math.random()*7);
                      Cell[] temp = b[ind].getCells();
                      for(int i=0;i<temp.length;i++){
                           if(!(cells[temp[i].getCoord().getR()][temp[i].getCoord().getC()].getColor().equals(WHITE))){
                               resetGame();
                               callLoss();
                               points=0;
                            }
                        }
                      transferCells(b[ind]); 
                }
                //System.print(count++ + " ");
            }
            catch(InterruptedException e){}
            for(int r=0; r<cells.length; r++){
                if(rowFull(r)) clearRow(r);
            }
        }
    }
    public boolean canSpawn()
    {
        for(int r=0; r<cells.length; r++){
            for(int c=0; c<cells[0].length; c++){
                if(!cells[r][c].getColor().equals(WHITE) && cells[r][c].getSet()==false)
                    return false;
            }
        }
        return true;
    }
    public int rowStart(){
        int row = Integer.MAX_VALUE;
        for(int r =0; r<cells.length; r++){
            for(int c= 0; c<cells[0].length; c++){
                if(cells[r][c].getSet()==false && !(cells[r][c].getColor().equals(WHITE)))
                    if(r<row) row=r;
            }
        }
        return row;
    }
    
    public int colStart(){
        int col = Integer.MAX_VALUE;
         for(int r =0; r<cells.length; r++){
            for(int c= 0; c<cells[0].length; c++){
                if(cells[r][c].getSet()==false && !(cells[r][c].getColor().equals(WHITE)))
                    if(c<col) col=c;
            }
        }
        return col;
    }
    
    public int[] getBox(){
        int[] box = new int[4];//{row start, row end, col start, col end}
        
        box[0] = rowStart();
        box[1] = rowStart()+3;
        box[2] = colStart();
        box[3] = colStart()+3;
        
        return box;
    }
    public boolean canRotate(){
        for(int r = getBox()[0]; r<=getBox()[1];r++){
            for(int c = getBox()[2]; c<=getBox()[3];c++){
                if(cells[r][c].getSet()==true){
                    //System.out.println("canRotate is FALSE");
                    return false;
                }
            }
        }
        //System.out.println("canRotate is TRUE");
        return true;
    }
    public Cell getBlock(){
        for(int r=0;r<cells.length;r++){
            for(int c=0;c<cells[0].length;c++){
                if(cells[r][c].getSet()==false&&!(cells[r][c].getColor().equals(WHITE))) return cells[r][c];
            }
        }
        return null;
    }
    public boolean equals(Color a, Color b)
    {
        if(a.getRed()==b.getRed() && 
            a.getGreen()==b.getGreen() &&
            a.getBlue()==b.getBlue())
            return true;
        return false;
    }
    public void rotate()
    {
        int[] box = getBox();//{row start, row end, col start, col end}
        Cell[][] tempBox= new Cell[4][4];
        if(equals(getBlock().getColor(),new Color(255,255,0))) return;
        for(int r=box[0],i=0; r<=box[1]; r++,i++){
            for(int c=box[2],j=0; c<=box[3]; c++,j++){
                tempBox[i][j]=cells[r][c];
            }
        }
        for(int r=box[0]; r<=box[1]; r++){
            for(int c=box[2]; c<=box[3]; c++){
                cells[r][c]=new Cell(new Coordinate(r,c),false,WHITE);
            }
        }
        for(int i=0, c=box[3]; i<tempBox.length; i++,c--){
            for(int j=0, r=box[0]; j<tempBox[0].length;r++, j++){
                cells[r][c]=tempBox[i][j];
            }
        }
    }
    public void callLoss()
    {
        title.setVisible(false);
        reset.setVisible(false);
        pointCount.setVisible(false);
        view.setVisible(false);
        this.setSize(515,505);
        
        congratsPic.setVisible(true);
        congrats.setVisible(true);
        layer.setVisible(true);
        congrats.setText("Good job! You had a score of: "+ points+ "!");
        cont=false;
        while (cont==false){
            jtf1.requestFocus();
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){}
        }
        cont=true;
        layer.setVisible(false);
        setSize(305,900);
        title.setVisible(true);
        reset.setVisible(true);
        pointCount.setVisible(true);
        view.setVisible(true);
    }
    KeyListener listener = new KeyListener(){
        public void keyPressed(KeyEvent e){
            cover1Cont=true;
            cover2Cont=true;
            cont=true;
            try{
                if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
                    shiftR();
                    //System.out.println();
                    showCells();
                }
                else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
                    shiftL();
                    //System.out.println();
                    showCells();
                }
                else if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    step();
                    
                    showCells();
                    //System.out.println("vk down");
                }
                else if(e.getKeyCode()==KeyEvent.VK_SHIFT){//space
                    if(canRotate()){
                        rotate();
                        //System.out.println("shift");
                        showCells();
                    }
                }
            }
            catch(Exception ex){}
            
        }
        public void keyReleased(KeyEvent e){
            
        }
        public void keyTyped(KeyEvent e){
            
        }
    };
    private boolean rowFull(int r){
        for(int c=0; c<cells[0].length; c++){
            if(cells[r][c].getSet()==false) return false;
        }
        return true;
    }
    private void resetGame(){
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                cells[i][j]=new Cell(new Coordinate(i,j),false,WHITE);
            }
        }
    }
    private void transferCells(Block b)
    {
        Cell[] temp=b.getCells();
        for(int i =0;i<temp.length;i++){
            cells[temp[i].getCoord().getR()][temp[i].getCoord().getC()]=
                new Cell(temp[i].getCoord(),false,temp[i].getColor());
        }
    }
    private void setupGrid()
    {
        Container contents = getContentPane();
        view = new GridView();
        this.add(view);
    }

    private void clearRow(int row){
        for(int r=row; r>0; r--){
            for(int c=0; c<cells[0].length;c++){
                cells[r][c]=new Cell(new Coordinate(r,c), false, WHITE);
                Cell temp = cells[r][c];
                cells[r][c]=cells[r-1][c];
                cells[r-1][c]= cells[r][c];
            }
        }
        points++;
    }

    public void showCells()
    {
        if(!isVisible()) {
            setVisible(true);
        }
        view.preparePaint();
        for(int row = 0; row < cells.length; row++) {
            for(int col = 0; col < cells[0].length; col++) {
                view.drawMark(col, row, cells[row][col].getColor());
            }
        }
        view.repaint();
    }
    
    
    public void step()
    {
        if(willCollide()){
            //System.out.println("will hit bottom");
            makeAllSet(true);
        }
        if(canStep()==false){
            makeAllSet(true);
            //System.out.println("hit bottom");
        }
        else {
            for(int i=cells.length-2; i>=0; i--){
                for(int j=0; j<cells[0].length; j++){
                    if(cells[i][j].getSet()==false && cells[i+1][j].getSet()==false){
                    Cell temp = cells[i+1][j];
                    cells[i+1][j]=cells[i][j];
                    cells[i][j]=temp;
                }
                }
            }
        }
    }
    public boolean canStep()
    {
        for(int c=0; c<cells[0].length; c++){
            if(cells[cells.length-1][c].getSet()==false &&
                !(cells[cells.length-1][c].getColor().equals(WHITE)))
                return false;
        }
        return true;
    }
        
    
    public void shiftR(){
        if(canShiftR()){
            for(int c=cells[0].length-2; c>=0; c--){
                for(int r=0; r<cells.length;r++){
                    if(cells[r][c].getSet()==false && !cells[r][c].getColor().equals(WHITE)){
                        Cell temp = cells[r][c];
                        cells[r][c]=cells[r][c+1];
                        cells[r][c+1]=temp;
                    }
                }
            }
        }
    }
    public boolean canShiftR(){
        for(int c=cells[0].length-2; c>=0; c--){
            for(int r=0; r<cells.length;r++){
                if(cells[r][c].getSet()==false && 
                    !(cells[r][c].getColor().equals(WHITE)) &&
                    cells[r][c+1].getSet()==true &&
                    !(cells[r][c+1].getColor().equals(WHITE))){
                        //System.out.println("will run into right brick");
                        return false;
                }
            }
        }

        for(int r=0; r<cells.length;r++){
            if(cells[r][cells[0].length-1].getSet()==false && 
                !(cells[r][cells[0].length-1].getColor().equals(WHITE))){
                  //System.out.println("will run into right wall");
                  return false;
            }
        }
        return true;
    }
    public void shiftL(){
        if(canShiftL()){
            for(int c=1; c<cells[0].length; c++){
                for(int r=0; r<cells.length;r++){
                    if(cells[r][c].getSet()==false && !cells[r][c].getColor().equals(WHITE)){
                        Cell temp = cells[r][c];
                        cells[r][c]=cells[r][c-1];
                        cells[r][c-1]=temp;
                    }
                }
            }
        }
    }
    public boolean canShiftL(){
        for(int c=1; c<cells[0].length; c++){
            for(int r=0; r<cells.length;r++){
                if(cells[r][c].getSet()==false && 
                    !(cells[r][c].getColor().equals(WHITE)) &&
                    cells[r][c-1].getSet()==true &&
                    !(cells[r][c-1].getColor().equals(WHITE))){
                        //System.out.println("will run into left brick");
                        return false;
                }
            }
        }
        for(int r=0; r<cells.length;r++){
            if(cells[r][0].getSet()==false && 
                !(cells[r][0].getColor().equals(WHITE))){
                  //System.out.println("will run into left wall");
                  return false;
            }
        }
        return true;
    }
    public void makeAllSet(boolean set){
        for(int r=0;r<cells.length;r++){
            for(int c=0; c<cells[0].length; c++){
                if(!(cells[r][c].getColor().equals(WHITE)))
                    cells[r][c].makeSet(set);
            }
        }
    }
    public boolean willCollide(){
        for(int r=cells.length-2; r>=0; r--){
            for(int c=cells[0].length-1; c>=0; c--){
                if(cells[r][c].getSet()==false && 
                    !(cells[r][c].getColor().equals(WHITE)) && 
                    cells[r+1][c].getSet()==true &&
                    !(cells[r+1][c].getColor().equals(WHITE)))
                    return true;
                
            }
        }
        return false;
    }
    
    
    private class GridView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 30;

        private final int gridWidth, gridHeight;
        private int xScale, yScale;
        private Dimension size;
        private Graphics g;
        private Image fieldImage;

        public GridView()
        {
            gridHeight = 20;
            gridWidth = 10;
            size = new Dimension(0,0);
        }
        
        public Dimension getPreferredSize()
        {
            return new Dimension(300,600);
        }

        
        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {
                size = getSize();
                fieldImage = view.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        @Override
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
    }
