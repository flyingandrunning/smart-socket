/*
 * Copyright (c) 2017, org.smartboot. All rights reserved.
 * project name: smart-socket
 * file name: ReadCompletionHandler.java
 * Date: 2017-11-25 10:29:55
 * Author: sandao
 */

package org.smartboot.socket.transport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.socket.Filter;
import org.smartboot.socket.StateMachineEnum;

import java.nio.channels.CompletionHandler;

/**
 * 读写事件回调处理类
 */
class ReadCompletionHandler<T> implements CompletionHandler<Integer, AioSession<T>> {
    private static final Logger LOGGER = LogManager.getLogger(ReadCompletionHandler.class);

    @Override
    public void completed(final Integer result, final AioSession<T> aioSession) {
        // 接收到的消息进行预处理
        for (Filter h : aioSession.getServerConfig().getFilters()) {
            h.readFilter(aioSession, result);
        }
        aioSession.readFromChannel(result == -1);
    }

    @Override
    public void failed(Throwable exc, AioSession<T> aioSession) {
        LOGGER.debug("smart-socket read fail:", exc);
        try {
            aioSession.getServerConfig().getProcessor().stateEvent(aioSession, StateMachineEnum.INPUT_EXCEPTION, exc);
        } catch (Exception e) {
            LOGGER.catching(e);
        }
        try {
            aioSession.close();
        } catch (Exception e) {
            LOGGER.catching(e);
        }
    }
}