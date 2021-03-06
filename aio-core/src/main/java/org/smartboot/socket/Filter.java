/*
 * Copyright (c) 2017, org.smartboot. All rights reserved.
 * project name: smart-socket
 * file name: Filter.java
 * Date: 2017-11-25 10:29:55
 * Author: sandao
 */

package org.smartboot.socket;

import org.smartboot.socket.transport.AioSession;

/**
 * 消息过滤器
 *
 * @author 三刀
 */
public interface Filter<T> {

    /**
     * 数据读取过滤,可用于统计流量
     *
     * @param session
     * @param readSize  本次解码读取的数据长度
     */
    public void readFilter(AioSession<T> session, int readSize);


    /**
     * 消息处理前置预处理
     *
     * @param session
     * @param msg 编解码后的消息实体
     */
    public void processFilter(AioSession<T> session, T msg);


    /**
     * 消息接受失败处理
     *
     * @param session
     * @param msg 编解码后的消息实体
     * @param e         本次处理异常对象
     */
    public void processFailHandler(AioSession<T> session, T msg, Throwable e);

    /**
     * 数据输出过滤,可用于统计流量
     *
     * @param session
     * @param writeSize  本次输出的数据长度
     */
    public void writeFilter(AioSession<T> session, int writeSize);

}
