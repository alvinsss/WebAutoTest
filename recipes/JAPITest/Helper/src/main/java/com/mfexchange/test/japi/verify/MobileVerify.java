package com.elong.test.japi.verify;

import java.util.Map;

public class MobileVerify extends JsonVerify {
    protected Map<String, String> requests;

    public MobileVerify(Map<String, String> requests, String json) {
        super(json);
        this.requests = requests;
    }
}
