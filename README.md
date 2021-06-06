README for JAX-WS Demo Project
==============================

This project allows for experimentations with JAX-WS within a JBoss EAP 6.4.23 application server.

Apache configuration:
```
<Location "/jaxws-demo">
    ProxyPass ajp://localhost:8009/jaxws-demo
    ProxyPassReverse ajp://localhost:8009/jaxws-demo
</Location>
```