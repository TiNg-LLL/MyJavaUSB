import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaUSBJFrame extends JFrame {

    static SerialPort chosenPort;
    SerialPort[] portNames = null;
    //boolean b = false;
    PrintWriter output = null;
    JLabel messageText1 = new JLabel();

    {
        messageText1.setFont(new Font("宋体", Font.PLAIN, 12));
    }

    JLabel messageText2 = new JLabel();

    {
        messageText2.setFont(new Font("宋体", Font.PLAIN, 12));
    }

    JLabel messageText3 = new JLabel("  ");

    {
        messageText3.setFont(new Font("宋体", Font.PLAIN, 20));
    }

    public JavaUSBJFrame(String s) {

        //主窗口
        setTitle(s);
        setSize(400, 600);
        setLocation(1000, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        //COM选择按钮模块   comJPanel
        JPanel comJPanel = new JPanel();
        comJPanel.setBackground(Color.DARK_GRAY);
        JComboBox<String> portList = new JComboBox<String>();
        JButton connectButton = new JButton("连接");
        JButton flashButton = new JButton("端口刷新");
        comJPanel.add(portList);
        comJPanel.add(flashButton);
        comJPanel.add(connectButton);
        add(comJPanel, BorderLayout.NORTH);

        //连接按钮实现
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (connectButton.getText().equals("连接")) {
                    try {
                        chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                        chosenPort.openPort();
                        if (chosenPort.isOpen()) {
                            connectButton.setText("断开");
                            portList.setEnabled(false);
                            flashButton.setEnabled(false);
                            output = new PrintWriter(chosenPort.getOutputStream());
                            messageText1.setText("端口已连接");
                            messageText1.setForeground(Color.GREEN);
                            System.out.println("端口已连接");
                        }
                    } catch (Exception x) {
                        messageText1.setText("没有可用的端口");
                        messageText1.setForeground(Color.DARK_GRAY);
                        System.out.println("没有可用的端口");
                        //b = false;
                        try {
                            if (chosenPort.isOpen()) {
                                chosenPort.closePort();
                            }
                            output.close();
                        } catch (Exception e1) {
                        }
                        portList.setEnabled(true);
                        flashButton.setEnabled(true);
                        connectButton.setText("连接");
                        //x.printStackTrace();
                    }
                } else {
                    //b = false;
                    if (chosenPort.isOpen()) {
                        chosenPort.closePort();
                    }
                    output.close();
                    portList.setEnabled(true);
                    flashButton.setEnabled(true);
                    connectButton.setText("连接");
                    messageText1.setText("端口已断开");
                    messageText1.setForeground(Color.DARK_GRAY);
                    System.out.println("端口已断开");
                }
            }
        });

        //数据输出总面板    buttonJPanel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        //第一组数据按钮
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

        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (chosenPort.isOpen()) {
                        output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                        output.flush();
                        messageText2.setText("text1已输出");
                        System.out.println("text1已输出");
                    } else {
                        messageText2.setText("端口未连接");
                        System.out.println("端口未连接");
                    }
                } catch (Exception x) {
                    messageText2.setText("端口未连接");
                    System.out.println("端口未连接");
                    //x.printStackTrace();
                }
            }
        });

        //第二组数据按钮
        JPanel buttonJPanel2 = new JPanel();
        buttonJPanel2.setLayout(new BorderLayout());
        JLabel jl2 = new JLabel("text2");
        JTextField jt2 = new JTextField(6);
        JButton jb2 = new JButton("Set");
        buttonJPanel2.add(jl2, BorderLayout.WEST);
        buttonJPanel2.add(jt2, BorderLayout.CENTER);
        buttonJPanel2.add(jb2, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel2);

        //第三组数据按钮
        JPanel buttonJPanel3 = new JPanel();
        buttonJPanel3.setLayout(new BorderLayout());
        JLabel jl3 = new JLabel("text2");
        JTextField jt3 = new JTextField(6);
        JButton jb3 = new JButton("Set");
        buttonJPanel3.add(jl3, BorderLayout.WEST);
        buttonJPanel3.add(jt3, BorderLayout.CENTER);
        buttonJPanel3.add(jb3, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel3);

        //第四组数据按钮
        JPanel buttonJPanel4 = new JPanel();
        buttonJPanel4.setLayout(new BorderLayout());
        JLabel jl4 = new JLabel("text2");
        JTextField jt4 = new JTextField(6);
        JButton jb4 = new JButton("Set");
        buttonJPanel4.add(jl4, BorderLayout.WEST);
        buttonJPanel4.add(jt4, BorderLayout.CENTER);
        buttonJPanel4.add(jb4, BorderLayout.EAST);
        buttonJPanel.add(buttonJPanel4);

        //底部信息面板
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageText1, BorderLayout.WEST);
        messagePanel.add(messageText3, BorderLayout.CENTER);
        messagePanel.add(messageText2, BorderLayout.EAST);
        messagePanel.setBackground(Color.GRAY);

        add(messagePanel, BorderLayout.SOUTH);
        add(buttonJPanel, BorderLayout.CENTER);
        setVisible(true);

        //初始获取COM
        portList.removeAllItems();
        portNames = SerialPort.getCommPorts();
        for (int i = 0; i < portNames.length; i++) {
            portList.addItem(portNames[i].getSystemPortName());
        }

        //COM刷新按钮
        flashButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //portNames = null;
                portList.removeAllItems();
                portNames = SerialPort.getCommPorts();
                for (int i = 0; i < portNames.length; i++) {
                    portList.addItem(portNames[i].getSystemPortName());
                }
            }
        });
    }
}