package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.*;
import multichain.object.queryobjects.AssetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class AssetUtil {
    private static AssetUtil instance = new AssetUtil();
    private static Logger LOGGER = LoggerFactory.getLogger(AssetUtil.class);

    private AssetUtil() {
    }

    public static AssetUtil getInstance() {
        return instance;
    }


    /**
     * @param cm
     * @param wallletAddress
     * @param assetName
     * @param quantity
     * @param units          the minimal value to send
     * @param open
     * @param restrict
     * @return
     */
    public String createAsset(CommandManager cm, String wallletAddress, String assetName, String quantity, String units, String open, String restrict) {
        String result;
        AssetParams params = new AssetParams(assetName, Boolean.parseBoolean(open), restrict);
        try {
            result = (String) cm.invoke(CommandElt.ISSUE, wallletAddress, params, Float.parseFloat(quantity), Double.parseDouble(units));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }

    /**
     * Extra fields are shown for assets to which this node is subscribed
     * an asset name, ref or issuance txid in assets to retrieve information about one asset only, an array thereof for multiple assets, or * for all assets
     *
     * @param cm
     * @return
     */
    public String listBalanceAssets(CommandManager cm, String assets, String verbose) {
        List<BalanceAsset> balanceAssets = new ArrayList<>();
        try {
            balanceAssets = (List<BalanceAsset>) cm.invoke(CommandElt.LISTASSETS, assets, Boolean.valueOf(verbose));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(balanceAssets);
    }

    /**
     * @param cm
     * @param assetIdentifier
     * @return
     */
    public String subscribeAsset(CommandManager cm, String assetIdentifier) {
        String result;
        try {
            result = (String) cm.invoke(CommandElt.SUBSCRIBE, assetIdentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }

    /**
     * query balances for all assets
     *
     * @param cm
     * @return
     */
    public String getTotalBalances(CommandManager cm) {
        List<BalanceAssetGeneral> assets = new ArrayList<>();
        try {
            assets = (List<BalanceAssetGeneral>) cm.invoke(CommandElt.GETTOTALBALANCES);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(assets);
    }

    /**
     * Action is not supported under these blockchain parameters or runtime parameters
     * By default Accounts are not supported with scalable wallet - if you need getassetbalances, run multichaind -walletdbversion=1 -rescan, but the wallet will perform worse
     * command : multichaind testChain -datadir=/home/multichain-testmasternode/data/ -walletdbversion=1 -rescan
     *
     * @param cm
     * @param assetIdentifier
     * @param verbose
     * @return
     */
    public String getAssetBalances(CommandManager cm, String assetIdentifier, String verbose) {
        List<BalanceAsset> balanceAssets = new ArrayList<>();
        try {
            balanceAssets = (List<BalanceAsset>) cm.invoke(CommandElt.GETASSETBALANCES, assetIdentifier, Boolean.valueOf(verbose.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(balanceAssets);
    }

    /**
     * Need to subscribe the asset firstly or Not subscribed to this asset message will be thrown
     * Retrieves a single specific transaction txid involving asset, passed as an asset name, ref or issuance txid, to which the node must be subscribed
     *
     * @param cm
     * @param assetIdentifier
     * @param txid
     * @param verbose
     * @return
     */
    public String getassettransaction(CommandManager cm, String assetIdentifier, String txid, String verbose) {
        TransactionWallet transactionWallet;
        try {
            transactionWallet = (TransactionWallet) cm.invoke(CommandElt.GETASSETTRANSACTION, assetIdentifier, txid, Boolean.valueOf(verbose.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(transactionWallet);
    }


    /**
     * Lists transactions involving asset, passed as an asset name, ref or issuance txid, to which the node must be subscribed
     * Set verbose to true for additional information about each transaction
     * Use count and start to retrieve part of the list only
     *
     * @param cm
     * @param assetIdentifier
     * @param verbose
     * @param count
     * @return
     */
    public String listAssetTransactions(CommandManager cm, String assetIdentifier, String verbose, int count) {
        List<AssetTransaction> assetTransactions;
        try {
            assetTransactions = (List<AssetTransaction>) cm.invoke(CommandElt.LISTASSETTRANSACTIONS, assetIdentifier, Boolean.valueOf(verbose.trim()), count);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(assetTransactions);
    }

    /**
     * query balances for wallet address
     * Returns a list of all the asset balances for address in this node’s wallet
     *
     * @param cm
     * @param walletAddress
     * @return
     */
    public String getWalletAddressBalances(CommandManager cm, String walletAddress) {
        List<BalanceAsset> assets = new ArrayList<>();
        try {
            assets = (List<BalanceAsset>) cm.invoke(CommandElt.GETADDRESSBALANCES, walletAddress);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(assets);
    }

    /**
     * @param cm
     * @param assetIdentifier is the asset name, ref or issuance txid
     * @param verbose
     * @return
     */
    public String getAssetInfo(CommandManager cm, String assetIdentifier, String verbose) {
        AssetInfo assetInfo;
        try {
            assetInfo = (AssetInfo) cm.invoke(CommandElt.GETASSETINFO, assetIdentifier, Boolean.valueOf(verbose.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(assetInfo);
    }


    /**
     * @param cm
     * @param amount          if the asset units is 0.01 and you send for example 1.001 then only 1 will be sent
     * @param assetIdentifier
     * @param walletAddress
     * @return
     */
    public String sendAssetToWalletAddress(CommandManager cm, String walletAddress, String assetIdentifier, String amount) {
        String result;
        try {
            result = (String) cm.invoke(CommandElt.SENDASSET, walletAddress, assetIdentifier, Float.parseFloat(amount.trim()));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }
}

