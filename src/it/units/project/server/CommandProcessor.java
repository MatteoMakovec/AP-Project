package it.units.project.server;

public interface CommandProcessor {
    String process(String input);
    String getQuitCommand();
}
