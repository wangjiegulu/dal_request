package com.wangjie.dal.request.library.gson.configuration;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import com.dangbei.xlog.XLog;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 08/08/2017.
 */
public class AnnotationSerializationExclusionStrategy implements ExclusionStrategy {
    private static final String TAG = AnnotationSerializationExclusionStrategy.class.getSimpleName();

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        try {
            GsonExclude gsonExclude = f.getAnnotation(GsonExclude.class);
            return null != gsonExclude && gsonExclude.serialize();
        } catch (Throwable throwable) {
            XLog.e(TAG, throwable);
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
