package gameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login implements ActionListener,Runnable {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel success;
    private static long ID;

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        login.level = level;
    }

    private static int level;

    public static long getID() {
        return ID;
    }

    public static void setID(long ID) {
        login.ID = ID;
    }


    private double scale(double data , double r_min, double r_max, double t_min, double t_max){
        double res = ((data - r_min)/(r_max-r_min))*(t_max-t_min)+t_min;
        return res;
    }
    private static double scaleX(double data){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double r_min =0;
        double r_max =1280;
        double t_max =screenSize.getWidth();
        double t_min =0;
        double reso =((data - r_min)/(r_max-r_min))*(t_max-t_min)+t_min;
        return reso;
    }
    private static double scaleY(double data){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double r_min =0;
        double r_max =720;
        double t_max =screenSize.getHeight();
        double t_min =0;
        double reso =((data - r_min)/(r_max-r_min))*(t_max-t_min)+t_min;
        return reso;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(100,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("OOP Pokemon mini game");
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("ID");
        userLabel.setBounds((int)scaleX(10),(int)scaleY(20),(int)scaleX(80),(int)scaleY(25));
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds((int)scaleX(100),(int)scaleY(20),(int)scaleX(165),(int)scaleY(25));
        panel.add(userText);

        passwordLabel = new JLabel("Choose Level (1-23)");
        passwordLabel.setBounds((int)scaleX(10),(int)scaleY(50),(int)scaleX(80),(int)scaleY(25));
        panel.add(passwordLabel);

        passwordText = new JTextField();
        passwordText.setBounds((int)scaleX(100),(int)scaleY(50),(int)scaleX(165),(int)scaleY(25));
        panel.add(passwordText);

        button = new JButton("Enter");
        button.setBounds((int)scaleX(10),(int)scaleY(80),(int)scaleX(80),(int)scaleY(25));
        button.addActionListener(new login());
        panel.add(button);

        success = new JLabel("");
        success.setBounds((int)scaleX(10),(int)scaleY(110),(int)scaleX(300),(int)scaleY(25));
        panel.add(success);



        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();
        System.out.println(user + ","+password);
        int i=Integer.parseInt(password);
        if(user.length()==9 && i>=1 ){
            ID=(long)Integer.parseInt(user);
            level=i;
            // scenario_num = i;
            //login(user);
        }
    }

    @Override
    public void run() {

    }
}