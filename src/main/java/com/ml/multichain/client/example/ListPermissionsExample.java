package com.ml.multichain.client.example;

import com.ml.multichain.client.util.CommandManagerUtil;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.Permission;

import java.util.List;

/**
 * @author mengl
 */
public class ListPermissionsExample {
    public static void main(String[] args) {
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager("192.168.1.105", "31005", "multichainrpc", "DaWnto6QrqL1fgFshxHLAXcYcmULR9F7c8qAsAaiQdcB");
        // localhost is the IP used by Multichain
        // 6824 is, here, the port used by the BlockChain, corresponding of the value of default-rpc-port in the file params.dat
        // multichainrpc and 73oYQWzx45h... are login and password to access to RPC commands, values can be found in the file multichain.conf
        List<Permission> listPermissions = null;
        try {
            listPermissions = (List<Permission>) cm.invoke(CommandElt.LISTPERMISSIONS);
            for (Object permission : listPermissions) {
                System.out.println(permission.toString());
            }
        } catch (MultichainException e) {
            e.printStackTrace();
            e.getMessage();
            e.getReason();
        }
    }
}
