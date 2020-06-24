package it.units.project.server;

import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args == null){
            System.err.println("["+new Date()+"] " + "You must set the port number");
        }
        else if(args.length != 0){
            Server server = new ProcessingServer(Integer.valueOf(args[0]), "BYE");
            server.start();
        }
    }
}