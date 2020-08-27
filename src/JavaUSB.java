import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSerialComm;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;

public class JavaUSB {

    public static void main(String[] args) {
        JavaUSBJFrame javaUSBJFrame = new JavaUSBJFrame("USB控制程序 -TiNg");
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSerialComm());
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
    }
}