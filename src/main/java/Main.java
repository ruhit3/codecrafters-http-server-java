import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket clientSocket;
        try (ServerSocket serverSocket = new ServerSocket(4221)) {
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.
            System.out.println("Accepted new connection!");

            StringBuilder data = new StringBuilder();
            var inputStream = clientSocket.getInputStream();
            while (inputStream.available() != 0) {
                char c = (char) inputStream.read();
                data.append(c);
            }
            System.out.println("Data: " + data);

            String response = "HTTP/1.1 200 OK\r\n\r\n";
            clientSocket.getOutputStream().write(response.getBytes());

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
