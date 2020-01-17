package com.ml.multichain.client.controller;

import com.ml.multichain.client.util.BlockInfoUtil;
import com.ml.multichain.client.util.CommandManagerUtil;
import com.ml.multichain.client.util.CommonUtil;
import multichain.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultichainBlockInfoController {
    private static Logger LOGGER = LoggerFactory.getLogger(MultichainBlockInfoController.class);

    @RequestMapping(value = "/getBlockInfo/{height}", method = RequestMethod.GET)
    @ResponseBody
    public String getBlockInfo(@PathVariable String height) {
        if (!CommonUtil.getInstance().isInteger(height)) {//not a valid number
            LOGGER.error(height + " is not a valid number");
            return height + " is not a valid number";
        }
        CommandManager cm = CommandManagerUtil.getInstance().getCommandManager("192.168.1.105", "31005", "multichainrpc", "DaWnto6QrqL1fgFshxHLAXcYcmULR9F7c8qAsAaiQdcB");
        return BlockInfoUtil.getInstance().getBlockInfo(cm, "1");
    }
}
