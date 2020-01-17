/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ml.multichain.client.util;

import multichain.command.CommandManager;

/**
 * @author Liudan_Luo
 */
public class CommandManagerUtil {
    private static final CommandManagerUtil instance = new CommandManagerUtil();
    private CommandManager commandManager;

    private CommandManagerUtil() {
    }

    public static CommandManagerUtil getInstance() {
        return instance;
    }

    public CommandManager getCommandManager(String hostname, String rpcport, String rpcuser, String password) {
        commandManager = new CommandManager(hostname, rpcport, rpcuser, password);
        return commandManager;
    }
}
