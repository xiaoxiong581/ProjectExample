package com.yzx.xiaoxiong581.projectexample.websocket.adapter;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;

/**
 * @author xiaoxiong581
 */
public class WebSocketClientAdapter extends Thread {
    private String url;

    private int sendCount;

    private WebSocketClient client;

    private String name;

    public WebSocketClientAdapter(String url, int sendCount, String name) {
        this.url = url;
        this.sendCount = sendCount;
        this.name = name;

        try {
            client = new WebSocketClient(new URI(url), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("client open connect success");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println(name + " receive: " + s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("client connect is closed");
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("client occur error " + e);
                }
            };
        } catch (Exception e) {
            System.out.println("websocket occur error " + e);
        }
    }

    @Override
    public void run() {
        if (this.url.startsWith("wss://")) {
            initSSL();
        }
        client.connect();
        while (!client.getReadyState().equals(ReadyState.OPEN)) {
        }

        int i = 0;
        while (i < sendCount) {
            System.out.println(name + " send: " + i);
            client.send(name + String.valueOf(i));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            i++;
        }
        client.close();
    }

    private void initSSL() {
        try {
            String STORETYPE = "JKS";
            String KEYSTORE = "/cert/websocketclient.jks";
            String STOREPASSWORD = "xiaoxiong581";
            String KEYPASSWORD = "xiaoxiong581";

            KeyStore ks = KeyStore.getInstance(STORETYPE);
            File kf = new File(this.getClass().getResource(KEYSTORE).getFile());
            ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KEYPASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            client.setSocketFactory(factory);
        } catch (Exception e) {
            System.out.println("init client ssl failed, error: " + e);
        }
    }
}
