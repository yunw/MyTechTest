package cn.mwee.service.paidui.common.filter;

import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.github.kristofa.brave.http.*;
import com.github.kristofa.brave.servlet.ServletHttpServerRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by yinshunlin on 2016/8/23.
 */
@Component("zipkinFilter")
public class ZipkinFilter implements Filter {

    @Setter
    private Brave brave;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("--------start zipkin()--------------------");
        if (RpcContext.getContext().getRequest() != null && RpcContext.getContext().getRequest() instanceof HttpServletRequest) {
            System.out.println("Client address is " + ((HttpServletRequest) RpcContext.getContext().getRequest()).getRemoteAddr());
            HttpServletRequest req = (HttpServletRequest) RpcContext.getContext().getRequest();
            Enumeration e = req.getHeaderNames();
            while (e.hasMoreElements()) {
                String name=(String)e.nextElement();
                String value=req.getHeader(name);
                System.out.println(name + " = " + value);
            }

            HttpServletResponse response = (HttpServletResponse) RpcContext.getContext().getResponse();
            final StatusExposingServletResponse statusExposingServletResponse = new StatusExposingServletResponse((HttpServletResponse) response);
            RpcContext.getContext().setResponse(statusExposingServletResponse);

            ServerRequestInterceptor requestInterceptor = brave.serverRequestInterceptor();
            SpanNameProvider spanNameProvider = new DefaultSpanNameProvider();
            requestInterceptor.handle(new HttpServerRequestAdapter(new ServletHttpServerRequest((HttpServletRequest) req), spanNameProvider));
            try {
                return invoker.invoke(invocation);
            }  finally {
                ServerResponseInterceptor responseInterceptor = brave.serverResponseInterceptor();
                responseInterceptor.handle(new HttpServerResponseAdapter(new HttpResponse() {
                    @Override
                    public int getHttpStatusCode() {
                        return statusExposingServletResponse.getStatus();
                    }
                }));
            }
        }
        System.out.println("--------end zipkin()--------------------");
        return invoker.invoke(invocation);
    }

    private static class StatusExposingServletResponse extends HttpServletResponseWrapper {
        // The Servlet spec says: calling setStatus is optional, if no status is set, the default is OK.
        private int httpStatus = HttpServletResponse.SC_OK;

        public StatusExposingServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) throws IOException {
            httpStatus = sc;
            super.sendError(sc);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            httpStatus = sc;
            super.sendError(sc, msg);
        }

        @Override
        public void setStatus(int sc) {
            httpStatus = sc;
            super.setStatus(sc);
        }

        public int getStatus() {
            return httpStatus;
        }
    }

}
