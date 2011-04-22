package projektarbete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import projektarbete.physics.ObjectData;
import projektarbete.physics.PhysicsData;
import projektarbete.physics.PhysicsUpdate;
import projektarbete.physics.ObjectUpdate;
import projektarbete.physics.PhysicsEngine;
import projektarbete.physics.Player;
import projektarbete.physics.PlayerUpdate;
import projektarbete.physics.Players;
import projektarbete.physics.Updates;

public class Communication {

    public static final short MESSAGE_TYPE_CONTROL = 1;
    public static final short MESSAGE_TYPE_CREATE_OBJECT = 10;
    public static final short MESSAGE_TYPE_REMOVE_OBJECT = 11;
    public static final short MESSAGE_TYPE_UPDATE_OBJECT = 12;
    public static final short MESSAGE_TYPE_CREATE_PLAYER = 13;
    public static final short MESSAGE_TYPE_REQUEST_OBJECT_DATA = 20;
    public static final short MESSAGE_TYPE_REQUEST_UPDATE = 21;
    public static final short MESSAGE_TYPE_UPDATE_PLAYER = 22;

    public static final short CONTROL_TYPE_CONNECT = 5;

    private String address = "";
    private byte[] data = new byte[0];

    public Communication() {

    }

    public Communication(String address, byte[] data) {
        this.address = address;
        this.data = data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public byte[] getData() {
        return data;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getAddress() {
        return address;
    }

    public void parse() {
        parse(this);
    }


    //static methods

    //input

    public static void parse(Communication communication) {
        InputData input = new InputData(communication.getData());
        short messageType = input.readMessageType();
        
        switch (messageType) {
            case MESSAGE_TYPE_CONTROL:
                control(input, communication.getAddress()); break;

            case MESSAGE_TYPE_CREATE_OBJECT:
                createObject(input); break;

            case MESSAGE_TYPE_REMOVE_OBJECT:
                removeObject(input); break;

            case MESSAGE_TYPE_UPDATE_OBJECT:
                updateObject(input); break;

            case MESSAGE_TYPE_CREATE_PLAYER:
                createPlayer(input); break;
                
            case MESSAGE_TYPE_REQUEST_OBJECT_DATA:
                requestObjectData(input); break;

            case MESSAGE_TYPE_REQUEST_UPDATE:
                requestUpdate(input); break;

            case MESSAGE_TYPE_UPDATE_PLAYER:
                updatePlayer(input); break;
        }
    }

    private static ObjectData readObjectData(InputData data) {
        short id = data.readId();
        PhysicsData newData = data.readPhysicsData();
        if (newData != null) {
            return new ObjectData(newData, id);
        } else {
            return null;
        }
    }

    private static ObjectData readPlayerData(InputData data) {
        short id = data.readId();
        PhysicsData newData = data.readPhysicsData();
        if (newData != null) {
            return new ObjectData(newData, id);
        } else {
            return null;
        }
    }

    private static short readRemoveObject(InputData data) {
        return data.readId();
    }

    private static ObjectUpdate readUpdateObject(InputData data) {
        short id = data.readId();
        PhysicsUpdate update = data.readPhysicsUpdate();
        if (update != null) {
            return new ObjectUpdate(update, id);
        } else {
            return null;
        }
    }
    
    private static short readRequestObjectData(InputData data) {
        return data.readId();
    }

    private static short readRequestUpdate(InputData data) {
        return data.readId();
    }

    private static PlayerUpdate readUpdatePlayer(InputData data) {
        short id = data.readId();
        PhysicsUpdate update = data.readPhysicsUpdate();
        byte flags = data.readFlags();
        Coordinate aim = data.readAim();
        if (update != null) {
            return new PlayerUpdate(update, flags, aim, id);
        } else {
            return null;
        }
    }

    //output

    public static byte[] writeUpdate(ObjectUpdate update) {
        OutputData output = new OutputData();
        short id = update.getId();
        PhysicsUpdate physicsUpdate = update.getUpdate();

        output.writeMessageType(MESSAGE_TYPE_UPDATE_OBJECT);
        output.writeId(id);
        output.writePhysicsUpdate(physicsUpdate);

        return output.getData();
    }

    public static byte[] writeObjectData(ObjectData data) {
        OutputData output = new OutputData();
        short id = data.getId();
        PhysicsData physicsData = data.getData();

        output.writeMessageType(MESSAGE_TYPE_CREATE_OBJECT);
        output.writeId(id);
        output.writePhysicsData(physicsData);

        return output.getData();
    }

    public static byte[] writePlayerData(ObjectData data) {
        OutputData output = new OutputData();
        short id = data.getId();
        PhysicsData physicsData = data.getData();

        output.writeMessageType(MESSAGE_TYPE_CREATE_PLAYER);
        output.writeId(id);
        output.writePhysicsData(physicsData);

        return output.getData();
    }

    public static byte[] writeRemoveObject(short id) {
        OutputData output = new OutputData();

        output.writeMessageType(MESSAGE_TYPE_REMOVE_OBJECT);
        output.writeId(id);

        return output.getData();
    }

    public static byte[] writeRequestObjectData(short id) {
        OutputData output = new OutputData();

        output.writeMessageType(MESSAGE_TYPE_REQUEST_OBJECT_DATA);
        output.writeId(id);

        return output.getData();
    }

    public static byte[] writeRequestUpdate(short id) {
        OutputData output = new OutputData();

        output.writeMessageType(MESSAGE_TYPE_REQUEST_UPDATE);
        output.writeId(id);

        return output.getData();
    }

    public static byte[] writePlayerUpdate(PlayerUpdate update) {
        OutputData output = new OutputData();
        short id = update.getId();
        PhysicsUpdate physicsUpdate = update.getUpdate();

        output.writeMessageType(MESSAGE_TYPE_UPDATE_PLAYER);
        output.writeId(id);
        output.writePhysicsUpdate(physicsUpdate);
        output.writeFlags(update.getFlags());
        output.writeAim(update.getAim());

        return output.getData();
    }

    public static byte[] writeControlConnect() {
        OutputData output = new OutputData();

        output.writeMessageType(MESSAGE_TYPE_CONTROL);
        output.writeControlType(Communication.CONTROL_TYPE_CONNECT);

        return output.getData();
    }

    //response

    private static void control(InputData data, String address) {
        short controlType = data.readControlType();

        switch (controlType) {


            case Communication.CONTROL_TYPE_CONNECT:
                PhysicsEngine.getInstance().createPlayer(address); break;
        }
    }

    private static void createObject(InputData data) {
        PhysicsEngine.getInstance().createObject(readObjectData(data));
    }

    private static void createPlayer(InputData data) {
        PhysicsEngine.getInstance().createPlayer(readPlayerData(data));
    }

    private static void removeObject(InputData data) {
        PhysicsEngine.getInstance().removeObject(readRemoveObject(data));
    }

    private static void requestObjectData(InputData data) {
        short id = Communication.readRequestObjectData(data);
        PhysicsEngine.getInstance().requestData(id);

        //more code to be added here
    }

    private static void requestUpdate(InputData data) {
        short id = Communication.readRequestUpdate(data);
        PhysicsEngine.getInstance().requestUpdate(id);
        //more code to be added here
    }

    private static void updatePlayer(InputData data) {
        PhysicsEngine.getInstance().updatePlayer(readUpdatePlayer(data));
    }

    private static void updateObject(InputData data) {
        PhysicsEngine.getInstance().updateObject(readUpdateObject(data));
    }

    //data

    private static class InputData {
        private ByteArrayInputStream byteInput;
        private DataInputStream dataInput;

        InputData(byte[] data) {
            byteInput = new ByteArrayInputStream(data);
            dataInput = new DataInputStream(byteInput);
        }

        short readMessageType() {
            try {
                return dataInput.readShort();
            } catch (IOException ex) {
            }
            return 0;
        }

        short readControlType() {
            try {
                return dataInput.readShort();
            } catch (IOException ex) {
            }
            return 0;
        }

        short readId() {
            try {
                return dataInput.readShort();
            } catch (IOException ex) {
            }
            return 0;
        }

        PhysicsData readPhysicsData() {
            try {
                short template = dataInput.readShort();
                double mass = dataInput.readDouble();
                double size = dataInput.readDouble();
                PhysicsUpdate update = readPhysicsUpdate();
                if (update != null) {
                    return new PhysicsData(template, mass, size, update);
                }
            } catch (IOException ex) {
            }
            return null;
        }

        PhysicsUpdate readPhysicsUpdate() {
            try {
                double x = dataInput.readDouble();
                double y = dataInput.readDouble();
                double xVel = dataInput.readDouble();
                double yVel = dataInput.readDouble();
                double angle = dataInput.readDouble();
                double angularVelocity = dataInput.readDouble();
                return new PhysicsUpdate(x, y, xVel, yVel, angle, angularVelocity);
            } catch (IOException ex) {
            }
            return null;
        }

        byte readFlags () {
            try {
                return dataInput.readByte();
            } catch (IOException ex) {
            }
            return 0;
        }

        Coordinate readAim () {
            try {
                double xAim = dataInput.readDouble();
                double yAim = dataInput.readDouble();
                return new Coordinate(xAim, yAim);
            } catch (IOException ex) {
            }
            return new Coordinate(0, 0);
        }

    }

    private static class OutputData {
        private ByteArrayOutputStream byteOutput;
        private DataOutputStream dataOutput;

        OutputData() {
            byteOutput = new ByteArrayOutputStream();
            dataOutput = new DataOutputStream(byteOutput);
        }

        byte[] getData() {
            return byteOutput.toByteArray();
        }

        void writeMessageType(short messageType) {
            try {
                dataOutput.writeShort(messageType);
            } catch (IOException ex) {
            }
        }

        void writeControlType(short controlType) {
            try {
                dataOutput.writeShort(controlType);
            } catch (IOException ex) {
            }
        }

        void writeId(short id) {
            try {
                dataOutput.writeShort(id);
            } catch (IOException ex) {
            }
        }

        void writePhysicsData(PhysicsData data) {
            try {
                dataOutput.writeShort(data.getTemplate());
                dataOutput.writeDouble(data.getMass());
                dataOutput.writeDouble(data.getSize());
                writePhysicsUpdate(data.getUpdate());
            } catch (IOException ex) {
            }
        }

        void writePhysicsUpdate(PhysicsUpdate update) {
            try {
                dataOutput.writeDouble(update.getPosition().x());
                dataOutput.writeDouble(update.getPosition().y());
                dataOutput.writeDouble(update.getVelocity().x());
                dataOutput.writeDouble(update.getVelocity().y());
                dataOutput.writeDouble(update.getAngle());
                dataOutput.writeDouble(update.getAngularVelocity());
            } catch (IOException ex) {
            }
        }

        void writeFlags (byte flags) {
            try {
                dataOutput.writeByte(flags);
            } catch (IOException ex) {
            }
        }

        void writeAim(Coordinate aim) {
            try {
                dataOutput.writeDouble(aim.x());
                dataOutput.writeDouble(aim.y());
            } catch (IOException ex) {
            }
        }
    }
}