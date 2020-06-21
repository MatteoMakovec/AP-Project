package it.units.project.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.err.println("You should set the port number");
        }
        else{
            Server server = new Server(Integer.valueOf(args[0]), "BYE");
            server.start();
        }
    }
}