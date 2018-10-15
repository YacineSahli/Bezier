import javax.swing.*;    
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
public class MyPanel extends JPanel {
            ArrayList<Point2D.Float> points = new ArrayList<Point2D.Float>();
            ArrayList<Shape> shapes = new ArrayList<Shape>();
            public void paint(Graphics g){
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHints(hints);
                for(Shape s : shapes){
                    g2.setColor(Color.BLUE);
                    g2.draw(s);
                }
            }
            public void clean(){
                points.clear();
                shapes.clear();
                repaint();
            }
        };