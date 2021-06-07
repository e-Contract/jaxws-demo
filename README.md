# README for JAX-WS Demo Project

This project allows for experimentations with JAX-WS within a JBoss EAP 7.3 or WildFly 23 application server.

## Apache Configuration:

Use the following apache configuration file:
```
<Location "/jaxws-demo">
    ProxyPass http://localhost:8080/jaxws-demo
    ProxyPassReverse http://localhost:8080/jaxws-demo
	RequestHeader set X-Forwarded-Proto "https" 
    ProxyPreserveHost On
</Location>
```

## JBoss Configuration

Use the following logging configuration:
```xml
            <periodic-rotating-file-handler name="DEMO" autoflush="true">
                <level name="DEBUG"/>
                <formatter>
                    <named-formatter name="PATTERN"/>
                </formatter>
                <file relative-to="jboss.server.log.dir" path="demo.log"/>
                <suffix value=".yyyy-MM-dd"/>
                <append value="true"/>
            </periodic-rotating-file-handler>
            <logger category="be.e_contract.demo" use-parent-handlers="false">
                <level name="DEBUG"/>
                <handlers>
                    <handler name="DEMO"/>
                </handlers>
            </logger>
            <logger category="org.jboss.ws" use-parent-handlers="false">
                <level name="DEBUG"/>
                <handlers>
                    <handler name="DEMO"/>
                </handlers>
            </logger>
            <logger category="org.apache.cxf" use-parent-handlers="false">
                <level name="DEBUG"/>
                <handlers>
                    <handler name="DEMO"/>
                </handlers>
            </logger>
            <logger category="org.jboss.wsf" use-parent-handlers="false">
                <level name="DEBUG"/>
                <handlers>
                    <handler name="DEMO"/>
                </handlers>
            </logger>
```

## References

http://www.mastertheboss.com/javaee/jboss-web-services/using-spring-cxf-descriptors-in-wildfly
https://access.redhat.com/solutions/168093