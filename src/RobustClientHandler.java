import java.io.*;
import java.net.Socket;

public class RobustClientHandler extends ClientHandler {

    public RobustClientHandler(Socket socket, Server server){
        super(socket, server);
    }

    public void run() {
        try (socket) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    System.err.println("Client abruptly closed connection");
                    break;
                }
                if (line.toUpperCase().equals(server.getQuitCommand())) {
                    break;
                }
                bw.write(server.process(line) + System.lineSeparator());
                bw.flush();
            }
        } catch (IOException e) {
            System.err.printf("IO error: %s\n", e);
        }
    }
}
