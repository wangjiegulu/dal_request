package com.wangjiegulu.dal.request.core.converter;

import com.wangjiegulu.dal.request.core.request.XRequest;

import java.lang.reflect.Type;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 23/03/2018.
 */
public interface ResponseConverter {
    <T> T convert(XRequest xRequest, byte[] responseBytes, Type responseType);
}
