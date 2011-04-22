/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author felix.hallqvist
 */
public class Players {

    public Players() {

    }

    private static Map<Short, Player> players = new TreeMap<Short, Player>();

    public static synchronized void addPlayer( Player player, short id ) {

        players.put(id, player);

        //Add more code to make this shit work over the network (maybe elsewhere)
        /*
         * How about server creating the player, then send its id and data to
         the client, which will be the only player the client need to know about
         * The other players can be physic objects, as the only difference is a
         cameracontainer and some movement code.
         * The client player acts like normal, but repeatedly updates its
         position, velocity and pressed buttons.
         ** what about sending the movement as a directional vector, this way
            we offer support for other ways to control it, such as joysticks
         * The server does not control the player very much, instead it
         updates the client as the client tells it to, then it uses the physics
         of it to do stuff like bulelts hitting, and until next time client
         updates the player is server side
         * This causes 2 problems, what if a player was hit on the server but
         not on the client, should we still pretend it took damage, or should
         all damage a client takes be something it has to tell the server about?
         ** and how about collisions, if a player suddenly moves into a wall,
            should the server stop it and tell clients of its revised position?
        */
    }

    public static synchronized void removePlayer( Player player ) {

        players.values().remove(player);

    }

    public static synchronized void removePlayer( short id ) {

        players.remove(id);

    }

    public static synchronized void clearPlayers() {

        players.clear();


    }

    public static synchronized List<Player> getAll() {
        List<Player> all = new ArrayList<Player>(players.values());
        return all;
    }

    public static synchronized Player findPlayerByID(short id) {
        return players.get(id);
    }

    public static synchronized int getCount() {
        return players.size();
    }

    public static synchronized boolean isPlayer(short id) {
        return players.containsKey(id);
    }

    public static synchronized boolean isPlayer(PhysicsObject object) {
        return object instanceof Player && players.containsValue((Player) object);
    }


    /*public static synchronized void updatePlayer(ObjectUpdate update) {

        int id = update.getId();
        
        ArrayList<Player> all = getAll();
        Player player = (Player) all.get(id);

        player.applyUpdate(update.getUpdate());

    }*/

}
