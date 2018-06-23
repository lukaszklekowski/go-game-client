package client;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za inicjowanie klienta.
 */
public class Client1 {
    public static Client1 instance;
    public static LobbyCommunication servercomm;

    Client1() {
        this.init();
    }

    public void init() {
        try {
            this.servercomm = new LobbyCommunication("localhost", 9435);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
