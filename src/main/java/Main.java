import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    static final String OK_RESPONSE = "HTTP/1.1 200 OK\r\n\r\n";
    static final String NOT_FOUND_RESPONSE = "HTTP/1.1 404 Not Found\r\n\r\n";

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

            var dataStrings = new String(data).split("\\r\\n");
            var requestInfo = dataStrings[0];
            var requestInfoStrings = requestInfo.split(" ");


            if (requestInfoStrings[1].equals("/")) {
                clientSocket.getOutputStream().write(OK_RESPONSE.getBytes());
            } else {
                clientSocket.getOutputStream().write(NOT_FOUND_RESPONSE.getBytes());
            }


        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
