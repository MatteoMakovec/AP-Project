package it.units.project.server;

import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.err.println("["+new Date()+"] " + "You should set the port number");
        }
        else{
            Server server = new ProcessingServer(Integer.valueOf(args[0]), "BYE");
            server.start();
        }
    }
}