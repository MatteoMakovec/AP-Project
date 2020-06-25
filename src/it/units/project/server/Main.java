package it.units.project.server;

import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.err.println("Port number must be set");
        }
        else {
            Server server = new ProcessingServer(Integer.valueOf(args[0]), "BYE");
            server.start();
        }
    }
}