import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

public class User2 extends JFrame {
    Socket socket;
    private JLabel heading = new JLabel("User2 Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    JLabel BG = new JLabel(new ImageIcon("Chat.jpg"));
    private Font font = new Font("Roboto",Font.BOLD,20);

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;


    BufferedReader br;
    PrintWriter out;

    public User2(){
        try{
            System.out.println("Sending Request to user1...");
//             10.35.37.205
            socket = new Socket("10.35.38.180",7777);
            System.out.println("Connection done...");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendFile("C:\\Users\\sriir\\Downloads\\srs_template.doc");
//            sendFile("path/to/file2.pdf");

            dataInputStream.close();
            dataInputStream.close();

//            createGUI();
            handleEvents();
            startReading();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createGUI(){
        //GUI code
        this.setTitle("User1 Messager");
        this.setSize(500,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        heading.setIcon(new ImageIcon("Usr4.jpg"));
        heading.setHorizontalTextPosition(SwingConstants.RIGHT);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);

        this.setLayout(new BorderLayout());
        BG.setBounds(250,150,500,600);
        this.add(heading,BorderLayout.NORTH);
        this.add(BG,BorderLayout.CENTER);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
        ImageIcon image = new ImageIcon("Logo.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0x0492C7));

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
//                        System.out.println("User1 terminated the chat");
                        JOptionPane.showMessageDialog(this, "Server" +
                                " terminated the chat");

                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
//                    System.out.println("User1 :"+msg);

                    messageArea.append("User1 :"+msg+"\n");
                }
            } catch (Exception e) {
//                throw new RuntimeException(e);
                System.out.println("");
            }
        };
        new Thread(r1).start();
    }

    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // send file size
        dataOutputStream.writeLong(file.length());
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }

    public static void main(String[] args) {
        System.out.println("User2 is Online...");
        new User2();
    }
}