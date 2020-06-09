import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 10000;
    private static final String CLOSE_COMMAND = "BYE";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String line = br.readLine();
                bw.write(line.toUpperCase() + System.lineSeparator());
                System.out.println("READ: " + line);
                bw.flush();
                if (line.toUpperCase().equals(CLOSE_COMMAND)) {
                    break;
                }
            }
            socket.close();
        }
    }
}