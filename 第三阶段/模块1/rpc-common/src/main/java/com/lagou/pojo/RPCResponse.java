package com.lagou.pojo;

import lombok.Data;

/**
 * ClassName: RPCResponse <br/>
 * Description: <br/>
 * date: 2020-05-12 11:15<br/>
 *
 * @author colde<br />
 */
@Data
public class RPCResponse {

    private String requestId;
    private int code;
    private String error_msg;
    private Object data;

}
