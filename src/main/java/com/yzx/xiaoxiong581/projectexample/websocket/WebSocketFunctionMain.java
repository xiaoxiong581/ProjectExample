package com.yzx.xiaoxiong581.projectexample.websocket;

import com.yzx.xiaoxiong581.projectexample.websocket.adapter.WebSocketClientAdapter;

/**
 * @author xiaoxiong581
 */
public class WebSocketFunctionMain {
    public static void main(String[] args) throws Exception {
        WebSocketClientAdapter clientAdapter = new WebSocketClientAdapter("ws://127.0.0.1:29080/customermanager/v1/chart/yangzhixiong", 5, "yangzhixiong");
        WebSocketClientAdapter clientAdapter1 = new WebSocketClientAdapter("ws://127.0.0.1:29080/customermanager/v1/chart/dongwen", 10, "dongwen");
        WebSocketClientAdapter clientAdapter2= new WebSocketClientAdapter("ws://127.0.0.1:29080/customermanager/v1/chart/fencun", 15, "fencun");
        clientAdapter.start();
        clientAdapter1.start();
        clientAdapter2.start();
    }
}
