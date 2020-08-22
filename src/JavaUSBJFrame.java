import javax.swing.*;
import java.awt.*;

public class JavaUSBJFrame extends JFrame {
    public JavaUSBJFrame(String s) {

        setTitle(s);
        setSize(400, 600);
        setLocation(1000, 260);
        setBackground(Color.black);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel comJPanel = new JPanel();
        JComboBox<String> portList = new JComboBox<String>();
        JButton connectButton = new JButton("端口连接");
        comJPanel.add(portList);
        comJPanel.add(connectButton);
        add(comJPanel, BorderLayout.NORTH);

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JPanel buttonJPanel1 = new JPanel();
        //buttonJPanel1.setPreferredSize(new Dimension(180,30));
        buttonJPanel1.setLayout(new BorderLayout());
        JLabel jl1 = new JLabel("text1");
        JTextField jt1 = new JTextField(6);
        JButton jb1 = new JButton("Set");
        buttonJPanel1.add(jl1, BorderLayout.WEST);
        buttonJPanel1.add(jt1, BorderLayout.CENTER);
        buttonJPanel1.add(jb1, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel1);

        JPanel buttonJPanel2 = new JPanel();
        buttonJPanel2.setLayout(new BorderLayout());
        JLabel jl2 = new JLabel("text2");
        JTextField jt2 = new JTextField(6);
        JButton jb2 = new JButton("Set");
        buttonJPanel2.add(jl2, BorderLayout.WEST);
        buttonJPanel2.add(jt2, BorderLayout.CENTER);
        buttonJPanel2.add(jb2, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel2);

        JPanel buttonJPanel3 = new JPanel();
        buttonJPanel3.setLayout(new BorderLayout());
        JLabel jl3 = new JLabel("text2");
        JTextField jt3 = new JTextField(6);
        JButton jb3 = new JButton("Set");
        buttonJPanel3.add(jl3, BorderLayout.WEST);
        buttonJPanel3.add(jt3, BorderLayout.CENTER);
        buttonJPanel3.add(jb3, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel3);

        JPanel buttonJPanel4 = new JPanel();
        buttonJPanel4.setLayout(new BorderLayout());
        JLabel jl4 = new JLabel("text2");
        JTextField jt4 = new JTextField(6);
        JButton jb4 = new JButton("Set");
        buttonJPanel4.add(jl4, BorderLayout.WEST);
        buttonJPanel4.add(jt4, BorderLayout.CENTER);
        buttonJPanel4.add(jb4, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel4);

        add(buttonJPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}