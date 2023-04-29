import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.awt.*;

class User1 extends JFrame {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //GUI COMPO
    private JLabel heading = new JLabel("User1");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    ImageIcon backgroundIcon = new ImageIcon("Chat.jpg");
    JLabel BG = new JLabel(backgroundIcon);
    private Font font = new Font("Roboto",Font.BOLD,20);

    public User1() {
        try {
            server = new ServerSocket(7777);
            System.out.println("User 1 is ready to accept connection");
            System.out.println("Waiting....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream
                    ()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();

            startReading();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void createGUI(){
        //GUI code
        this.setTitle("User2 Messager");
        this.setSize(500,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for component
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        heading.setIcon(new ImageIcon("Usr1.jpg"));
        heading.setHorizontalTextPosition(SwingConstants.RIGHT);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);
        this.setLayout(new BorderLayout());
        BG.setBounds(0, 0, 500, 600);
        this.add(heading,BorderLayout.NORTH);
        this.add(BG, BorderLayout.CENTER);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
        ImageIcon image = new ImageIcon("C:/Users/sriir/study material/sem-4/cse 310 java/Chat_App/img.jpg");
        this.setIconImage(image.getImage());
        this.setVisible(true);
        this.setResizable(false);
    }

    private void handleEvents(){
        messageInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==10) {
                    String content = messageInput.getText();
                    messageArea.append("Me :"+content+"\n");
                    out.println(content);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
        });
    }
    public void startReading(){
        Runnable r1 = ()->{
            System.out.println("Reader started...");
            try {
            while(true){
                String msg = null;
                    msg = br.readLine();
                    if(msg.equals("Exit")){
                        JOptionPane.showMessageDialog(this, "Server" +
                                " terminated the chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
//                    System.out.println("User2 :"+msg);
                messageArea.append("User2 :"+msg+"\n");
                }
            } catch (Exception e) {
                System.out.println("");
            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writer Started...");
            try{
            while(!socket.isClosed()){

                    BufferedReader br1 = new BufferedReader(new
                            InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                if(content.equals("Exit")){
                    socket.close();
                    break;
                }
            }
            System.out.println("Connection is Closed");
            }catch(Exception e){
                System.out.println("");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("User1 is online");
        new User1();
    }
}
