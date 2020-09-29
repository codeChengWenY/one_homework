package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;
    private Map<String, HttpServlet> servletMap;

    List<String> appsServletPath;


    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap, List<String> appsServletPath) {
        this.socket = socket;
        this.servletMap = servletMap;
        this.appsServletPath = appsServletPath;

    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if (servletMap.get(request.getUrl()) == null) {
                String url = request.getUrl().substring(request.getUrl().indexOf("/"), request.getUrl().lastIndexOf("/"));
                url = url.replace("/", "");
                if (appsServletPath.get(0).contains(url) && !"".equals(url)) {
                    response.outputAppHtml(appsServletPath.get(0) + "\\" + "index.html");
                } else if (appsServletPath.get(1).contains(url) && !"".equals(url)) {
                    response.outputAppHtml(appsServletPath.get(1) + "\\" + "index.html");
                } else {
                    response.outputHtml(request.getUrl());
                }
            } else {
                // 动态资源servlet请求
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
