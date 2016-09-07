import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class JavaWebPage {

    public static void main(String[] args) throws Exception {
        JavaWebPage jwp = new JavaWebPage();
        jwp.handling();
    }

    public void handling() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/f.html", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("***********REQUEST RECEIVED ###########");
            try {

                new Thread(new HandleThread(t)).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HandleThread implements Runnable {

        private HttpExchange t;
        private String query = "hello";

        public HandleThread(HttpExchange h) throws Exception {
            this.t = h;
        }

        public void run() {
            try {
                Thread.sleep(3000);
                this.t.sendResponseHeaders(200, query.length());
                OutputStream os = this.t.getResponseBody();
                os.write(query.getBytes());
                os.close();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*public void handling() throws IOException {
        HttpServer server;
        int nThreads = 5;
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(nThreads));
        server.start();
    }

    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            new Thread(new HandleThread(t)).start();
        }
    }

    class HandleThread implements Runnable {

        private HttpExchange t;

        public HandleThread(HttpExchange h) {
            this.t = h;
        }

        public void run() {
            try {
                long threadId = Thread.currentThread().getId();
                System.out.println(threadId);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadId = Thread.currentThread().getId();
                System.out.println(threadId);
                String response;
                OutputStream os;
                response = "This was a query:";
                this.t.sendResponseHeaders(200, response.length());
                os = this.t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


}