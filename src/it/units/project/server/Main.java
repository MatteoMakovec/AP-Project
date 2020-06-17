package it.units.project.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(15000, "BYE");
        server.start();
    }
}