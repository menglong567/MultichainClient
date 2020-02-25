package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.Block;
import multichain.object.LastBlockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author mengl
 */
public class BlockInfoUtil {

    private static final BlockInfoUtil instance = new BlockInfoUtil();
    private static Logger LOGGER = LoggerFactory.getLogger(BlockInfoUtil.class);

    private BlockInfoUtil() {
    }

    public static BlockInfoUtil getInstance() {
        return instance;
    }

    /**
     * @param cm
     * @param height
     * @return
     */
    public String getBlockInfo(CommandManager cm, String height) {
        // localhost is the IP used by Multichain
        // 6824 is, here, the port used by the BlockChain, corresponding of the value of default-rpc-port in the file params.dat
        // multichainrpc and 73oYQWzx45h... are login and password to access to RPC commands, values can be found in the file multichain.conf
        Block block = null;
        try {
            block = (Block) cm.invoke(CommandElt.GETBLOCK, Long.parseLong(height));
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(block);
    }

    /**
     * @param cm
     * @return
     */
    public String getBlockCount(CommandManager cm) {
        Double count = null;
        try {
            count = (Double) cm.invoke(CommandElt.GETBLOCKCOUNT);
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(count);
    }

    /**
     * @param cm
     * @param blockHeight e.g 10
     * @return
     */
    public String getBlockHash(CommandManager cm, String blockHeight) {
        String hash = null;
        try {
            hash = (String) cm.invoke(CommandElt.GETBLOCKHASH, Long.parseLong(blockHeight.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(hash);
    }

    /**
     * Returns information about the last or recent blocks in the active chain. Omit skip or set to 0 for information about the most recent block.
     *
     * @param cm
     * @return
     */
    public String getLastBlockInfo(CommandManager cm) {
        LastBlockInfo info = null;
        try {
            info = (LastBlockInfo) cm.invoke(CommandElt.GETLASTBLOCKINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(info);
    }

    /**
     * Returns information about the blocks specified, on the active chain only. The blocks parameter can contain a comma-delimited list or
     * array of block heights, hashes, height ranges (e.g. 100-200) or -n for the most recent n blocks
     *
     * @param cm
     * @param blockIdentifiers
     * @param verbose
     * @return
     */
    public String listBlocks(CommandManager cm, String blockIdentifiers, String verbose) {
        List<Block> blocks = null;
        try {
            blocks = (List<Block>) cm.invoke(CommandElt.LISTBLOCKS, blockIdentifiers, Boolean.valueOf(verbose.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(blocks);
    }

}
