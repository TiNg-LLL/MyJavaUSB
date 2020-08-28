import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

//获取屏幕尺寸
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高

//主窗口
        setTitle(s);
        setSize(800, 450);
        setLocation(screenWidth / 2 - 800 / 2, screenHeight / 2 - 450 / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 5));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        setResizable(false);

//顶部COM参数跳窗
        JFrame jFrame2 = new JFrame("COM参数");
        jFrame2.setSize(600, 120);
        jFrame2.setLocation(screenWidth / 2 - 800 / 2 + 50, screenHeight / 2 - 450 / 2 + 100);
        jFrame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

//COM参数设置跳窗内容
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

        JLabel slavejl = new JLabel("地址(默认1)");
        jFrame2.add(slavejl);

        JTextField bitJtf2 = new JTextField(2);
        bitJtf2.setText("1");
        jFrame2.add(bitJtf2);

        JButton comset = new JButton("设置");
        jFrame2.add(comset);

        jFrame2.setResizable(false);
        jFrame2.setVisible(false);

//顶部COM选择面板
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
                            comSet.setEnabled(false);
                            messageText1.setText("端口已连接");
                            messageText2.setText("");
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
                    comSet.setEnabled(true);
                    connectButton.setText("连接");
                    messageText1.setText("端口已断开");
                    messageText1.setForeground(Color.DARK_GRAY);
                    comJPanel.setBackground(Color.DARK_GRAY);
                    System.out.println("端口已断开");
                }
            }
        });

//COM参数设置按钮实现
        comSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame2.setVisible(true);
            }
        });

//左侧数据输出面板
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        buttonJPanel.setPreferredSize(new Dimension(320, 500));

//地址参数设置按钮
        JPanel buttonJPanel0 = new JPanel();
        buttonJPanel0.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 10));
        buttonJPanel0.setPreferredSize(new Dimension(300, 50));
        JButton offsetset = new JButton("参数地址设置");
        buttonJPanel0.add(offsetset);
        buttonJPanel.add(buttonJPanel0);

//地址参数设置跳窗
        JFrame jFrame3 = new JFrame("地址参数设置(修改地址后务必点击设置按钮)");
        jFrame3.setSize(600, 115);
        jFrame3.setLocation(screenWidth / 2 - 800 / 2 + 50, screenHeight / 2 - 450 / 2 + 100);
        jFrame3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel no1jl = new JLabel("速度:");
        jFrame3.add(no1jl);

        JTextField no1jf = new JTextField(5);
        no1jf.setText("0");
        jFrame3.add(no1jf);

        JLabel nojLabel = new JLabel("|");
        jFrame3.add(nojLabel);

        JLabel no2jl = new JLabel("加减速度");
        jFrame3.add(no2jl);

        JTextField no2jf = new JTextField(5);
        no2jf.setText("1");
        jFrame3.add(no2jf);

        JLabel nojLabel1 = new JLabel("|");
        jFrame3.add(nojLabel1);

        JLabel no3jl = new JLabel("上端目标位置");
        jFrame3.add(no3jl);

        JTextField no3jf = new JTextField(5);
        no3jf.setText("2");
        jFrame3.add(no3jf);

        JLabel nojLabel2 = new JLabel("|");
        jFrame3.add(nojLabel2);

        JLabel no4jl = new JLabel("下端目标位置");
        jFrame3.add(no4jl);

        JTextField no4jf = new JTextField(5);
        no4jf.setText("3");
        jFrame3.add(no4jf);

        JLabel mid1jl = new JLabel("材料厚度");
        jFrame3.add(mid1jl);

        JTextField mid1jf = new JTextField(5);
        mid1jf.setText("4");
        jFrame3.add(mid1jf);

        JLabel nojLabel3 = new JLabel("|");
        jFrame3.add(nojLabel3);

        JLabel mid1jl2 = new JLabel("备用1");
        jFrame3.add(mid1jl2);

        JTextField mid1jf2 = new JTextField(5);
        mid1jf2.setText("5");
        jFrame3.add(mid1jf2);

        JLabel nojLabel4 = new JLabel("|");
        jFrame3.add(nojLabel4);

        JLabel mid1jl3 = new JLabel("备用2");
        jFrame3.add(mid1jl3);

        JTextField mid1jf3 = new JTextField(5);
        mid1jf3.setText("6");
        jFrame3.add(mid1jf3);

//com细分地址数据传输
        final int[] slaveId = {Integer.parseInt(bitJtf2.getText())};

        comset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    slaveId[0] = Integer.parseInt(bitJtf2.getText());
                    messageText2.setText("设置成功");
                } catch (Exception e2) {
                    messageText2.setText("设置失败");
                }
            }
        });

//左侧第一组数据传递
        final int[] offset = {Integer.parseInt(no1jf.getText())};

//左侧第二组数据传递
        final int[] offset2 = {Integer.parseInt(no2jf.getText())};

//左侧第三组数据传递
        final int[] offset3 = {Integer.parseInt(no3jf.getText())};

//左侧第四组数据传递
        final int[] offset4 = {Integer.parseInt(no4jf.getText())};

//中部第一组数据传递
        final int[] midset = {Integer.parseInt(mid1jf.getText())};

//中部第二组数据传递
        final int[] midset2 = {Integer.parseInt(mid1jf2.getText())};

//中部第二组数据传递
        final int[] midset3 = {Integer.parseInt(mid1jf3.getText())};

//左侧地址参数设置跳窗设置按钮
        JButton noset = new JButton("设置");
        jFrame3.add(noset);

        noset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    offset[0] = Integer.parseInt(no1jf.getText());
                    offset2[0] = Integer.parseInt(no2jf.getText());
                    offset3[0] = Integer.parseInt(no3jf.getText());
                    offset4[0] = Integer.parseInt(no4jf.getText());
                    midset[0] = Integer.parseInt(mid1jf.getText());
                    midset2[0] = Integer.parseInt(mid1jf2.getText());
                    midset3[0] = Integer.parseInt(mid1jf3.getText());
                    messageText2.setText("设置成功");
                } catch (Exception e2) {
                    messageText2.setText("设置失败");
                }
            }
        });

        jFrame3.setResizable(false);
        jFrame3.setVisible(false);

//地址参数设置按钮实现
        offsetset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame3.setVisible(true);
            }
        });

//第一组数据按钮
        JPanel buttonJPanel1 = new JPanel();
        buttonJPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonJPanel1.setPreferredSize(new Dimension(300, 50));
        JLabel jl1 = new JLabel("                速度");
        JTextField jt1 = new JTextField(4);
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
                        modbus.ModbuswriteSingleRegister(slaveId[0], offset[0], quantity);

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

/*//分割符
        JLabel jLabel4 = new JLabel("|");
        buttonJPanel.add(jLabel4);*/

//第二组数据按钮
        JPanel buttonJPanel2 = new JPanel();
        buttonJPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonJPanel2.setPreferredSize(new Dimension(300, 50));
        JLabel jl2 = new JLabel("        加减速度");
        JTextField jt2 = new JTextField(4);
        JButton jb2 = new JButton("Set");
        JLabel jlnow2 = new JLabel("当前值:");
        buttonJPanel2.add(jl2);
        buttonJPanel2.add(jt2);
        buttonJPanel2.add(jb2);
        buttonJPanel2.add(jlnow2);
        buttonJPanel.add(buttonJPanel2);

        jb2.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String sjt1 = jt2.getText();
                        int c = 0;
                        if (!(sjt1 == null)) {
                            c = Integer.parseInt(sjt1);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], offset2[0], quantity);

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

/*//分割符
        JLabel jLabel5 = new JLabel("|");
        buttonJPanel.add(jLabel5);*/

//第三组数据按钮
        JPanel buttonJPanel3 = new JPanel();
        buttonJPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonJPanel3.setPreferredSize(new Dimension(300, 50));
        JLabel jl3 = new JLabel("上端目标位置");
        JTextField jt3 = new JTextField(4);
        JButton jb3 = new JButton("Set");
        JLabel jlnow3 = new JLabel("当前值:");
        buttonJPanel3.add(jl3);
        buttonJPanel3.add(jt3);
        buttonJPanel3.add(jb3);
        buttonJPanel3.add(jlnow3);
        buttonJPanel.add(buttonJPanel3);

        jb3.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String sjt1 = jt3.getText();
                        int c = 0;
                        if (!(sjt1 == null)) {
                            c = Integer.parseInt(sjt1);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], offset3[0], quantity);

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

/*//分割符
        JLabel jLabel6 = new JLabel("|");
        buttonJPanel.add(jLabel6);*/

//第四组数据按钮
        JPanel buttonJPanel4 = new JPanel();
        buttonJPanel4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonJPanel4.setPreferredSize(new Dimension(300, 50));
        JLabel jl4 = new JLabel("下端目标位置");
        JTextField jt4 = new JTextField(4);
        JButton jb4 = new JButton("Set");
        JLabel jlnow4 = new JLabel("当前值:");
        buttonJPanel4.add(jl4);
        buttonJPanel4.add(jt4);
        buttonJPanel4.add(jb4);
        buttonJPanel4.add(jlnow4);
        buttonJPanel.add(buttonJPanel4);

        jb4.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String sjt1 = jt4.getText();
                        int c = 0;
                        if (!(sjt1 == null)) {
                            c = Integer.parseInt(sjt1);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], offset4[0], quantity);

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

//中部面板
        JPanel middleJPanel = new JPanel();
        middleJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        middleJPanel.setPreferredSize(new Dimension(320, 500));

//中部面板第一组数据
        JPanel midJPanel1 = new JPanel();
        midJPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        midJPanel1.setPreferredSize(new Dimension(300, 50));
        JLabel midjl1 = new JLabel("材料厚度:");
        JTextField midjt1 = new JTextField(4);
        JButton midjb1 = new JButton("Set");
        JLabel midjlnow = new JLabel("当前值:");
        midJPanel1.add(midjl1);
        midJPanel1.add(midjt1);
        midJPanel1.add(midjb1);
        midJPanel1.add(midjlnow);
        middleJPanel.add(midJPanel1);

        midjb1.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String s = midjt1.getText();
                        int c = 0;
                        if (!(s == null)) {
                            c = Integer.parseInt(s);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], midset[0], quantity);

                    } else {
                        messageText2.setText("端口未连接");
                        System.out.println("端口未连接");
                    }
                } catch (Exception x) {
                    messageText2.setText("端口未连接");
                    System.out.println("端口未连接");
                    x.printStackTrace();
                }
            }
        });

//中部面板第二组数据
        JPanel midJPanel2 = new JPanel();
        midJPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        midJPanel2.setPreferredSize(new Dimension(300, 50));
        JLabel midjl2 = new JLabel("      备用1:");
        JTextField midjt2 = new JTextField(4);
        JButton midjb2 = new JButton("Set");
        JLabel midjlnow2 = new JLabel("当前值:");
        midJPanel2.add(midjl2);
        midJPanel2.add(midjt2);
        midJPanel2.add(midjb2);
        midJPanel2.add(midjlnow2);
        middleJPanel.add(midJPanel2);

        midjb2.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String s = midjt2.getText();
                        int c = 0;
                        if (!(s == null)) {
                            c = Integer.parseInt(s);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], midset2[0], quantity);

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

//中部面板第三组数据
        JPanel midJPanel3 = new JPanel();
        midJPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        midJPanel3.setPreferredSize(new Dimension(300, 50));
        JLabel midjl3 = new JLabel("      备用2:");
        JTextField midjt3 = new JTextField(4);
        JButton midjb3 = new JButton("Set");
        JLabel midjlnow3 = new JLabel("当前值:");
        midJPanel3.add(midjl3);
        midJPanel3.add(midjt3);
        midJPanel3.add(midjb3);
        midJPanel3.add(midjlnow3);
        middleJPanel.add(midJPanel3);

        midjb3.addActionListener(new ActionListener() {                    //set按钮动作
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modbus.ModbusisConnected()) {
                        String s = midjt3.getText();
                        int c = 0;
                        if (!(s == null)) {
                            c = Integer.parseInt(s);
                        }
                        int quantity = c;
                        modbus.ModbuswriteSingleRegister(slaveId[0], midset3[0], quantity);

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

//右侧上升下降按钮面板
        JPanel updown = new JPanel();
        updown.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        updown.setPreferredSize(new Dimension(150, 500));

        JButton offset5set = new JButton("参数设置");
        JButton up = new JButton("      上升      ");
        JButton down = new JButton("      下降      ");

        updown.add(offset5set);
        updown.add(up);
        updown.add(down);

//上下参数设置面板
        JFrame jFrame4 = new JFrame("运动指令地址设置(修改地址后务必点击设置按钮)");
        jFrame4.setSize(600, 90);
        jFrame4.setLocation(screenWidth / 2 - 800 / 2 + 50, screenHeight / 2 - 450 / 2 + 100);
        jFrame4.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel upsetjl = new JLabel("上指令地址:");
        jFrame4.add(upsetjl);

        JTextField upsetjf = new JTextField(5);
        upsetjf.setText("18432");
        jFrame4.add(upsetjf);

        JLabel downsetjl = new JLabel("下指令地址:");
        jFrame4.add(downsetjl);

        JTextField downsetjf = new JTextField(5);
        downsetjf.setText("18433");
        jFrame4.add(downsetjf);

        JButton updownsetjb = new JButton("设置");
        jFrame4.add(updownsetjb);

        jFrame4.setResizable(false);
        jFrame4.setVisible(false);

//上下参数设置面板按钮实现
        offset5set.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame4.setVisible(true);
            }
        });

//上下按钮实现
        final int[] offset5 = {Integer.parseInt(upsetjf.getText())};

        up.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (modbus.ModbusisConnected()) {
                    modbus.ModbuswritetrueMultipleCoils(slaveId[0], offset5[0]);
                } else {
                    messageText2.setText("端口未连接");
                    System.out.println("端口未连接");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (modbus.ModbusisConnected()) {
                    modbus.ModbuswritefalseMultipleCoils(slaveId[0], offset5[0]);
                } else {
                    messageText2.setText("端口未连接");
                    System.out.println("端口未连接");
                }
            }
        });

        final int[] offset6 = {Integer.parseInt(downsetjf.getText())};

        down.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                modbus.ModbuswritetrueMultipleCoils(slaveId[0], offset6[0]);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                modbus.ModbuswritefalseMultipleCoils(slaveId[0], offset6[0]);
            }
        });

//上下参数设置按钮实现
        updownsetjb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    offset5[0] = Integer.parseInt(upsetjf.getText());
                    offset6[0] = Integer.parseInt(downsetjf.getText());
                    messageText2.setText("设置成功");
                } catch (Exception e3) {
                    messageText2.setText("设置失败");
                }
            }
        });

//底部信息面板
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageText1, BorderLayout.WEST);
        messagePanel.add(messageText3, BorderLayout.CENTER);
        messagePanel.add(messageText2, BorderLayout.EAST);
        messagePanel.setBackground(Color.GRAY);

//面板总添加
        add(comJPanel, BorderLayout.NORTH);
        add(messagePanel, BorderLayout.SOUTH);
        add(middleJPanel, BorderLayout.CENTER);
        add(buttonJPanel, BorderLayout.WEST);
        add(updown, BorderLayout.EAST);

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
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    try {
                        if (modbus.ModbusisConnected()) {
                            int[] i = modbus.ModbusreadHoldingRegisters(slaveId[0], offset[0], 1);
                            int[] i2 = modbus.ModbusreadHoldingRegisters(slaveId[0], offset2[0], 1);
                            int[] i3 = modbus.ModbusreadHoldingRegisters(slaveId[0], offset3[0], 1);
                            int[] i4 = modbus.ModbusreadHoldingRegisters(slaveId[0], offset4[0], 1);
                            int[] i5 = modbus.ModbusreadHoldingRegisters(slaveId[0], midset[0], 1);
                            int[] i6 = modbus.ModbusreadHoldingRegisters(slaveId[0], midset2[0], 1);
                            int[] i7 = modbus.ModbusreadHoldingRegisters(slaveId[0], midset3[0], 1);
                            jlnow.setText("当前值：" + i[0]);
                            jlnow2.setText("当前值：" + i2[0]);
                            jlnow3.setText("当前值：" + i3[0]);
                            jlnow4.setText("当前值：" + i4[0]);
                            midjlnow.setText("当前值：" + i5[0]);
                            midjlnow2.setText("当前值：" + i6[0]);
                            midjlnow3.setText("当前值：" + i7[0]);
                        } else {
                            jlnow.setText("当前值：null");
                            jlnow2.setText("当前值：null");
                            jlnow3.setText("当前值：null");
                            jlnow4.setText("当前值：null");
                            midjlnow.setText("当前值：null");
                            midjlnow2.setText("当前值：null");
                            midjlnow3.setText("当前值：null");
                        }
                    } catch (Exception e) {
                        System.out.println("COM端无返回值");
                        messageText2.setText("COM端无返回值");
                    }

                }
            }
        }
        ;
        ReadThread readThread = new ReadThread();
        readThread.start();
    }
}