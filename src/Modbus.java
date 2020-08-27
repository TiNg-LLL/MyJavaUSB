import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;

public class Modbus {
    SerialParameters sp = new SerialParameters();
    ModbusMaster m = null;

    public void Modbusconnect(String scom, Integer baudrate, int dateBits, int stopBits, String doubleEven) {
        sp.setDevice(scom);
        switch (baudrate) {
            case 4800:
                sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_4800);
                break;
            case 9600:
                sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_9600);
                break;
            case 19200:
                sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_19200);
                break;
            case 38400:
                sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_38400);
                break;
            case 115200:
                sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_115200);
                break;
        }
        sp.setDataBits(dateBits);
        sp.setStopBits(stopBits);
        switch (doubleEven) {
            case "奇":
                sp.setParity(SerialPort.Parity.ODD);
                break;
            case "偶":
                sp.setParity(com.intelligt.modbus.jlibmodbus.serial.SerialPort.Parity.EVEN);
                break;
            case "无":
                sp.setParity(SerialPort.Parity.NONE);
                break;
        }
        try {
            m = ModbusMasterFactory.createModbusMasterRTU(sp);
            m.setResponseTimeout(100);
            m.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ModbusDisconnect() {
        try {
            m.disconnect();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }

    public boolean ModbusisConnected() {
        boolean a = false;
        if (m == null) {
            return false;
        } else {
            if (m.isConnected()) {
                a = true;
            }
            return a;
        }

    }

    public void ModbuswriteSingleRegister(int slaveId, int offset, int quantity) {
        try {
            m.writeSingleRegister(slaveId, offset, quantity);
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }

    public String ModbusreadHoldingRegisters(int slaveId, int offset, int quantity) {
        try {
            int[] i = m.readHoldingRegisters(slaveId, offset, quantity);
            StringBuffer sb = new StringBuffer();
            sb.append(i[0]);
            return sb.toString();
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
