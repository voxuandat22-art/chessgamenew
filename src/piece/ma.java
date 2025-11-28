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
public class ma extends piece{

    public ma(int color, int col, int row) {
        super(color, col, row);
        
        type = Type.NGUA;
        
        if(color == GamePanel.WHITE){
            image = getImage("/images/matrang.png");
        }
        else{
            image = getImage("/images/maden.png");
        }
    }
    public boolean canMove(int targetCol, int targetRow)
    {
        if(isValidSquare(targetCol, targetRow))
        {
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2)
            {
                if(isValidSquare(targetCol, targetRow))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
