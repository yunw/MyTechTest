package cn.mwee.service.paidui.utils.aop;

import cn.mwee.service.paidui.utils.SpringUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientTracer;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Client;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by yinshunlin on 2016/8/2.
 */
@Aspect
public class RedisAop {

    public static RedisAop aspectOf() {
        return new RedisAop();
    }

    @Pointcut("execution(* redis.clients.jedis.BinaryJedis.*(..)) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.getClient()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.connect()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.getDB()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.checkIsInMultiOrPipeline()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.ping()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.isConnected()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.quit()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.disconnect()) "
            + "&& !execution(* redis.clients.jedis.BinaryJedis.resetState())")
    private void anyMethod() {
    }

    @Around("anyMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        System.out.println(",,,,,,,,,redis before............. pointcut: " + point.toString());
        System.out.println(point.getThis().getClass().getName() + "." + point.getSignature().getName());
        Client client = ((BinaryJedis) point.getThis()).getClient();
        String host = client.getHost();
        int port = client.getPort();
        Brave brave = (Brave) SpringUtil.getBean("brave");
        String methodName = point.getSignature().getName();
        beginTrace(brave.clientTracer(), host, port, methodName);
        result = point.proceed();
        System.out.println(",,,,,,,,,redis after.............. pointcut: " + point.toString());
        endTrace(brave.clientTracer());
        return result;
    }

    private void endTrace(final ClientTracer tracer) {
        tracer.setClientReceived();
    }

    private void beginTrace(final ClientTracer tracer, String host, int port, String methodName) throws SQLException {
        tracer.startNewSpan("redis operator");
        tracer.submitBinaryAnnotation("executed.redis", methodName);

        try {
            setClientSent(tracer, host, port);
        } catch (Exception e) { // logging the server address is optional
            tracer.setClientSent();
        }
    }

    private void setClientSent(ClientTracer tracer, String host, int port) throws Exception {
        InetAddress address = Inet4Address.getByName(host);
        int ipv4 = ByteBuffer.wrap(address.getAddress()).getInt();

        String serviceName = "redis-" + host + ":" + port;

        tracer.setClientSent(ipv4, port, serviceName);
    }

}
