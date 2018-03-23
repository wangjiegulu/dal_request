package com.wangjiegulu.dal.request.gson;

import com.google.gson.Gson;

import com.wangjiegulu.dal.request.core.converter.ResponseConverter;
import com.wangjiegulu.dal.request.core.request.XRequest;
import com.wangjiegulu.dal.request.util.XIOUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 23/03/2018.
 */
public class DefaultGsonResponseConverter implements ResponseConverter {

    private Gson gson;

    public static DefaultGsonResponseConverter create() {
        return new DefaultGsonResponseConverter(DalGsonHelper.getOriginalGson());
    }

    public static DefaultGsonResponseConverter create(Gson gson) {
        return new DefaultGsonResponseConverter(gson);
    }

    private DefaultGsonResponseConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> T convert(XRequest xRequest, byte[] responseBytes, Type responseType) {
        Reader reader = null;
        try {
            return gson.fromJson(reader = new InputStreamReader(new ByteArrayInputStream(responseBytes)), responseType);
        } finally {
            XIOUtil.closeIO(reader);
        }
    }
}
