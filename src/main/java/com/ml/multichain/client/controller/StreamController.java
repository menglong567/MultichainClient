package com.ml.multichain.client.controller;

import com.ml.multichain.client.model.MultichainOperationResult;
import com.ml.multichain.client.util.*;
import multichain.command.CommandManager;
import multichain.object.MultichainStreamItemJsonValueObject;
import multichain.object.MultichainStreamItemTextValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author mengl
 */
@RestController
public class StreamController {
    private static Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    /**
     * @param hostIp
     * @param rpcPort
     * @param rpcUser
     * @param rpcUserPwd
     * @param streamIdentifiers a stream name, ref or creation txid in streams to retrieve information about one stream only, an array thereof for multiple streams, or * for all streams
     * @param verbose
     * @return
     */
    @RequestMapping(value = "/getAllStreamsForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String getAllStreamForm(@RequestParam(value = "hostIp", required = true) String hostIp,
                                   @RequestParam(value = "rpcPort", required = true) String rpcPort,
                                   @RequestParam(value = "rpcUser", required = true) String rpcUser,
                                   @RequestParam(value = "rpcUserPwd", required = true) String rpcUserPwd,
                                   @RequestParam(value = "streamIdentifiers", required = true) String streamIdentifiers,
                                   @RequestParam(value = "verbose", required = true) String verbose) {
        MultichainOperationResult varifyResult = BlockChainUtil.getInstance().varifyConnectionParameters(hostIp, rpcPort, rpcUser, rpcUserPwd);
        if (!varifyResult.isResult()) {//if varify failed
            return GSonUtil.getInstance().object2Json(varifyResult);
        }
        if (streamIdentifiers == null || streamIdentifiers.isEmpty()) {
            LOGGER.error("streamIdentifiers is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("streamIdentifiers is null", false));
        }
        if (verbose == null || verbose.isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid String value to represent a boolean, for example:true/false", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().getAllStreams(cm, streamIdentifiers, verbose);
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
        if (verbose == null || verbose.isEmpty()) {
            LOGGER.error("verbose is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("verbose is null", false));
        }
        if (!verbose.trim().equalsIgnoreCase("true") && !verbose.trim().equalsIgnoreCase("false")) {
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(verbose + " is not a valid String value to represent a boolean, for example:true/false", false));
        }
        if (limit == null || limit.isEmpty()) {
            LOGGER.error("limit is null");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult("limit is null", false));
        }
        if (!CommonUtil.getInstance().isInteger(limit.trim())) {//not a valid number
            LOGGER.error(limit + " is not a valid number");
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(limit + " is not a valid number", false));
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager(hostIp.trim(), rpcPort.trim(), rpcUser.trim(), rpcUserPwd.trim());
        return StreamUitl.getInstance().getStreamItems(cm, streamidentifier.trim(), Boolean.valueOf(verbose.trim()), Integer.parseInt(limit.trim()));
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
}
