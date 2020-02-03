package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.GrantElt;
import multichain.command.MultichainException;
import multichain.object.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengl
 */
public class PermissionGrantUtil {
    private static PermissionGrantUtil instance = new PermissionGrantUtil();

    private PermissionGrantUtil() {
    }

    public static PermissionGrantUtil getInstance() {
        return instance;
    }

    /**
     * @param cm
     * @param walletAddress
     * @param permissionIdentifier
     * @return
     */
    public String grantGlobalPermission(CommandManager cm, String walletAddress, String permissionIdentifier) {
        String result = null;
        try {
            List<GrantElt> grantElements = new ArrayList<>();
            String[] identifiers = permissionIdentifier.split(",");
            for (String identifier : identifiers) {
                if (identifier.equals(GrantElt.SEND.getGrant())) {//send
                    grantElements.add(GrantElt.SEND);
                } else if (identifier.equals(GrantElt.CONNECT.getGrant())) {//connect
                    grantElements.add(GrantElt.CONNECT);
                } else if (identifier.equals(GrantElt.RECEIVE.getGrant())) {//reveive
                    grantElements.add(GrantElt.RECEIVE);
                } else if (identifier.equals(GrantElt.ISSUE.getGrant())) {//issue
                    grantElements.add(GrantElt.ISSUE);
                } else if (identifier.equals(GrantElt.MINE.getGrant())) {//mine
                    grantElements.add(GrantElt.MINE);
                } else if (identifier.equals(GrantElt.ACTIVATE.getGrant())) {//activate
                    grantElements.add(GrantElt.ACTIVATE);
                } else if (identifier.equals(GrantElt.ADMIN.getGrant())) {//admin
                    grantElements.add(GrantElt.ADMIN);
                } else if (identifier.equals(GrantElt.CREATE.getGrant())) {//create
                    grantElements.add(GrantElt.CREATE);
                }
            }
            result = (String) cm.invoke(CommandElt.GRANT, walletAddress, GrantElt.grantElements(grantElements));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }

    /**
     * grant permission for asset like asset.issue,send,receive,activate,admin
     *
     * @param cm
     * @param walletAddress
     * @param permissionIdentifier
     * @return
     */
    public String grantAssetPermission(CommandManager cm, String walletAddress, String permissionIdentifier) {
        String result = null;
        try {
            result = (String) cm.invoke(CommandElt.GRANT, walletAddress, permissionIdentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }

    /**
     * grant permission for stream like stream.write,read,activate,admin
     *
     * @param cm
     * @param walletAddress
     * @param permissionIdentifier
     * @return
     */
    public String grantStreamPermission(CommandManager cm, String walletAddress, String permissionIdentifier) {
        String result = null;
        try {
            result = (String) cm.invoke(CommandElt.GRANT, walletAddress, permissionIdentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(result);
    }


    /**
     * 获取节点钱包地址下的权限
     * get global permissions or asset permissions or stream permissions against to one or more addresses
     * for global permission (connect, send, receive, issue, mine, activate, admin, or a comma-separated list,Omit or pass * or all to list all global permissions)
     * for asset permission(asset.issue,send,receive,activate,admin or asset.*)
     * for stream permission(stream.write,read,activate,admin or stream.*)
     * If verbose is true, the admins output field lists the administrator/s who assigned the corresponding permission, and the pending field lists permission changes which are waiting to reach consensus
     *
     * @param cm
     * @param wallletAddresses     comma-delimited list in addresses to list the permissions for particular addresses or * for all addresses
     * @param verbose
     * @param permissionIdentifier
     * @return
     */
    public String getBlockChainNodeWalletAddressesPermissions(CommandManager cm, String wallletAddresses, String permissionIdentifier, boolean verbose) {
        List<Permission> list;
        try {
            //listpermissions send 12rDdFmMLyprVdwL4SUBTqN8t7sabYoA2yW6r3 true
            list = (List<Permission>) cm.invoke(CommandElt.LISTPERMISSIONS, permissionIdentifier, wallletAddresses, verbose);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(list);
    }

    /**
     * @param cm
     * @param permissionIdentifier ex: root.* / root.send
     * @return
     */
    public String getStreamPermissions(CommandManager cm, String permissionIdentifier) {
        List<Permission> list;
        try {
            list = (List<Permission>) cm.invoke(CommandElt.LISTPERMISSIONS, permissionIdentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(list);
    }
}
