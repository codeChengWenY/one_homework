package servlet;

import server.HttpProtocolUtil;
import server.HttpServlet;
import server.Request;
import server.Response;

import java.io.IOException;

/**
 * @ClassName servlet.DemoServlet1
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-27 14:42
 * @Version V1.0
 **/
public class DemoServlet2 extends HttpServlet {


    @Override
    public void doGet(Request request, Response response) {

        String content = "<h1>DemoServlet2 get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>DemoServlet2 post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
