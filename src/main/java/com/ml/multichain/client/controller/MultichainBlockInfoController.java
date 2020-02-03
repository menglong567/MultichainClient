package com.ml.multichain.client.controller;

import com.ml.multichain.client.model.MultichainOperationResult;
import com.ml.multichain.client.util.*;
import multichain.command.CommandManager;
import multichain.object.MultichainStreamItemJsonValueObject;
import multichain.object.MultichainStreamItemTextValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultichainBlockInfoController {
    private static Logger LOGGER = LoggerFactory.getLogger(MultichainBlockInfoController.class);

    @RequestMapping(value = "/getBlockInfo/{height}", method = RequestMethod.GET)
    @ResponseBody
    public String getBlockInfo(@PathVariable String height) {
        if (!CommonUtil.getInstance().isInteger(height.trim())) {//not a valid number
            LOGGER.error(height + " is not a valid number");
            return height + " is not a valid number";
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager("192.168.1.105", "31005", "multichainrpc", "DaWnto6QrqL1fgFshxHLAXcYcmULR9F7c8qAsAaiQdcB");
        return BlockInfoUtil.getInstance().getBlockInfo(cm, "1");
    }

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
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
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
        return BlockInfoUtil.getInstance().getBlockInfo(cm, blockHeight.trim());
    }

    @RequestMapping(value = "/getAllStreamsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getAllStreamForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().getAllStreams(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamidentifier
     * @param verbose
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getStreamItemsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getStreamItemsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                     @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                     @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                     @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                     @RequestParam(value = "streamidentifier", required = true) String streamidentifier,
                                     @RequestParam(value = "verbose", required = true) String verbose,
                                     @RequestParam(value = "limit", required = true) String limit) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamidentifier == null || streamidentifier.isEmpty()) {
            LOGGER.error("streamidentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamidentifier is null", false));
        }
        if (!verbose.equalsIgnoreCase("true") && !verbose.equalsIgnoreCase("false")) {
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid String value to represent a boolean, for example:true/false", false));
        }
        if (!CommonUtil.getInstance().isInteger(limit.trim())) {//not a valid number
            LOGGER.error(limit + " is not a valid number");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(limit + " is not a valid number", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().getStreamItems(cm, streamidentifier.trim(), Boolean.valueOf(verbose), Integer.parseInt(limit));
    }


    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamidentifier
     * @return
     */
    @RequestMapping(value = "/getStreamInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getStreamInfoForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                    @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                    @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                    @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                    @RequestParam(value = "streamidentifier", required = true) String streamidentifier) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamidentifier == null || streamidentifier.isEmpty()) {
            LOGGER.error("streamidentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamidentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().getStreamInfo(cm, streamidentifier.trim());
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamidentifier
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/publishToStreamForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String publishToStreamForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                      @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                      @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                      @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                      @RequestParam(value = "streamidentifier", required = true) String streamidentifier,
                                      @RequestParam(value = "key", required = true) String key,
                                      @RequestParam(value = "value", required = true) String value) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamidentifier == null || streamidentifier.trim().isEmpty()) {
            LOGGER.error("streamidentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamidentifier is null", false));
        }
        if (key == null || key.trim().isEmpty()) {
            LOGGER.error("key is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("key is null", false));
        }
        if (value == null || value.trim().isEmpty()) {
            LOGGER.error("value is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("value is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        if (value.trim().contains("text")) {
            return StreamUitl.getInstance().publishToStream(cm, streamidentifier.trim(), key.trim(), value.trim(), MultichainStreamItemTextValueObject.class);
        } else if (value.trim().contains("json")) {
            return StreamUitl.getInstance().publishToStream(cm, streamidentifier.trim(), key.trim(), value.trim(), MultichainStreamItemJsonValueObject.class);
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult("value filed doesn't have the correct format!", false));
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamName
     * @return
     */
    @RequestMapping(value = "/createStreamForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String createStreamForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                   @RequestParam(value = "streamName", required = true) String streamName) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamName == null || streamName.trim().isEmpty()) {
            LOGGER.error("streamName is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamName is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().createStream(cm, streamName.trim());
    }

    /**
     * 订阅stream
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamidentifier
     * @return
     */
    @RequestMapping(value = "/subscribeStreamForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String subscribeStreamForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                      @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                      @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                      @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                      @RequestParam(value = "streamidentifier", required = true) String streamidentifier) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamidentifier == null || streamidentifier.trim().isEmpty()) {
            LOGGER.error("streamidentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamidentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().subscribeStream(cm, streamidentifier.trim());
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
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
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
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
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
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
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
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
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


    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/listBalanceAssetsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String listBalanceAssetsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                        @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                        @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                        @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().listBalanceAssets(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param walletAddress
     * @return
     */
    @RequestMapping(value = "/getWalletAddressBalancesForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getWalletAddressBalancesForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                               @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                               @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                               @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                               @RequestParam(value = "walletAddress", required = true) String walletAddress) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().getWalletAddressBalances(cm, walletAddress);
    }


    /**
     * query balances for all assets
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    @RequestMapping(value = "/getTotalBalancesForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getTotalBalancesForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                       @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                       @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                       @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().getTotalBalances(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param walletAddress
     * @param assetIdentifier
     * @param amount
     * @return
     */
    @RequestMapping(value = "/sendAssetToWalletAddressForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String sendAssetToWalletAddressForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                               @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                               @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                               @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                               @RequestParam(value = "walletAddress", required = true) String walletAddress,
                                               @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier,
                                               @RequestParam(value = "amount", required = true) String amount) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (walletAddress == null || walletAddress.trim().isEmpty()) {
            LOGGER.error("walletAddress is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("walletAddress is null", false));
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
        }
        if (amount == null || amount.trim().isEmpty()) {
            LOGGER.error("amount is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("amount is null", false));
        }
        if (CommonUtil.getInstance().isValidDouble(amount)) {
            LOGGER.error(amount + " is not a valid double");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(amount + " is not a valid double", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().sendAssetToWalletAddress(cm, walletAddress, assetIdentifier, amount);
    }


    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param wallletAddress
     * @param assetName
     * @param quantity
     * @param units
     * @param open
     * @param restrict
     * @return
     */
    @RequestMapping(value = "/createAssetForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String createAssetForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                  @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                  @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                  @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                  @RequestParam(value = "wallletAddress", required = true) String wallletAddress,
                                  @RequestParam(value = "assetName", required = true) String assetName,
                                  @RequestParam(value = "quantity", required = true) String quantity,
                                  @RequestParam(value = "units", required = true) String units,
                                  @RequestParam(value = "open", required = true) String open,
                                  @RequestParam(value = "restrict", required = true) String restrict) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (wallletAddress == null || wallletAddress.trim().isEmpty()) {
            LOGGER.error("wallletAddress is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("wallletAddress is null", false));
        }
        if (assetName == null || assetName.trim().isEmpty()) {
            LOGGER.error("assetName is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetName is null", false));
        }
        if (quantity == null || quantity.trim().isEmpty()) {
            LOGGER.error("quantity is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("quantity is null", false));
        }
        if (!CommonUtil.getInstance().isValidFloat(quantity.trim())) {//not a valid number
            LOGGER.error(quantity + " is not a valid float");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(quantity + " is not a valid float", false));
        }
        if (units == null || units.trim().isEmpty()) {
            LOGGER.error("units is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("units is null", false));
        }
        if (!CommonUtil.getInstance().isValidDouble(units.trim())) {//not a valid double
            LOGGER.error(units + " is not a valid double");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(units + " is not a valid double", false));
        }
        if (open == null || open.trim().isEmpty()) {
            LOGGER.error("open is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("open is null", false));
        }
        if (!open.trim().equalsIgnoreCase("true") && !open.trim().equalsIgnoreCase("false")) {
            LOGGER.error(open + " is not a valid string to present a boolean value");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(open + " is not a valid string to present a boolean value", false));
        }
        if (restrict == null || restrict.trim().isEmpty()) {
            LOGGER.error("restrict is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("restrict is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().createAsset(cm, wallletAddress.trim(), assetName.trim(), quantity.trim(), units.trim(), open.trim(), restrict.trim());
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
