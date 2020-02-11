package com.ml.multichain.client.controller;

import com.ml.multichain.client.model.MultichainOperationResult;
import com.ml.multichain.client.util.BlockChainUtil;
import com.ml.multichain.client.util.CommandManagerUtil;
import com.ml.multichain.client.util.GSonUtil;
import multichain.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author mengl
 */
@RestController
public class BlockChainController {
    private static Logger LOGGER = LoggerFactory.getLogger(BlockChainController.class);

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getMempoolInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getMempoolInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                     @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                     @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                     @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getMempoolInfo(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getNetworkInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getNetworkInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                     @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                     @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                     @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getNetworkInfo(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getBlockchainParamsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockchainParamsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                          @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                          @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                          @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getBlockchainParams(cm);
    }


    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getRuntimeParamsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getRuntimeParamsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                       @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                       @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                       @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getRuntimeParas(cm);
    }

    /**
     * 获取节点钱包地址
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getBlockChainNodeWalletAddressesForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockChainNodeWalletAddressesForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                                       @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                                       @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                                       @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getBlockChainWalletAddresses(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getNewWalletAddressForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getNewWalletAddressForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                          @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                          @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                          @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getNewWalletAddress(cm);
    }

    /**
     * 获取节点的peer节点信息
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getPeerInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getPeerInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                  @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                  @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                  @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getPeerInfo(cm);
    }

    /**
     * 获取节点详细信息
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getBlockChainInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockChainInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                        @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                        @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                        @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return BlockChainUtil.getInstance().getBlockChainInfo(cm);
    }
}
