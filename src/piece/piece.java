package piece;

import chessgamenew.Board;
import chessgamenew.GamePanel;
import chessgamenew.Type;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class piece {
    
    public Type type;
    public BufferedImage image;
    public int x,y;
    public int col,row,preCol,preRow;
    public int color;
    public piece hittingP;
    public boolean moved,twoStepped;
    
    public piece(int color, int col, int row) 
    {
        this.color = color;
        this.col = col;
        this.row = row;
        x=getX(col);
        y=getY(row);
        preCol = col;
        preRow = row;
    }
public BufferedImage getImage(String imagePath) {
    try {
        return ImageIO.read(getClass().getResourceAsStream(imagePath));
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}




    public int getX(int col) {
        return col * Board.QRUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.QRUARE_SIZE;
    }
    
    public int getCol(int x){
        return (x + Board.HALF_QRUARE_SIZE)/Board.QRUARE_SIZE;
    }
    
     public int getRow(int y){
        return (y + Board.HALF_QRUARE_SIZE)/Board.QRUARE_SIZE;
    }       
     
     public int getIndex()
     {
         for(int index = 0; index < GamePanel.simpieces.size(); index++)
         {
             if(GamePanel.simpieces.get(index) == this)
             {
                 return index;
             }
         }
         return 0;
     }
    
     public void updatePosition()
     {
         
         if(type == Type.TOT)
         {
             if(Math.abs(row - preRow) == 2)
             {
                 twoStepped = true;
             }
         }
         
         x = getX(col);
         y = getY(row);
         preCol = getCol(x);
         preRow = getRow(y);
         moved = true;
     }
     
     public void resetPosition()
     {
         col = preCol;
         row = preRow;
         x = getX(col);
         y = getY(row);
     }
     public boolean canMove(int targetCol, int targetRow)
     {
         return false;
     }
     
     public boolean isWithinBoard(int targetCol, int targetRow )
     {
         if(targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <=7)
         {
             return true;
         }
         return false;
     }
     
     public boolean isSameSquare(int targetCol, int targetRow)
     {
         if(targetCol == preCol && targetRow == preRow)
         {
             return true;
         }
         return false;
     }
     
     public piece getHittingP(int targetCol, int targetRow)
     {
         for(piece piece : GamePanel.simpieces)
         {
             if(piece.col == targetCol && piece.row == targetRow && piece != this)
             {
                 return piece;
             }
         }
         return null;
     }
     
     public boolean isValidSquare(int targetCol, int targetRow)
     {
         hittingP = getHittingP(targetCol, targetRow);
         
         if(hittingP == null)
         {
             return true;
         }
         else{
             if(hittingP.color != this.color)
             {
                 return true;
             }
             else{
                 hittingP = null;
             }
         }
         return false;
     }
     
     public boolean pieceIsOnStraightLine(int targetCol, int targetRow)
     {
         for(int c = preCol-1; c > targetCol; c--)
         {
             for(piece piece : GamePanel.simpieces)
             {
                 if(piece.col == c && piece.row == targetRow)
                 {
                     hittingP = piece;
                     return true;
                 }
             }
         }
         
         for(int c = preCol + 1; c < targetCol; c++)
         {
             for(piece piece : GamePanel.simpieces)
             {
                 if(piece.col == c && piece.row == targetRow)
                 {
                     hittingP = piece;
                     return true;
                 }
             }
         }

         for(int r = preRow - 1; r > targetRow; r--)
         {
             for(piece piece : GamePanel.simpieces)
             {
                 if(piece.col == targetCol && piece.row == r)
                 {
                     hittingP = piece;
                     return true;
                 }
             }
         }

         for(int r = preRow + 1; r < targetRow; r++)
         {
             for(piece piece : GamePanel.simpieces)
             {
                 if(piece.col == targetCol && piece.row == r)
                 {
                     hittingP = piece;
                     return true;
                 }
             }
         }        
        return false;
     }
     public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow)
     {
         if(targetRow < preRow)
         {
             for(int c = preCol-1; c>targetCol;c--)
             {
                 int diff = Math.abs(c - preCol);
                 for(piece piece : GamePanel.simpieces)
                 {
                     if(piece.col == c && piece.row == preRow - diff)
                     {
                         hittingP = piece;
                         return true;
                     }
                 }
             }
             for(int c = preCol+1; c < targetCol;c++)
             {
                 int diff = Math.abs(c - preCol);
                 for(piece piece : GamePanel.simpieces)
                 {
                     if(piece.col == c && piece.row == preRow - diff)
                     {
                         hittingP = piece;
                         return true;
                     }
                 }
             }             
         }
         if(targetRow > preRow)
         {
             for(int c = preCol-1; c>targetCol;c--)
             {
                 int diff = Math.abs(c - preCol);
                 for(piece piece : GamePanel.simpieces)
                 {
                     if(piece.col == c && piece.row == preRow + diff)
                     {
                         hittingP = piece;
                         return true;
                     }
                 }
             }
             for(int c = preCol+1; c<targetCol;c++)
             {
                 int diff = Math.abs(c - preCol);
                 for(piece piece : GamePanel.simpieces)
                 {
                     if(piece.col == c && piece.row == preRow + diff)
                     {
                         hittingP = piece;
                         return true;
                     }
                 }
             }
         }
         return false;
     }
     
     
    public void draw(Graphics2D g2)
    {
        g2.drawImage(image, x, y, Board.QRUARE_SIZE, Board.QRUARE_SIZE, null);
    }
}
