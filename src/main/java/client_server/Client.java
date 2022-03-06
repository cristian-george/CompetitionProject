package client_server;

import database.dao.PersonDao;
import database.model.PersonEntity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int PORT = 6543;
    private static ClientHandler clientHandler = null;

    public void start() throws Exception {
        Socket socket = new Socket("localhost", PORT);
        clientHandler = new ClientHandler();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        boolean isClose = false;
        while (!isClose) {

            if (clientHandler.getUsername() == null) {
                clientHandler.printLoginOptions();
            } else {
                PersonDao personDao = new PersonDao();
                PersonEntity personEntity = personDao.getByUsername(clientHandler.getUsername());
                if (personEntity.getType().equals("competitor"))
                    clientHandler.printCompetitorOptions();
                else
                    clientHandler.printAdminOptions();
            }

            String messageToSend = scanner.nextLine();

            if (messageToSend.equals("EXIT")) {
                isClose = true;
            }

            Packet packet = new Packet(messageToSend);
            outputStream.writeObject(packet);

            Packet receivePacket = (Packet) inputStream.readObject();
            System.out.println(receivePacket.message);

            String[] message = receivePacket.message.split(" ");

            if (message[0].equals("OKLOGIN")) {
                clientHandler.setUsername(message[1]);
                clientHandler.setIdPerson(Integer.valueOf(message[2]));
                clientHandler.setType(message[3]);
            }
        }

        socket.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
    }
}
