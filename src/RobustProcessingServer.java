import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RobustProcessingServer extends Server {

    public RobustProcessingServer(int port, String quitCommand) {
        super(port, quitCommand);
    }

    public void start () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    ClientHandler clientHandler = new RobustClientHandler(socket, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("Cannot accept connection due to %s", e);
                }
            }
        }
    }
}
