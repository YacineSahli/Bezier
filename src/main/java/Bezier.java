import javax.swing.*;    
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.imageio.ImageIO;

public class Bezier {
    static Point2D.Float a = new Point2D.Float(-1,-1);
    static Point2D.Float b = new Point2D.Float(-1,-1);
    static Point2D.Float c = new Point2D.Float(-1,-1);
    static Point2D.Float d = new Point2D.Float(-1,-1);
    static Point2D.Float[] curve;
    static Image crosshair ;
    static ImageIcon crosshairIcon;
    static MyPanel panel;

    /*
     * Calculate One bezier point considering the t value for linear interpolation
     */
    private static Point2D.Float bezier(Point2D.Float a, Point2D.Float b, Point2D.Float c, Point2D.Float d, float t){
        Point2D.Float lerpAB = lerp(a, b, t);
        Point2D.Float lerpBC = lerp(b, c, t);
        Point2D.Float lerpCD = lerp(c, d, t);
        Point2D.Float lerpABBC = lerp(lerpAB, lerpBC, t);
        Point2D.Float lerpBCCD = lerp(lerpBC, lerpCD, t);
        Point2D.Float res = lerp(lerpABBC, lerpBCCD, t);
        return res;
    }
    /*
     * Linear Interpolation.
     */
    private static Point2D.Float lerp(Point2D.Float a, Point2D.Float b, float t){
        Point2D.Float res = new Point2D.Float();
        float x = a.x + (b.x-a.x)*t;
        float y = a.y + (b.y-a.y)*t;
        res.setLocation(x, y);
        return res;
    }

    private static void calculate(){
        for(int i=0; i<1000; ++i){
            float t =  (float) ((float)i/ 999.0);
            Point2D.Float p = bezier(a,b,c,d,t);
            panel.points.add(p);
        }
        for(int i=0; i<999; ++i){
            panel.shapes.add(new Line2D.Float(panel.points.get(i).x, panel.points.get(i).y, panel.points.get(i+1).x, panel.points.get(i+1).y));
        }
        
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Bezier");
        frame.setMinimumSize(new Dimension(800,600));
        frame.setPreferredSize(new Dimension(1600,900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel = new MyPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(800, 600));
        panel.setPreferredSize(new Dimension(1600, 900));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        try {
            crosshair = ImageIO.read(Bezier.class.getClassLoader().getResource("crosshair.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        crosshairIcon = new ImageIcon(crosshair);
        JLabel crosshair1 = new JLabel(crosshairIcon);
        JLabel crosshair2 = new JLabel(crosshairIcon);
        JLabel crosshair3 = new JLabel(crosshairIcon);
        JLabel crosshair4 = new JLabel(crosshairIcon);
        crosshair1.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                a.x = e.getXOnScreen() - panel.getLocationOnScreen().x ;
                a.y = e.getYOnScreen() - panel.getLocationOnScreen().y ;
                crosshair1.setBounds(Math.round(a.x) -5 , Math.round(a.y) -5, 10, 10);
                panel.clean();
                calculate();
            }
        });
        crosshair2.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                b.x = e.getXOnScreen() - panel.getLocationOnScreen().x ;
                b.y = e.getYOnScreen() - panel.getLocationOnScreen().y ;
                crosshair2.setBounds(Math.round(b.x) -5 , Math.round(b.y)-5, 10, 10);
                panel.clean();
                calculate();
            }
        });
        crosshair3.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                c.x = e.getXOnScreen() - panel.getLocationOnScreen().x ;
                c.y = e.getYOnScreen() - panel.getLocationOnScreen().y ;
                crosshair3.setBounds(Math.round(c.x)-5, Math.round(c.y)-5, 10, 10);
                panel.clean();
                calculate();
            }
        });
        crosshair4.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                d.x = e.getXOnScreen() - panel.getLocationOnScreen().x;
                d.y = e.getYOnScreen() - panel.getLocationOnScreen().y;
                crosshair4.setBounds(Math.round(d.x)-5, Math.round(d.y)-5, 10, 10);
                panel.clean();
                calculate();
            }
        });
        panel.add(crosshair1);
        panel.add(crosshair2);
        panel.add(crosshair3);
        panel.add(crosshair4);

        panel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if(a.x == -1){
                    a.x = e.getX() ;
                    a.y = e.getY() ;
                    crosshair1.setBounds(Math.round(a.x)-5, Math.round(a.y)-5, 10, 10);
                }
                else if(b.x == -1){
                    b.x = e.getX() ;
                    b.y = e.getY() ;
                    crosshair2.setBounds(Math.round(b.x)-5, Math.round(b.y)-5, 10, 10);
                }
                else if(c.x == -1){
                    c.x = e.getX() ;
                    c.y = e.getY() ;
                    crosshair3.setBounds(Math.round(c.x)-5, Math.round(c.y)-5, 10, 10);
                }
                else if(d.x == -1){
                    d.x = e.getX() ;
                    d.y = e.getY() ;
                    crosshair4.setBounds(Math.round(d.x)-5, Math.round(d.y)-5, 10, 10);
                    panel.removeMouseListener(this);
                    panel.clean();
                    calculate();
                }
            }                
         });
        
        panel.setVisible(true);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    
    

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}