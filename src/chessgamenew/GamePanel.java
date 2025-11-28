package chessgamenew;

import static chessgamenew.Type.XE;
import piece.ma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import java.util.ArrayList;
import piece.hau;
import piece.piece;
import piece.tot;
import piece.tuong;
import piece.vua;
import piece.xe;

public class GamePanel extends JPanel implements Runnable{



    public static final int WIDTH = 1100;
    public static final int REIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    
public static ArrayList<piece> pieces = new ArrayList<>();
public static ArrayList<piece> simpieces = new ArrayList<>();
ArrayList<piece> promopieces = new ArrayList<>();
piece activeP, checkingP;
public static piece castlingP;


    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;
    private Mouse mouse = new Mouse();

    
    boolean canMove;
    boolean validSquare;
    boolean promotion;
    boolean gameover;
    
    public GamePanel()
    {
        setPreferredSize(new Dimension(WIDTH,REIGHT));
        setBackground(Color.GRAY);
addMouseListener((MouseListener) mouse);
addMouseMotionListener(mouse);
        
        setPiece();
        copyPieces(pieces, simpieces);
    }
    
    public void launchGame()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void setPiece(){
        
        pieces.add(new tot(WHITE,0,6));
        pieces.add(new tot(WHITE,1,6));
        pieces.add(new tot(WHITE,2,6));
        pieces.add(new tot(WHITE,3,6));
        pieces.add(new tot(WHITE,4,6));
        pieces.add(new tot(WHITE,5,6));
        pieces.add(new tot(WHITE,6,6));
        pieces.add(new tot(WHITE,7,6));
        pieces.add(new xe(WHITE,0,7));
        pieces.add(new xe(WHITE,7,7));
        pieces.add(new ma(WHITE,1,7));
        pieces.add(new ma(WHITE,6,7));
        pieces.add(new tuong(WHITE,2,7));
        pieces.add(new tuong(WHITE,5,7));
        pieces.add(new hau(WHITE,3,7));
        pieces.add(new vua(WHITE,4,7));
        
        
        pieces.add(new tot(BLACK,0,1));
        pieces.add(new tot(BLACK,1,1));
        pieces.add(new tot(BLACK,2,1));
        pieces.add(new tot(BLACK,3,1));
        pieces.add(new tot(BLACK,4,1));
        pieces.add(new tot(BLACK,5,1));
        pieces.add(new tot(BLACK,6,1));
        pieces.add(new tot(BLACK,7,1));
        pieces.add(new xe(BLACK,0,0));
        pieces.add(new xe(BLACK,7,0));
        pieces.add(new ma(BLACK,1,0));
        pieces.add(new ma(BLACK,6,0));
        pieces.add(new tuong(BLACK,2,0));
        pieces.add(new tuong(BLACK,5,0));
        pieces.add(new hau(BLACK,3,0));
        pieces.add(new vua(BLACK,4,0));
        
    }
    
private void copyPieces(ArrayList<piece> source, ArrayList<piece> target)
{
    target.clear();
    for (int i = 0; i < source.size(); i++) {
        target.add(source.get(i));
    }
}

    
        @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            
            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
            }
        }
    }
    
    private void update()
    {
        if(promotion)
        {
            promoting();
        }
        else if(gameover == false){
                    if(mouse.pressed)
        {
            if(activeP == null)
            {
                for(piece piece : simpieces)
                {
                    if(piece.color == currentColor && piece.col == mouse.x/Board.QRUARE_SIZE && piece.row == mouse.y/Board.QRUARE_SIZE)
                    {
                        activeP = piece;
                    }
                }
            }
            else{
                simulate();
            }
        }
        
        if(mouse.pressed == false)
        {
            if(activeP != null)
            {
                if(validSquare)
                {
                    copyPieces(simpieces, pieces);
                    activeP.updatePosition();
                if(castlingP != null)
                {
                    castlingP.updatePosition();
                }
                
                if(isKingInCheck() && isCheckmate())
                {
                    gameover = true;
                }
                else{
                if(canPromote())
                {
                promotion = true;
                }
                else{
                    changePlayer();
                }
                }
                }
                else{
                    copyPieces(pieces, simpieces);
                    activeP.resetPosition();
                    activeP = null;
                }
            }
        }  
    }
        
 
    }
    
    private void simulate(){
        
        canMove = false;
        validSquare = false;
        
        copyPieces(pieces, simpieces);
        
        if(castlingP != null)
        {
            castlingP.col = castlingP.preCol;
            castlingP.x = castlingP.getX(castlingP.col);
            castlingP = null;
        }
        
        activeP.x = mouse.x - Board.HALF_QRUARE_SIZE;
        activeP.y = mouse.y - Board.HALF_QRUARE_SIZE;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);
        
        if(activeP.canMove(activeP.col, activeP.row))
        {
            canMove = true;
            
            if(activeP.hittingP != null)
            {
                simpieces.remove(activeP.hittingP.getIndex());
            }
            
            checkCastlig();
            
            if(isIllegal(activeP) == false && opponentCanCaptureKing() == false){
            validSquare = true;
            }
        }
    }
    
    private boolean isIllegal(piece vua)
    {
        if(vua.type == Type.VUA)
        {
            for(piece piece : simpieces)
            {
                if(piece != vua && piece.color != vua.color && piece.canMove(vua.col, vua.row))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean opponentCanCaptureKing()
    {
        piece vua = getVua(false);
        for(piece piece : simpieces)
        {
            if(piece.color != vua.color && piece.canMove(vua.col, vua.row))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isKingInCheck()
    {
        piece vua = getVua(true);
        if(activeP.canMove(vua.col, vua.row))
        {
            checkingP = activeP;
            return true;
        }
        
        return false;
    }
    
    private piece getVua(boolean opponent)
    {
        piece vua = null;
        for(piece piece : simpieces)
        {
            if(opponent)
            {
                if(piece.type == Type.VUA && piece.color != currentColor)
                {
                    vua = piece;
                }
            }
            else{
                if(piece.type == Type.VUA && piece.color == currentColor)
                {
                    vua = piece;
                }
            }
        }
        return vua;
    }
    
    private boolean isCheckmate()
    {
        piece vua = getVua(true);
        if(kingCanMove(vua))
        {
            return false;
        }
        else{
            int colDiff = Math.abs(checkingP.col - vua.col);
            int rowDiff = Math.abs(checkingP.row - vua.row);
            
            if(colDiff == 0)
            {
                if(checkingP.row < vua.row)
                {
                    for(int row = checkingP.row; row < vua.row; row++)
                    {
                        for(piece piece : simpieces)
                        {
                           if(piece != vua && piece.color != currentColor && piece.canMove(checkingP.col, row))
                           {
                               return false;
                           }
                        }
                    }
                }
                if(checkingP.row > vua.row)
                {
                    for(int row = checkingP.row; row > vua.row; row--)
                    {
                        for(piece piece : simpieces)
                        {
                           if(piece != vua && piece.color != currentColor && piece.canMove(checkingP.col, row))
                           {
                               return false;
                           }
                        }
                    }                    
                }
            }
            else if(rowDiff == 0){
                if(checkingP.col < vua.col)
                {
                    for(int col = checkingP.col ; col < vua.col; col++)
                    {
                        for(piece piece : simpieces)
                        {
                           if(piece != vua && piece.color != currentColor && piece.canMove(col, checkingP.row))
                           {
                               return false;
                           }
                        }
                    }
                }         
                
                if(checkingP.col > vua.col)
                {
                    for(int col = checkingP.col; col > vua.col; col--)
                    {
                        for(piece piece : simpieces)
                        {
                           if(piece != vua && piece.color != currentColor && piece.canMove(col, checkingP.row))
                           {
                               return false;
                           }
                        }
                    }
                }                        
            }
            else if(colDiff == rowDiff)
            {
                if(checkingP.row < vua.row)
                {
                    if(checkingP.col < vua.col)
                    {
                       for(int col = checkingP.col, row = checkingP.row; col < vua.col; col++, row++)
                       {
                           for(piece piece : simpieces)
                           {
                               if(piece != vua && piece.color != currentColor && piece.canMove(col, row))
                               {
                                   return false;
                               }
                           }
                       }
                    }
                    
                    if(checkingP.col > vua.col)
                    {
                       for(int col = checkingP.col, row = checkingP.row; col > vua.col; col--, row++)
                       {
                           for(piece piece : simpieces)
                           {
                               if(piece != vua && piece.color != currentColor && piece.canMove(col, row))
                               {
                                   return false;
                               }
                           }
                       }                        
                    }
                }
                
                if(checkingP.row > vua.row)
                {
                    if(checkingP.col < vua.col)
                    {
                       for(int col = checkingP.col, row = checkingP.row; col < vua.col; col++, row--)
                       {
                           for(piece piece : simpieces)
                           {
                               if(piece != vua && piece.color != currentColor && piece.canMove(col, row))
                               {
                                   return false;
                               }
                           }
                       }                        
                    }
                    
                    if(checkingP.col > vua.col)
                    {
                       for(int col = checkingP.col, row = checkingP.row; col > vua.col; col--, row--)
                       {
                           for(piece piece : simpieces)
                           {
                               if(piece != vua && piece.color != currentColor && piece.canMove(col, row))
                               {
                                   return false;
                               }
                           }
                       }                        
                    }                    
                }
            }
            else{
                
            }
        }
        
        return  true;
    }
    private boolean kingCanMove(piece vua)
    {
        if(isValidMove(vua, -1, -1)) {return true;}
        if(isValidMove(vua, 0, -1)) {return true;}
        if(isValidMove(vua, 1, -1)) {return true;}
        if(isValidMove(vua, -1, 0)) {return true;}
        if(isValidMove(vua, 1, 0)) {return true;}
        if(isValidMove(vua, -1, 1)) {return true;}
        if(isValidMove(vua, 0, 1)) {return true;}
        if(isValidMove(vua, 1, 1)) {return true;}
        
        return false;
    }
    private boolean isValidMove(piece vua,int colPlus, int rowPlus)
    {
        boolean isValidMove = false;
        
        vua.col += colPlus;
        vua.row += rowPlus;
        
        if(vua.canMove(vua.col, vua.row))
        {
            if(vua.hittingP != null)
            {
                simpieces.remove(vua.hittingP.getIndex());
            }
            if(isIllegal(vua) == false)
            {
                isValidMove = true;
            }
        }
        vua.resetPosition();
        copyPieces(pieces, simpieces);
        
        return isValidMove;
    }
    
    private void checkCastlig()
    {
        if(castlingP != null)
        {
            if(castlingP.col == 0)
            {
                castlingP.col += 3;
            }
            else if(castlingP.col == 7){
                castlingP.col -= 2;
            }
            castlingP.x = castlingP.getX(castlingP.col);
        }
    }
    
    private void changePlayer()
    {
        if(currentColor == WHITE)
        {
            currentColor = BLACK;
            
            for(piece piece : pieces)
            {
                if(piece.color == BLACK)
                {
                 piece.twoStepped = false;   
                }
            }
        }
        else{
            currentColor = WHITE;
            
            for(piece piece : pieces)
            {
                if(piece.color == WHITE)
                {
                 piece.twoStepped = false;   
                }
            }
        }
        activeP = null;
    }
    
    private boolean canPromote()
    {
        if(activeP.type == Type.TOT)
        {
            if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row == 7)
            {
                promopieces.clear();
                promopieces.add(new xe(currentColor,9,2));
                promopieces.add(new ma(currentColor,9,3));
                promopieces.add(new tuong(currentColor,9,4));
                promopieces.add(new hau(currentColor,9,5));
                return true;
            }
        }
        return false;
    }
    private void promoting()
    {
        if(mouse.pressed)
        {
            for(piece piece : promopieces)
            {
                if(piece.col == mouse.x/Board.QRUARE_SIZE && piece.row == mouse.y/Board.QRUARE_SIZE)
                {
                  switch(piece.type)
                  {
                      case XE: simpieces.add(new xe(currentColor,activeP.col,activeP.row));break;
                      case TUONG: simpieces.add(new tuong(currentColor,activeP.col,activeP.row));break;
                      case NGUA: simpieces.add(new ma(currentColor,activeP.col,activeP.row));break;
                      case HAU: simpieces.add(new hau(currentColor,activeP.col,activeP.row));break;
                      default: break;
                  }
                  simpieces.remove(activeP.getIndex());
                    copyPieces(simpieces, pieces);
                    activeP = null;
                    promotion = false;
                    changePlayer();
                }
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        board.draw(g2);
    for(piece p : simpieces){
    p.draw(g2);
    }
    
if(activeP != null)
{
    if(canMove)
    {
        if(isIllegal(activeP) || opponentCanCaptureKing())
        {
    g2.setColor(Color.gray);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
    g2.fillRect(activeP.col * Board.QRUARE_SIZE, activeP.row * Board.QRUARE_SIZE, 
                Board.QRUARE_SIZE, Board.QRUARE_SIZE);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); 
        }
        else{
    g2.setColor(Color.white);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
    g2.fillRect(activeP.col * Board.QRUARE_SIZE, activeP.row * Board.QRUARE_SIZE, 
                Board.QRUARE_SIZE, Board.QRUARE_SIZE);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); 
        }
       
    }
    


    activeP.draw(g2);
}

g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g2.setFont(new Font("book Antiqua", Font.PLAIN,40));
g2.setColor(Color.white);

if(promotion)
{
    g2.drawString("phong cap", 840, 150);
    for(piece piece : promopieces)
    {
        g2.drawImage(piece.image, piece.getX(piece.col), piece.getY(piece.row),Board.QRUARE_SIZE,Board.QRUARE_SIZE,null);
    }
}
else{
    if(currentColor == WHITE)
{
    g2.drawString("white's turn", 840, 550);
    if(checkingP != null && checkingP.color == BLACK)
    {
        g2.setColor(Color.red);
        g2.drawString("chieu vua", 840, 650);
        g2.drawString("trang", 840, 700);
    }
}
else{
    g2.drawString("black's turn", 840, 250);
    if(checkingP != null && checkingP.color == WHITE)
    {
        g2.setColor(Color.red);
        g2.drawString("chieu vua", 840, 100);
        g2.drawString("den", 840, 150);
    }    
}
}

if(gameover)
{
    String s = "";
    if(currentColor == WHITE)
    {
        s = "WHITE WINS";
    }
    else{
        s = "BLACK WINS";
    }
    g2.setFont(new Font("Arial", Font.PLAIN,90));
    g2.setColor(Color.blue);
    g2.drawString(s, 200, 420);
}

}

}
