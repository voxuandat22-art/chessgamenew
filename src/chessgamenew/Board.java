/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessgamenew;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ADMIN
 */
public class Board {
    final int MAX_COL = 8;
    final int MAX_ROW = 8;
    public static final int QRUARE_SIZE = 100;
    public static final int HALF_QRUARE_SIZE = QRUARE_SIZE/2;
    
    public void draw(Graphics2D g2)
    {
        int c = 0;
        for(int row = 0;row < MAX_ROW;row++)
        {
            for(int col = 0; col < MAX_COL; col++)
            {
                if(c == 0)
                {
                    g2.setColor(new Color(210,165,125));
                    c = 1;
                }
                else{
                    g2.setColor(new Color(175,115,70));
                    c = 0;
                }
                g2.fillRect(col*QRUARE_SIZE, row*QRUARE_SIZE, QRUARE_SIZE, QRUARE_SIZE);
            }
            if(c == 0)
            {
                c = 1;
            }
            else{
                c=0;
            }
        }
    }
}
