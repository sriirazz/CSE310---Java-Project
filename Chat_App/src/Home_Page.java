import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Myframe extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel welcome = new JLabel("WELCOME TO THE HOME PAGE!!!");

    ImageIcon Bg = new ImageIcon("hmpgb.png");
    JLabel BG = new JLabel(Bg);
    JButton Usr1 = new JButton("User 2");
    ImageIcon ic = new ImageIcon("Logo1.jpg");
    JLabel IC = new JLabel(ic);


    Myframe(){
        this.setTitle("Home Page");
        ImageIcon image = new ImageIcon("C:/Users/sriir/study material/sem-4/cse 310 java/Chat_App/img.jpg");
        this.setIconImage(image.getImage());
        this.setVisible(true);
        this.setBounds(0, 0, 500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    public void setLayoutManager() {
        container.setLayout(null);
    }
    public void addComponentsToContainer(){
        container.add(welcome);
        container.add(IC);
        container.add(Usr1);
        container.add(BG);
    }
    public void setLocationAndSize() {
        welcome.setBounds(50,35,200,40);
        IC.setBounds(8,35,40,40);
        Usr1.setBounds(11,120,460,60);
        BG.setBounds(0,0,500,600);
    }
    public void addActionEvent() {
        Usr1.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Usr1){
            try {
                Usr1.setBackground(new Color(0x73c2fb));
                JOptionPane.showMessageDialog(this, "Waiting for User 1...");//1
                User2 u2 = new User2();
                this.setVisible(false);

                JOptionPane.showMessageDialog(this, "You are now connected to User 1...");
                u2.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while connecting to User 2.");
            }
        }
}}

public class Home_Page {
    public static void main(String rk[]){
        new Myframe();

}}
