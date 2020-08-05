import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
        Main window = new Main();
        window.run();
    }

    private class Cell {
        private final int x, y, width, height;
    
        public Cell(int x, int y, int w, int h){
            this.x = x;
            this.y = y;
            width = w;
            height = h;
        }
    
        public void paint(Graphics g, Point mousePosition){
            // Check if mouse is hovering over cell
            boolean mouse_hover = 
                (mousePosition.getX() - x < width) && (mousePosition.getX() - x > 0) &&
                (mousePosition.getY() - y < height) && (mousePosition.getY() - y > 0);

            // Draw Rectangle
            g.setColor(mouse_hover ? Color.GRAY : Color.WHITE);
            g.fillRect(x, y, width, height);
    
            // Draw Rectangle Outline
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }

    private class Grid {

        private int GridWidth = 20, GridHeight = 20;

        private Cell[][] cells = new Cell[GridWidth][GridHeight];
    
        public Grid(int screenWidth, int screenHeight){
            for(int i = 0; i < GridWidth; i++){
                for(int j = 0; j < GridHeight; j++){
                    cells[i][j] = new Cell(
                        (int)(i * screenWidth / GridWidth), 
                        (int)(j * screenHeight / GridHeight), 
                        (int)(screenWidth / GridWidth), 
                        (int)(screenHeight / GridHeight)
                    );
                }
            }
        }

        public void paint(Graphics g, Point mousePosition){
            for(int i = 0; i < cells.length; i++){
                for(int j = 0; j < cells.length; j++){
                    cells[i][j].paint(g, mousePosition);
                }
            }
        }
    }

    private class Canvas extends JPanel {
        private int ScreenWidth = 720, ScreenHeight = 720;

        private Grid grid = new Grid(ScreenWidth, ScreenHeight);

        public Canvas(){
            // Set the Canvas Size
            setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        }

        @Override
        public void paint(Graphics g){
            // Try to get mouse position
            Point mousePosition;
            if(getMousePosition() == null){
                mousePosition = new Point(-1, -1);
            } else {
                mousePosition = getMousePosition();
            }
            
            grid.paint(g, mousePosition);
        }
    }

    private Main(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas canvas = new Canvas();
        this.setContentPane(canvas);

        this.pack();
        this.setVisible(true);
    }

    public void run(){
        while(true){
            this.repaint();
        }
    }
}