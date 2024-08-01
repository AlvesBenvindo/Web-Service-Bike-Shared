package ao.uan.fc.cc4.bikeshared.Station2.config.web;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {


    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext contexto) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(contexto);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/wsStation/*");
    }

    @Bean(name = "station2")
    DefaultWsdl11Definition userWsdlDefinition ( XsdSchema  xmlSchema ) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();

        wsdl11def.setPortTypeName("/apiSoapHttpBikeSharedStation");
        wsdl11def.setLocationUri("/wsStation");
        wsdl11def.setTargetNamespace("http://soap.xml");
        wsdl11def.setSchema(xmlSchema);

        return wsdl11def;
    }

    @Bean
    XsdSchema xmlSchema () {
        return new SimpleXsdSchema(new ClassPathResource("xsd/station_schema.xsd") );
    }

}