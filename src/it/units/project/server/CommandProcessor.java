package it.units.project.server;

@FunctionalInterface
public interface CommandProcessor {
    String process(String input);
}
