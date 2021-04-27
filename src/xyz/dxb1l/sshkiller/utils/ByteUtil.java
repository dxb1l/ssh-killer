package xyz.dxb1l.sshkiller.utils;


public class ByteUtil {

    public static byte[] generate(){
        byte[] bytes = new byte[1000];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (i % 2 == 0 ? 1 : 0);
        }
        return bytes;
    }

}
