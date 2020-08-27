import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JavaUSBJFrame extends JFrame {

    static SerialPort chosenPort;
    JLabel messageText1 = new JLabel();                     //底部信息栏左面板

    {
        messageText1.setFont(new Font("宋体", Font.PLAIN, 12));
    }

    JLabel messageText2 = new JLabel();                     //底部信息栏右面板

    {
        messageText2.setFont(new Font("宋体", Font.PLAIN, 12));
    }

    JLabel messageText3 = new JLabel("  ");             //底部信息栏中间面板

    {
        messageText3.setFont(new Font("宋体", Font.PLAIN, 20));
    }

    public JavaUSBJFrame(String s) {
//Modbus类创建
        Modbus modbus = new Modbus();

//主窗口
        setTitle(s);
        setSize(1000, 600);
        setLocation(500, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

//副窗口
        JFrame jFrame2 = new JFrame("COM参数");
        jFrame2.setSize(600, 90);
        jFrame2.setLocation(750, 360);
        jFrame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel baudrateLabel = new JLabel("波特率:");
        jFrame2.add(baudrateLabel);

        JComboBox<Integer> baudrateList = new JComboBox<Integer>();
        baudrateList.addItem(4800);
        baudrateList.addItem(9600);
        baudrateList.addItem(14400);
        baudrateList.addItem(19200);
        baudrateList.addItem(38400);
        baudrateList.addItem(56000);
        baudrateList.addItem(115200);
        baudrateList.setSelectedIndex(3);
        jFrame2.add(baudrateList);

        JLabel jLabel = new JLabel("|");
        jFrame2.add(jLabel);

        JLabel bitnum = new JLabel("比特位");
        jFrame2.add(bitnum);

        JTextField bitJtf = new JTextField(2);
        bitJtf.setText("8");
        jFrame2.add(bitJtf);

        JLabel jLabel1 = new JLabel("|");
        jFrame2.add(jLabel1);

        JLabel stopbit = new JLabel("结束位");
        jFrame2.add(stopbit);

        JTextField stoptf = new JTextField(2);
        stoptf.setText("1");
        jFrame2.add(stoptf);

        JLabel jLabel2 = new JLabel("|");
        jFrame2.add(jLabel2);

        JLabel doublejl = new JLabel("奇偶校验");
        jFrame2.add(doublejl);

        JComboBox<String> doubleBox = new JComboBox<String>();
        doubleBox.addItem("奇");
        doubleBox.addItem("偶");
        doubleBox.addItem("无");
        doubleBox.setSelectedIndex(1);
        jFrame2.add(doubleBox);

        JLabel jLabel3 = new JLabel("|");
        jFrame2.add(jLabel3);

        JLabel slaveID = new JLabel("地址(默认1)");
        jFrame2.add(slaveID);

        JTextField bitJtf2 = new JTextField(2);
        bitJtf2.setText("1");
        jFrame2.add(bitJtf2);

        jFrame2.setResizable(false);
        jFrame2.setVisible(false);

//COM选择按钮模块   comJPanel
        JPanel comJPanel = new JPanel();
        comJPanel.setBackground(Color.DARK_GRAY);
        JComboBox<String> portList = new JComboBox<String>();       //端口选择下拉菜单
        JButton connectButton = new JButton("连接");
        JButton flashButton = new JButton("端口刷新");
        JButton comSet = new JButton("参数设置");
        comJPanel.add(portList);
        comJPanel.add(flashButton);
        comJPanel.add(comSet);
        comJPanel.add(connectButton);
        add(comJPanel, BorderLayout.NORTH);

//连接按钮实现
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String a = portList.getSelectedItem().toString();
                Integer b = (Integer) baudrateList.getSelectedItem();
                int c = Integer.parseInt(bitJtf.getText());
                int d = Integer.valueOf(stoptf.getText());
                String s = doubleBox.getSelectedItem().toString();
                if (connectButton.getText().equals("连接")) {
                    try {
                        modbus.Modbusconnect(a, b, c, d, s);
                        if (modbus.ModbusisConnected()) {
                            connectButton.setText("断开");
                            portList.setEnabled(false);
                            flashButton.setEnabled(false);
                            messageText1.setText("端口已连接");
                            messageText1.setForeground(Color.GREEN);
                            comJPanel.setBackground(Color.GREEN);
                            System.out.println("端口已连接");
                        } else {
                            messageText1.setText("端口被占用");
                            messageText1.setForeground(Color.DARK_GRAY);
                            System.out.println("端口被占用");
                        }
                    } catch (Exception x) {                                                     //没有端口可用
                        System.out.println("奇怪的报错");
                    }
                } else {                                                                        //断开连接
                    if (modbus.ModbusisConnected()) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        modbus.ModbusDisconnect();
                    }
                    portList.setEnabled(true);
                    flashButton.setEnabled(true);
                    connectButton.setText("连接");
                    messageText1.setText("端口已断开");
                    messageText1.setForeground(Color.DARK_GRAY);
                    comJPanel.setBackground(Color.DARK_GRAY);
                    System.out.println("端口已断开");
                }
            }
        });

//参数设置按钮实现
        comSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame2.setVisible(true);
            }
        });

//数据输出总面板    buttonJPanel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

//数据传递
        int slaveId = Integer.parseInt(bitJtf2.getText());
        int offset = 2;

//第一组数据按钮
        JPanel buttonJPanel1 = new JPanel();
        buttonJPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel jl1 = new JLabel("D2");
        JTextField jt1 = new JTextField(10);
        JButton jb1 = new JButton("Set");
        JLabel jlnow = new JLabel("当前值:");
        buttonJPanel1.add(jl1);
        buttonJPanel1.add(jt1);
        buttonJPanel1.add(jb1);
        buttonJPanel1.add(jlnow);
        buttonJPanel.add(buttonJPanel1);

        jb1.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String sjt1 = jt1.getText();
                        int c = 0;
                        if (!(sjt1 == null)) {
                            c = Integer.parseInt(sjt1);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId, offset, quantity);

                    } else {
                        messageText2.setText("端口未连接1");
                        System.out.println("端口未连接1");
                    }
                } catch (Exception x) {
                    messageText2.setText("端口未连接");
                    System.out.println("端口未连接");
                    x.printStackTrace();
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
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (int i = 0; i < portNames.length; i++) {
            portList.addItem(portNames[i].getSystemPortName());
        }

//COM刷新按钮
        flashButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                portList.removeAllItems();
                SerialPort[] portNames = SerialPort.getCommPorts();
                for (int i = 0; i < portNames.length; i++) {
                    portList.addItem(portNames[i].getSystemPortName());
                }
            }
        });

//读取线程
        class ReadThread extends Thread {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                    if (modbus.ModbusisConnected()) {
                        jlnow.setText("当前值：" + modbus.ModbusreadHoldingRegisters(slaveId, offset, 1));
                    }
                }
            }
        }
        ;
        ReadThread readThread = new ReadThread();
        readThread.start();
    }
}