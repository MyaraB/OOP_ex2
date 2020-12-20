package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.security.Key;
import java.util.EventListener;
import java.util.LinkedList;

public class GraphPanel extends JPanel implements MouseListener{

    private LinkedList<Point2D> points;
    String message;


    public GraphPanel(){
    super();
    this.setBackground(new Color(7,43,74));
    points = new LinkedList<Point2D>();
    this.addMouseListener(this);


    }

    void reset(){
        points = new LinkedList<Point2D>();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Point2D p = new Point2D.Double(x,y);
        points.add(p);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("name", Font.PLAIN, 25));
        g.setColor(Color.white);
        Point2D prev = null;

        for(Point2D p : points){
            g.setColor(Color.white);
            g.fillOval((int)p.getX(),(int)p.getY(),10,10);
            if(prev != null){
                Double dist = p.distance(prev);
                String dists = dist.toString().substring(0,dist.toString().indexOf(".")+2);
                g.setColor(Color.red);
                g.drawLine((int)p.getX(),(int)p.getY(),(int)prev.getX(),(int)prev.getY());
                g.drawString(dists,(int)((p.getX()+prev.getX())/2),(int)((p.getY()+prev.getY())/2));

            }
            prev=p;
        }
    }
}
