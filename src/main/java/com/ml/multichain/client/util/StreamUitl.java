package com.ml.multichain.client.util;

import com.ml.multichain.client.model.MultichainOperationResult;
import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.Stream;
import multichain.object.StreamKeyItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengl
 */
public class StreamUitl {
    private static final StreamUitl instance = new StreamUitl();

    private StreamUitl() {
    }

    public static StreamUitl getInstance() {
        return instance;
    }


    /**
     * query all streams
     *
     * @param cm
     * @return
     */
    public String getAllStreams(CommandManager cm) {
        // localhost is the IP used by Multichain
        // 6824 is, here, the port used by the BlockChain, corresponding of the value of default-rpc-port in the file params.dat
        // multichainrpc and 73oYQWzx45h... are login and password to access to RPC commands, values can be found in the file multichain.conf
        List<Stream> streams = new ArrayList<>();
        try {
            streams = (List<Stream>) cm.invoke(CommandElt.LISTSTREAMS);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(streams);
    }

    /**
     * @param cm
     * @param streamidentifier
     * @return
     */
    public String getStreamInfo(CommandManager cm, String streamidentifier) {
        Stream stream = null;
        try {
            stream = (Stream) cm.invoke(CommandElt.GETSTREAMINFO, streamidentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(stream);
    }

    /**
     * stream (verbose=false)(count=10) (start=-count)(local-ordering=false)
     * Lists items in stream, passed as a stream name, ref or creation txid.
     * Set verbose to true for additional information about each item’s transaction.
     * Use count and start to retrieve part of the list only, with negative start values (like the default)
     * indicating the most recent items. Set local-ordering to true to order items by when first seen by this node,
     * rather than their order in the chain
     *
     * @param cm
     * @param streamidentifier
     * @param verbose
     * @param limit
     * @return
     */
    public String getStreamItems(CommandManager cm, String streamidentifier,boolean verbose,int limit) {
        List<StreamKeyItem> items = new ArrayList<>();
        try {
            items = (List<StreamKeyItem>) cm.invoke(CommandElt.LISTSTREAMITEMS, streamidentifier, verbose, limit, 0);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(items);
    }

    /**
     * 注意，发布的数据不是随便发布的，要对应与模型类
     *
     * @param cm
     * @param streamidentifier
     * @param key
     * @param value
     * @return
     */
    public String publishToStream(CommandManager cm, String streamidentifier, String key, String value, Class c) {
        String result;
        try {
            result = (String) cm.invoke(CommandElt.PUBLISH, streamidentifier, key, GSonUtil.getInstance().json2Object(value, c));
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }

    /**
     * @param cm
     * @param streamName
     * @return
     */
    public String createStream(CommandManager cm, String streamName) {

        String result;
        try {
            result = (String) cm.invoke(CommandElt.CREATE, "stream", streamName, false);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }

    /**
     * @param cm
     * @param streamidentifier
     * @return
     */
    public String subscribeStream(CommandManager cm, String streamidentifier) {
        String result;
        try {
            result = (String) cm.invoke(CommandElt.SUBSCRIBE, streamidentifier);
        } catch (MultichainException e) {
            e.printStackTrace();
            //should return meaningful message to the front-end
            return GSonUtil.getInstance().object2Json(new MultichainOperationResult(e.getMessage(), false));
        }
        return GSonUtil.getInstance().object2Json(new MultichainOperationResult(result, true));
    }
}
