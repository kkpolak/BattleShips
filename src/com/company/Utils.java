package com.company;

import java.net.*;

public class Utils {
    static InetAddress findAddress() throws SocketException, UnknownHostException {
        var wlp3s0 = NetworkInterface.getByName("lo");
        return wlp3s0.inetAddresses()
                .filter(a -> a instanceof Inet4Address)
                .findFirst()
                .orElse(InetAddress.getLocalHost());
    }


    static void ender() {
        for (int i = 0; i < 10; i++) {
            System.out.println("counting: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
