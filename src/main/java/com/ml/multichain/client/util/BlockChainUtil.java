package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @author mengl
 */
public class BlockChainUtil {
    private static BlockChainUtil instance = new BlockChainUtil();
    private static Logger LOGGER = LoggerFactory.getLogger(BlockChainUtil.class);

    private BlockChainUtil() {
    }

    public static BlockChainUtil getInstance() {
        return instance;
    }

    /**
     * 获取节点钱包地址
     *
     * @param cm
     * @return
     */
    public String getBlockChainWalletAddresses(CommandManager cm) {
        Address address = null;
        List<Address> addresses = null;
        try {
            addresses = (List<Address>) cm.invoke(CommandElt.LISTADDRESSES);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(addresses);
    }

    /**
     * @param cm
     * @return
     */
    public String getNewWalletAddress(CommandManager cm) {
        String result = null;
        try {
            result = (String) cm.invoke(CommandElt.GETNEWADDRESS);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }

    /**
     * @param cm
     * @return
     */
    public String getWalletInfo(CommandManager cm) {
        WalletInfo walletInfo = null;
        try {
            walletInfo = (WalletInfo) cm.invoke(CommandElt.GETWALLETINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(walletInfo);
    }

    /**
     * @param cm
     * @return
     */
    public String getBlockchainParams(CommandManager cm) {
        HashMap<String, String> result = null;
        try {
            result = (HashMap<String, String>) cm.invoke(CommandElt.GETBLOCKCHAINPARAMS);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }

    /**
     * @param cm
     * @return
     */
    public String getRuntimeParas(CommandManager cm) {
        RuntimeParams result = null;
        try {
            result = (RuntimeParams) cm.invoke(CommandElt.GETRUNTIMEPARAMS);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }


    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @return
     */
    public MultichainOperationResult varifyConnectionParameters(String hostIp, String rpcPort, String rpcUser, String rpcUserPwd) {
        if (hostIp == null || hostIp.trim().isEmpty()) {
            LOGGER.error("hostIp is null");
            return new MultichainOperationResult("hostIp is null", false);
        }
        if (!CommonUtil.getInstance().validIPAddressAll(hostIp.trim())) {
            LOGGER.error(hostIp + " is not a valid ipv4 or ipv6 address");
            return new MultichainOperationResult(hostIp + " is not a valid ipv4 or ipv6 address", false);
        }
        if (rpcPort == null || rpcPort.trim().isEmpty()) {
            LOGGER.error("rpcPort is null");
            return new MultichainOperationResult("rpcPort is null", false);
        }
        if (!CommonUtil.getInstance().isInteger(rpcPort.trim())) {//not a valid number
            LOGGER.error(rpcPort + " is not a valid number");
            return new MultichainOperationResult(rpcPort + " is not a valid number", false);
        }
        if (Integer.parseInt(rpcPort.trim()) < 30000 || Integer.parseInt(rpcPort.trim()) > 32767) {
            LOGGER.error(rpcPort + " is beyond the valid range <30000,32767>");
            return new MultichainOperationResult("rpcPort is beyond the valid range from 30000 to 32767", false);
        }
        if (rpcUser == null || rpcUser.trim().isEmpty()) {
            LOGGER.error("rpcUser is null");
            return new MultichainOperationResult("rpcUser is null", false);
        }
        if (rpcUserPwd == null || rpcUserPwd.trim().isEmpty()) {
            LOGGER.error("rpcUserPwd is null");
            return new MultichainOperationResult("rpcUserPwd is null", false);
        }
        return new MultichainOperationResult("varifyConnectionParameters successfully", true);
    }

    /**
     * Returns general information about this node and blockchain
     *
     * @param cm
     * @return
     */
    public String getBlockChainInfo(CommandManager cm) {
        HashMap<String, Object> infos = new HashMap<String, Object>();
        try {
            infos = (HashMap<String, Object>) cm.invoke(CommandElt.GETINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(infos);
    }

    /**
     * @param cm
     * @return
     */
    public String getPeerInfo(CommandManager cm) {
        List<PeerInfo> peerInfos = null;
        try {
            peerInfos = (List<PeerInfo>) cm.invoke(CommandElt.GETPEERINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(peerInfos);
    }

    /**
     * @param cm
     * @return
     */
    public String getMempoolInfo(CommandManager cm) {
        MemPoolInfo memPoolInfo = null;
        try {
            memPoolInfo = (MemPoolInfo) cm.invoke(CommandElt.GETMEMPOOLINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(memPoolInfo);
    }

    /**
     * @param cm
     * @return
     */
    public String getNetworkInfo(CommandManager cm) {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = (NetworkInfo) cm.invoke(CommandElt.GETNETWORKINFO);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(networkInfo);
    }
}
