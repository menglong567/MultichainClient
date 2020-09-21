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
public class AssetController {
    private static Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    /****
     * subscribe to an asset
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param assetIdentifier
     * @return
     */
    @RequestMapping(value = "/subscribeAssetForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String subscribeAssetForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                     @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                     @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                     @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                     @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().subscribeAsset(cm, assetIdentifier.trim());
    }

    /****
     * get the asset info
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param assetIdentifier
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/getAssetInfoForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getAssetInfo(@RequestParam(value = "hostIp", required = true) String hostIp,
                               @RequestParam(value = "rpcPort", required = true) String rpcPort,
                               @RequestParam(value = "rpcUser", required = true) String rpcUser,
                               @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                               @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier,
                               @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
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
        return AssetUtil.getInstance().getAssetInfo(cm, assetIdentifier, verbose.trim());
    }

    /**
     * Action is not supported under these blockchain parameters or runtime parameters
     * By default Accounts are not supported with scalable wallet - if you need getassetbalances, run multichaind -walletdbversion=1 -rescan, but the wallet will perform worse
     * command : multichaind testChain -datadir=/home/multichain-testmasternode/data/ -walletdbversion=1 -rescan
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param assetIdentifier
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/getAssetBalancesForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getAssetBalances(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                   @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier,
                                   @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
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
        return AssetUtil.getInstance().getAssetBalances(cm, assetIdentifier, verbose.trim());
    }

    /**
     * Retrieves a single specific transaction txid involving asset, passed as an asset name, ref or issuance txid, to which the node must be subscribed
     *
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param assetIdentifier
     * @param txid
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/getAssettransactionForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getAssettransactionForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                          @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                          @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                          @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                          @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier,
                                          @RequestParam(value = "txid", required = true) String txid,
                                          @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
        }
        if (txid == null || txid.trim().isEmpty()) {
            LOGGER.error("txid is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("txid is null", false));
        }
        //txid should be hexadecimal


        if (verbose == null || verbose.trim().isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            LOGGER.error(verbose + " is not a valid boolean value");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid boolean value", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().getassettransaction(cm, assetIdentifier, txid, verbose.trim());
    }

    /****
     * list the asset related transactions
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param assetIdentifier
     * @param verbose
     * @param count
     * @return
     */
    @RequestMapping(value = "/listAssetTransactionsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String listAssetTransactionsForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                            @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                            @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                            @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                            @RequestParam(value = "assetIdentifier", required = true) String assetIdentifier,
                                            @RequestParam(value = "verbose", required = true) String verbose,
                                            @RequestParam(value = "count", required = true) String count) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assetIdentifier == null || assetIdentifier.trim().isEmpty()) {
            LOGGER.error("assetIdentifier is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assetIdentifier is null", false));
        }
        if (verbose == null || verbose.trim().isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            LOGGER.error(verbose + " is not a valid boolean value");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid boolean value", false));
        }
        if (count == null || count.trim().isEmpty()) {
            LOGGER.error("count is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("count is null", false));
        }
        if (!CommonUtil.getInstance().isInteger(count.trim())) {
            LOGGER.error(count + " is not a valid number");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(count + " is not a valid number", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().listAssetTransactions(cm, assetIdentifier, verbose.trim(), Integer.parseInt(count.trim()));
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
                                        @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                        @RequestParam(value = "assets", required = true) String assets,
                                        @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (assets == null || assets.trim().isEmpty()) {
            LOGGER.error("assets is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("assets is null", false));
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
        return AssetUtil.getInstance().listBalanceAssets(cm, assets, verbose);
    }

    /**
     * Returns a list of all the asset balances for address in this node’s wallet
     *
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
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        if (walletAddress == null || walletAddress.trim().isEmpty()) {
            LOGGER.error("walletAddress is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("walletAddress is null", false));
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
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().getTotalBalances(cm);
    }

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param walletAddress   this address should have receive permission to receive asset
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
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
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
        if (!CommonUtil.getInstance().isValidDouble(amount.trim())) {
            LOGGER.error(amount + " is not a valid double");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(amount + " is not a valid double", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return AssetUtil.getInstance().sendAssetToWalletAddress(cm, walletAddress, assetIdentifier, amount.trim());
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
        MultichainOperationResult verifyResult = BlockChainUtil.getInstance().verifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!verifyResult.isResult()) {//if verify failed
            return GSonUtil.getInstance().object2Json(verifyResult);
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
}
