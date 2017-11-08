package com.wangjie.dal.request.gson.configuration;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import android.util.Log;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 08/08/2017.
 */
public class AnnotationDeserializationExclusionStrategy implements ExclusionStrategy {
    private static final String TAG = AnnotationDeserializationExclusionStrategy.class.getSimpleName();

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        try {
            GsonExclude gsonExclude = f.getAnnotation(GsonExclude.class);
            return null != gsonExclude && gsonExclude.deserialize();
        } catch (Throwable throwable) {
            Log.e(TAG, "", throwable);
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
