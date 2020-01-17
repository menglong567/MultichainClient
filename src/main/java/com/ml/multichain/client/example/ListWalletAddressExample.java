/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ml.multichain.client.example;

import com.ml.multichain.client.util.CommandManagerUtil;

import java.util.ArrayList;

import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;

/**
 * @author Liudan_Luo
 */
public class ListWalletAddressExample {
    public static void main(String[] args) {
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager("192.168.1.105", "31005", "multichainrpc", "DaWnto6QrqL1fgFshxHLAXcYcmULR9F7c8qAsAaiQdcB");

        // localhost is the IP used by Multichain
        // 6824 is, here, the port used by the BlockChain, corresponding of the value of default-rpc-port in the file params.dat 
        // multichainrpc and 73oYQWzx45h... are login and password to access to RPC commands, values can be found in the file multichain.conf
        java.util.ArrayList addressResult = null;

        //result contains the addresses of the wallet as list of String (verbose is false).
        //class java.util.ArrayList
        //class java.lang.String
        try {
            addressResult = (ArrayList) cm.invoke(CommandElt.GETADDRESSES);//(verbose is false).
            for (Object add : addressResult) {
                System.out.println("Address:" + add.toString());
            }
        } catch (MultichainException e) {
            e.printStackTrace();
            e.getMessage();
            e.getReason();
        }

        //result contains the addresses of the wallet as list of Addresses (verbose is true).
        //class java.util.ArrayList
        //class multichain.object.Address
        try {
            addressResult = (ArrayList) cm.invoke(CommandElt.GETADDRESSES, true);//(verbose is true).
        } catch (MultichainException e) {
            e.printStackTrace();
            e.getMessage();
            e.getReason();
        }
    }
}
