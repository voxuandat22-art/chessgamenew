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
public class hau extends piece{
    
    public hau(int color, int col, int row) {
        super(color, col, row);
        
        type = Type.HAU;
        
                if(color == GamePanel.WHITE){
            image = getImage("/images/hautrang.png");
        }
        else{
            image = getImage("/images/hauden.png");
        }
    }
    
        public boolean canMove(int targetCol, int targetRow)
    {
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false)
        {
            if(targetCol == preCol || targetRow == preRow)
            {
                if(isValidSquare(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow) == false)
                {
                    return true;
                }
            }
            
            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow))
            {
                if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
}
