package com.epam.training.cafe.command;

public class CommandFactory {

    private final static String COMMAND_LOGIN = "login";

    public static Command create(String commandName) {

        Command command = null;

        switch (commandName) {
            case COMMAND_LOGIN : {
                command = new LoginCommand();
            } break;
        }

        return command;
    }
}
