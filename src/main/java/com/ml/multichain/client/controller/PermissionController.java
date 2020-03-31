package com.ml.multichain.client.controller;

import com.ml.multichain.client.model.MultichainOperationResult;
import com.ml.multichain.client.util.BlockChainUtil;
import com.ml.multichain.client.util.CommandManagerUtil;
import com.ml.multichain.client.util.GSonUtil;
import com.ml.multichain.client.util.PermissionGrantUtil;
import multichain.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author mengl
 */
@RestController
public class PermissionController {
    private static Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    /**
     * grant global permission like connect, send, receive, create, issue, mine, activate, admin
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param walletAddress
     * @param permissionIdentifier
     * @return
     */
    @RequestMapping(value = "/grantGlobalPermissionForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String grantGlobalPermissionForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                            @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                            @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                            @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                            @RequestParam(value = "walletAddress", required = true) String walletAddress,
                                            @RequestParam(value = "permissionIdentifier", required = true) String permissionIdentifier) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (walletAddress == null || walletAddress.trim().isEmpty()) {
            LOGGER.error("walletAddress is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("walletAddress is null", false));
        }
        if (permissionIdentifier == null || permissionIdentifier.trim().isEmpty()) {
            LOGGER.error("permissionIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("permissionIdentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return PermissionGrantUtil.getInstance().grantGlobalPermission(cm, walletAddress.trim(), permissionIdentifier.trim());
    }

    /**
     * grant stream permission like root.write / demostream.write
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param walletAddress
     * @param permissionIdentifier
     * @return
     */
    @RequestMapping(value = "/grantStreamPermissionForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String grantStreamPermissionForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                            @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                            @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                            @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                            @RequestParam(value = "walletAddress", required = true) String walletAddress,
                                            @RequestParam(value = "permissionIdentifier", required = true) String permissionIdentifier) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (walletAddress == null || walletAddress.trim().isEmpty()) {
            LOGGER.error("walletAddress is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("walletAddress is null", false));
        }
        if (permissionIdentifier == null || permissionIdentifier.trim().isEmpty()) {
            LOGGER.error("permissionIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("permissionIdentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return PermissionGrantUtil.getInstance().grantStreamPermission(cm, walletAddress.trim(), permissionIdentifier.trim());
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param permissionIdentifier
     * @return
     */
    @RequestMapping(value = "/getStreamPermissionsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getStreamPermissionsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                           @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                           @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                           @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                           @RequestParam(value = "permissionIdentifier", required = true) String permissionIdentifier) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (permissionIdentifier == null || permissionIdentifier.trim().isEmpty()) {
            LOGGER.error("permissionIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("permissionIdentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return PermissionGrantUtil.getInstance().getStreamPermissions(cm, permissionIdentifier.trim());
    }

    /**
     * 获取节点钱包地址下的权限
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param wallletAddresses
     * @param permissionIdentifier
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/getBlockChainNodeWalletAddressesPermissionsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getBlockChainNodeWalletAddressesPermissionsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                                                  @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                                                  @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                                                  @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                                                  @RequestParam(value = "wallletAddresses", required = true) String wallletAddresses,
                                                                  @RequestParam(value = "permissionIdentifier", required = true) String permissionIdentifier,
                                                                  @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (wallletAddresses == null || wallletAddresses.trim().isEmpty()) {
            LOGGER.error("wallletAddresses is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("wallletAddresses is null", false));
        }
        if (permissionIdentifier == null || permissionIdentifier.trim().isEmpty()) {
            LOGGER.error("permissionIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("permissionIdentifier is null", false));
        }
        if (verbose == null || verbose.trim().isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            LOGGER.error(verbose + " is not a valid boolean value");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid boolean value", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return PermissionGrantUtil.getInstance().getBlockChainNodeWalletAddressesPermissions(cm, wallletAddresses.trim(), permissionIdentifier.trim(), Boolean.valueOf(verbose.trim()));
    }
}
