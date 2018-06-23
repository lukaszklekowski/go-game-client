package client;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa odpowiedzialna za komunikację z serwerem
 */

public class LobbyCommunication {

    public BufferedReader in;
    public PrintWriter out;
    public Socket socket;
    Pane pane;

    /**
     * Konstruktor klasy tworzący połączenie z serwerem. Gdy połączenia nie udało się uzyskać otrzymujemy
     * iformację oraz klient wyłącza się.
     * @param host
     * @param port
     * @throws IOException
     */
    public LobbyCommunication(String host, int port) throws IOException {
        try {
            socket = new Socket(host, port);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("No connection with the server");
            alert.showAndWait();
            System.out.println("No I/O");
            System.exit(1);
        }

                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);


    }

    /**
     * Metoda sprawdzająca czy wersja klienta jest aktualna.
     * @return Zwraca WRONG_VERSION gdy wersja jest nieaktualna lub GOOD_VERSION gdy jest wporządku.
     */
    public String checkVersion(){
        String answer = null;
        try {
            answer = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(answer.equals("CONNECTED go_protocol v0.1_Alpha"))
        {
            out.println("PROTOCOL_MATCHES");
            out.flush();
            return "GOOD_VERSION";
        }
        else
        {
            out.println("WRONG_VERSION");
            return "WRONG_VERSION";
        }
    }
}
