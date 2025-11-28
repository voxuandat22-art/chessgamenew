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
public class xe extends piece{
    
    public xe(int color, int col, int row) {
        super(color, col, row);
        
        type = Type.XE;
        
        if(color == GamePanel.WHITE){
            image = getImage("/images/xetrang.png");
        }
        else{
            image = getImage("/images/xeden.png");
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
        }
        return false;
    }
    
}
