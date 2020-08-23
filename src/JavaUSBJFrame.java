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
    boolean b = false;

    public JavaUSBJFrame(String s) {

        //主窗口
        setTitle(s);
        setSize(400, 600);
        setLocation(1000, 260);
        //getContentPane().setBackground(Color.black);
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

        //端口连接按钮实现
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (connectButton.getText().equals("连接")) {
                    try {
                        chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                        if (chosenPort.openPort()) {
                            connectButton.setText("断开");
                            portList.setEnabled(false);
                            flashButton.setEnabled(false);
                            PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                           /* b = true;

                            Thread thread = new Thread() {
                                public void run() {
                                    try {
                                        Thread.sleep(100);
                                    } catch (Exception e) {
                                    }
                                    while (b) {
                                        output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                                        output.flush();
                                        try {
                                            Thread.sleep(100);
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            };
                            thread.start();*/
                            System.out.println("端口已连接");
                        }
                    } catch (Exception x) {
                        System.out.println("没有可用的端口");
                        b = false;
                        chosenPort.closePort();
                        portList.setEnabled(true);
                        flashButton.setEnabled(true);
                        connectButton.setText("连接");
                        x.printStackTrace();
                    }
                } else {
                    b = false;
                    chosenPort.closePort();
                    portList.setEnabled(true);
                    flashButton.setEnabled(true);
                    connectButton.setText("连接");
                }
            }
        });

        //数据输出总面板    buttonJPanel
        JPanel buttonJPanel = new JPanel();
        //buttonJPanel.setBackground(Color.GRAY);
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
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Set");
                PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                output.flush();
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