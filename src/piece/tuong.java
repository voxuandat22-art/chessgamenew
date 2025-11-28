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
public class tuong extends piece{
    
    public tuong(int color, int col, int row) {
        super(color, col, row);
        
        type = Type.TUONG;
        
                if(color == GamePanel.WHITE){
            image = getImage("/images/tuongtrang.png");
        }
        else{
            image = getImage("/images/tuongden.png");
        }
    }
    public boolean canMove(int targetCol, int targetRow)
    {
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false)
        {
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
