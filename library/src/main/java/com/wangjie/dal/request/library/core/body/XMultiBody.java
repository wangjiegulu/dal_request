package com.wangjie.dal.request.library.core.body;

import okhttp3.RequestBody;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 12/14/16.
 */
public class XMultiBody {
    //    public static final String IMAGE = "image/png";
    public static final String IMAGE = "image/*";
    private String filename;
    private RequestBody requestBody;

    public XMultiBody(String filename, RequestBody requestBody) {
        this.filename = filename;
        this.requestBody = requestBody;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
