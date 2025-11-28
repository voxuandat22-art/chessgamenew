package chessgamenew;


import javax.swing.JFrame;

public class main {

    public static void main(String[] args) {
        
        JFrame Window = new JFrame("chess game");
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.setResizable(false);
                
        
        GamePanel gp = new GamePanel();
        Window.add(gp);
        Window.pack();
        
        Window.setLocationRelativeTo(null);
        Window.setVisible(true);

        
        gp.launchGame();
    }
    
}
