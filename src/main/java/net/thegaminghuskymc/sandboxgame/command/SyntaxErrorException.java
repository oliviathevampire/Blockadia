package net.thegaminghuskymc.sandboxgame.command;

public class SyntaxErrorException extends CommandException {
    public SyntaxErrorException() {
        this("commands.generic.syntax");
    }

    public SyntaxErrorException(String message, Object... replacements) {
        super(message, replacements);
    }

    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}