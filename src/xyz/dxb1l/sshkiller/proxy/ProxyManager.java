package xyz.dxb1l.sshkiller.proxy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProxyManager {

    private static final List<Proxy> SOCKS_PROXIES = new ArrayList<>();
    private static final AtomicInteger SOCKS_INDEX = new AtomicInteger();

    public static void loadProxy() {
        try {
            System.out.println("[SSH-Killer] Loading proxies...");
            final URL website = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4");
            final URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String proxy = "";
                String port = "";
                String[] split = inputLine.split(":");
                proxy = split[0];
                port = split[1];
                SOCKS_PROXIES.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy, Integer.parseInt(port))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Socket createSocket(Proxy proxy) {
        try {
            Socket socket = new Socket(proxy);
            Class<?> clazz = socket.getClass();
            Field field = clazz.getDeclaredField("impl");
            field.setAccessible(true);

            SocketImpl socketImpl = (SocketImpl) field.get(socket);
            Class<?> clazzSocksImpl = socketImpl.getClass();
            Method method = clazzSocksImpl.getDeclaredMethod("setV4");
            method.setAccessible(true);
            method.invoke(socketImpl);
            field.set(socket, socketImpl);

            return socket;
        } catch (Exception e) {
            return null;
        }
    }
    public static Proxy getSocks() {
        if (SOCKS_INDEX.get() >= SOCKS_PROXIES.size()) {
            SOCKS_INDEX.set(0);
        }

        return SOCKS_PROXIES.get(SOCKS_INDEX.getAndIncrement());
    }


}
