/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package piece;

import chessgamenew.GamePanel;
import chessgamenew.Type;

/**
 *
 * @author ADMIN
 */
public class vua extends piece{
    
    public vua(int color, int col, int row) {
        super(color, col, row);
        
        type = Type.VUA;
        
                if(color == GamePanel.WHITE){
            image = getImage("/images/vuatrang.png");
        }
        else{
            image = getImage("/images/vuaden.png");
        }
    }
    
     public boolean canMove(int targetCol, int targetRow)
     {
         if(isWithinBoard(targetCol,targetRow))
         {
             if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 || 
                     Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1)
             {
                 if(isValidSquare(targetCol, targetRow))
                 {
                 return true;
                 }
             }
             if(moved == false)
             {
                 if(targetCol == preCol + 2 && targetRow == preRow && pieceIsOnStraightLine(targetCol, targetRow) == false)
                 {
                     for(piece piece : GamePanel.simpieces)
                     {
                         if(piece.col == preCol + 3 && piece.row == preRow && piece.moved == false)
                         {
                             GamePanel.castlingP = piece;
                             return true;
                         }
                     }
                 }
                 if(targetCol == preCol - 2 && targetRow == preRow && pieceIsOnStraightLine(targetCol, targetRow) == false)
                 {
                     piece p[] = new piece[2];
                     for(piece piece : GamePanel.simpieces)
                     {
                         if(piece.col == preCol - 3 && piece.row == targetRow)
                         {
                             p[0] = piece;
                         }
                         if(piece.col == preCol - 4 && piece.row == targetRow)
                         {
                             p[1] = piece;
                         }
                         System.out.println(p[1]);
                         
                         if(p[0] == null && p[1] != null && p[1].moved == false)
                         {
                             GamePanel.castlingP = p[1];
                             return true;
                         }
                     }
                 }
                 
                 
             }
         }
         return false;
     }    
}
