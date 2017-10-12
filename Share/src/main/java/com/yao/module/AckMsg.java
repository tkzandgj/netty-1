package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 请求类型的消息
 */
public class AckMsg extends BaseMsg {
    public AckMsg() {
        super();
        setType(MsgType.ACK);
    }
    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}
