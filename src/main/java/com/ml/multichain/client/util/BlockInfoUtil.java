package com.ml.multichain.client.util;

import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockInfoUtil {

    private static final BlockInfoUtil instance = new BlockInfoUtil();
    private static Logger LOGGER = LoggerFactory.getLogger(BlockInfoUtil.class);

    private BlockInfoUtil() {
    }

    public static BlockInfoUtil getInstance() {
        return instance;
    }

    public String getBlockInfo(CommandManager cm, String height) {
        // localhost is the IP used by Multichain
        // 6824 is, here, the port used by the BlockChain, corresponding of the value of default-rpc-port in the file params.dat
        // multichainrpc and 73oYQWzx45h... are login and password to access to RPC commands, values can be found in the file multichain.conf
        Block block = null;
        try {
            block = (Block) cm.invoke(CommandElt.GETBLOCK, 1);
            System.out.println(block.toString());
        } catch (MultichainException e) {
            e.printStackTrace();
            e.getMessage();
            e.getReason();
        }
        return GSonUtil.getInstance().object2Json(block);
    }
}
