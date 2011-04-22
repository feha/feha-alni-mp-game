package projektarbete.physics;

import java.util.LinkedList;
import java.util.List;
import projektarbete.Communication;
import projektarbete.Stage;

public class Updates {
    private static List<PhysicsObject> remove = new LinkedList<PhysicsObject>();
    private static List<PhysicsObject> create = new LinkedList<PhysicsObject>();
    private static List<PhysicsObject> update = new LinkedList<PhysicsObject>();
    private static LinkedList<PhysicsObject> sync = new LinkedList<PhysicsObject>();

    
    private static List<Short> dataRequest = new LinkedList<Short>();
    private static List<Short> updateRequest = new LinkedList<Short>();
    private static int syncOffset;
    public static final int DESIRED_MESSAGE_AMOUNT = 10;

    public static void requestUpdate(short id) {
        if (!updateRequest.contains(id)) {
            updateRequest.add(id);
        }
    }
    
    public static void requestData(short id) {
        if (!dataRequest.contains(id)) {
            dataRequest.add(id);
        }
    }

    public static void remove(PhysicsObject object) {
        if (!remove.contains(object)) {
            remove.add(object);
        }
    }

    public static void create(PhysicsObject object) {
        if (!create.contains(object)) {
            create.add(object);
        }
    }

    public static void update(PhysicsObject object) {
        if (!update.contains(object)) {
            update.add(object);
        }
    }

    public static void sync(List<PhysicsObject> objects) {
        sync.addAll(sync);
    }

    public static List<Communication> getUpdates() {
        for (int i =0; i<syncOffset; i++) {
            sync.addLast(sync.removeFirst());
        }

        create.removeAll(remove);
        update.removeAll(remove);
        sync.removeAll(remove);
        update.removeAll(create);
        sync.removeAll(create);
        sync.removeAll(update);
        updateRequest.removeAll(dataRequest);

        List<Communication> messageList = new LinkedList<Communication>();
        
        for (short element : dataRequest) {
            Communication communication = new Communication();
            communication.setData(Communication.writeRequestObjectData(element));
            messageList.add(communication);
        }

        for (short element : updateRequest) {
            Communication communication = new Communication();
            communication.setData(Communication.writeRequestUpdate(element));
            messageList.add(communication);
        }

        for (PhysicsObject element : remove) {
            Communication communication = new Communication();
            communication.setData(Communication.writeRemoveObject(element.getId()));
            messageList.add(communication);
        }

        for (PhysicsObject element : create) {
            Communication communication = new Communication();
            ObjectData data = new ObjectData(element.getData(), element.getId());
            communication.setData(Communication.writeObjectData(data));
            messageList.add(communication);
        }
        
        for (PhysicsObject element : update) {
            Communication communication = new Communication();
            ObjectUpdate data = new ObjectUpdate(element.getUpdate(), element.getId());
            communication.setData(Communication.writeUpdate(data));
            messageList.add(communication);
        }

        int syncSize = sync.size();
        while(messageList.size() < DESIRED_MESSAGE_AMOUNT && !sync.isEmpty()) {
            Communication communication = new Communication();
            PhysicsObject element = sync.pop();
            ObjectUpdate data = new ObjectUpdate(element.getUpdate(), element.getId());
            communication.setData(Communication.writeUpdate(data));
            messageList.add(communication);

            syncOffset++;
            if (syncOffset >= syncSize) {
                syncOffset = 0;
            }
        }

        remove.clear();
        create.clear();
        update.clear();
        sync.clear();

        for (Communication message : messageList) {
            String address = Stage.getConnection();
            message.setAddress(address);
        }
        return messageList;
    }

}
