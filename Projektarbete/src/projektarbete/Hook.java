/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 *
 * @author felix.hallqvist
 */
public class Hook {

    public Hook() {

        System.out.println("Hook Initializing");

        initComponents();
        initTesting();

        System.out.println("Hook Initialized");

    }

    private static LinkedList<Method>[] methodReferences = new LinkedList[10];
    private static LinkedList<Object>[] objectReferences = new LinkedList[10];

    private void initComponents() {



    }

    private void initTesting() {

    }

    public static void add(int id, Method methodReference, Object objectReference) {

        if (methodReferences == null) {
            methodReferences[id] = new LinkedList<Method>();
        }
        if (methodReferences[id] == null) {
            methodReferences[id] = new LinkedList<Method>();
        }
        if (objectReferences[id] == null) {
            objectReferences[id] = new LinkedList<Object>();
        }

        methodReferences[id].add(methodReference);
        objectReferences[id].add(objectReference);

    }

    public static void call(int id, String test) {

        LinkedList<Method> methodTable = methodReferences[id];
        LinkedList<Object> objectTable = objectReferences[id];

        if (methodTable != null & objectTable != null) {
            for (int i = 0; i < methodTable.size(); i++) {
                try {
                    methodTable.get(i).invoke(objectTable.get(i), new Object[] {test});
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }

    }

}
