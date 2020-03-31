package com.ml.multichain.client.controller;

import com.ml.multichain.client.model.MultichainOperationResult;
import com.ml.multichain.client.util.*;
import multichain.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author mengl
 */
@RestController
public class BlockController {
    private static Logger LOGGER = LoggerFactory.getLogger(BlockController.class);

    /**
     * 获取block info
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param blockHeight
     * @return
     */
    @RequestMapping(value = "/getBlockInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                   @RequestParam(value = "blockHeight", required = true) String blockHeight) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (blockHeight == null || blockHeight.isEmpty()) {
            LOGGER.error("blockHeight is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("blockHeight is null", false));
        }
        if (!CommonUtil.getInstance().isValidLong(blockHeight.trim())) {//not a valid number
            LOGGER.error(blockHeight + " is not a valid long");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(blockHeight + " is not a valid long", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockInfoUtil.getInstance().getBlockInfo(cm, blockHeight.trim());
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getBlockCountForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockCountForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                    @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                    @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                    @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockInfoUtil.getInstance().getBlockCount(cm);
    }

    /**
     * Returns information about the last or recent blocks in the active chain. Omit skip or set to 0 for information about the most recent block.
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getLastBlockInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getLastBlockInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                       @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                       @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                       @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockInfoUtil.getInstance().getLastBlockInfo(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param blockHeight
     * @return
     */
    @RequestMapping(value = "/getBlockHashForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockHashForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                   @RequestParam(value = "blockHeight", required = true) String blockHeight) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (blockHeight == null || blockHeight.isEmpty()) {
            LOGGER.error("blockHeight is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("blockHeight is null", false));
        }
        if (!CommonUtil.getInstance().isInteger(blockHeight.trim())) {//not a valid number
            LOGGER.error(blockHeight + " is not a valid number");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(blockHeight + " is not a valid number", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockInfoUtil.getInstance().getBlockHash(cm, blockHeight);
    }


    /**
     * Returns information about the blocks specified, on the active chain only. The blocks parameter can contain a comma-delimited list or
     * array of block heights, hashes, height ranges (e.g. 100-200) or -n for the most recent n blocks
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param blockIdentifiers
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/listBlocksForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String listBlocksForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                 @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                 @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                 @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                 @RequestParam(value = "blockIdentifiers", required = true) String blockIdentifiers,
                                 @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (blockIdentifiers == null || blockIdentifiers.isEmpty()) {
            LOGGER.error("blockIdentifiers is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("blockIdentifiers is null", false));
        }
        if (verbose == null || verbose.isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid String value to represent a boolean, for example:true/false", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockInfoUtil.getInstance().listBlocks(cm, blockIdentifiers, verbose);
    }
}
