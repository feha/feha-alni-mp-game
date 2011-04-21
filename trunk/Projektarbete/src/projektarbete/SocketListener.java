package projektarbete;

public class SocketListener {
    private static boolean listening;

    public static boolean isListening() {
        return listening;
    }

    public static void setListening(boolean listening) {
        SocketListener.listening = listening;
    }



    public static void listen() {
        Runnable runnable = new Runnable() {

            public void run() {
                while(isListening()) {
                    UDPSocket.listen().parse();
                    //System.out.println("Received Packet");
                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
