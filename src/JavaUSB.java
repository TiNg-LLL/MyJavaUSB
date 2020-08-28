import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSerialComm;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;

public class JavaUSB {

    public static void main(String[] args) {
        JavaUSBJFrame javaUSBJFrame = new JavaUSBJFrame("运动参数设置 -FMLaser v1.0");
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSerialComm());
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
    }
}