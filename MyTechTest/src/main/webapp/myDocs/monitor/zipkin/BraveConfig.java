package com.test.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.SpanCollectorMetricsHandler;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.kafka.KafkaSpanCollector;
import com.github.kristofa.brave.mysql.MySQLStatementInterceptorManagementBean;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import com.github.kristofa.brave.servlet.BraveServletFilter;

import okhttp3.OkHttpClient;

@Configuration
public class BraveConfig {

    @Autowired
    private ZipkinProperties zipkinProperties;
    
    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public SpanCollector spanCollector() {
        KafkaSpanCollector.Config cnf = KafkaSpanCollector.Config.builder().kafkaProperties(kafkaProperties.getProps())
                .flushInterval(zipkinProperties.getFlushInterval()).build();

        return KafkaSpanCollector.create(cnf, new SpanCollectorMetricsHandler() {

            @Override
            public void incrementAcceptedSpans(int quantity) {
                System.out.println("kafka accepted: " + quantity);
            }

            @Override
            public void incrementDroppedSpans(int quantity) {
                System.out.println("kafka dropped: " + quantity);
            }

        });

    }

//     @Bean
//     public SpanCollector spanCollector() {
//     HttpSpanCollector.Config config = HttpSpanCollector.Config.builder()
//     .connectTimeout(zipkinProperties.getConnectTimeout()).readTimeout(zipkinProperties.getReadTimeout())
//     .compressionEnabled(zipkinProperties.isCompressionEnabled()).flushInterval(zipkinProperties.getFlushInterval())
//     .build();
//     return HttpSpanCollector.create(zipkinProperties.getUrl(), config, new
//     SpanCollectorMetricsHandler() {
//    
//     @Override
//     public void incrementAcceptedSpans(int quantity) {
//     System.out.println("accepted: " + quantity);
//     }
//    
//     @Override
//     public void incrementDroppedSpans(int quantity) {
//     System.out.println("dropped: " + quantity);
//     }
//    
//     });
//     }

    @Bean
    public Brave brave(SpanCollector spanCollector) {
        Brave.Builder builder = new Brave.Builder(properties.getServiceName());
        builder.spanCollector(spanCollector);
        Brave brave = builder.build();
        return brave;
    }

    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(),
                brave.serverResponseInterceptor(), new DefaultSpanNameProvider());
        return filter;
    }

    @Bean
    public OkHttpClient okHttpClient(Brave brave) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(brave.clientRequestInterceptor(),
                        brave.clientResponseInterceptor(), new DefaultSpanNameProvider()))
                .build();
        return client;
    }

    @Bean
    public MySQLStatementInterceptorManagementBean mySQLStatementInterceptorManagementBean(Brave brave) {
        return new MySQLStatementInterceptorManagementBean(brave.clientTracer());
    }

}
