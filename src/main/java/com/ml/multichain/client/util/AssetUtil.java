package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.BalanceAsset;
import multichain.object.BalanceAssetGeneral;
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
     * @param units the minimal value to send
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
     * @param cm
     * @return
     */
    public String listBalanceAssets(CommandManager cm) {
        List<BalanceAsset> assets = new ArrayList<>();
        try {
            assets = (List<BalanceAsset>) cm.invoke(CommandElt.LISTASSETS);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(assets);
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
     * query balances for wallet address
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
     * @param amount if the asset units is 0.01 and you send for example 1.001 then only 1 will be sent
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

