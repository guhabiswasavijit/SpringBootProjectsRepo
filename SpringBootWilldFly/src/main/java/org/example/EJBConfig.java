package org.example;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EJBConfig {

    //@Bean
    public PayTmFacadeRemote payTmFacadeNotWorking() throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put("java.naming.factory.initial","com.sun.jndi.ldap.LdapCtxFactory");
        jndiProperties.put("java.naming.security.authentication","simple");
        jndiProperties.put("java.naming.provider.url", "ldap://127.0.0.1:389");
        jndiProperties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        jndiProperties.put(Context.SECURITY_CREDENTIALS, "U3dhbTJAd2lsZGZseQ==");
        Context ctx = new InitialContext(jndiProperties);
        Object remote = ctx.lookup("ejb:/PayTmService-1.0-SNAPSHOT/PayTmTxFacade!org.example.PayTmFacadeRemote");
        PayTmFacadeRemote ejb = (PayTmFacadeRemote) PortableRemoteObject.narrow(remote,PayTmFacadeRemote.class);
        return ejb;
    }
    @Bean
    public PayTmFacadeRemote payTmFacade() throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put("java.naming.provider.url", "http-remoting://localhost:8080");
        Context ctx = new InitialContext(jndiProperties);
        Object remote = ctx.lookup("ejb:/PayTmService-1.0-SNAPSHOT/PayTmTxFacade!org.example.PayTmFacadeRemote");
        PayTmFacadeRemote ejb = (PayTmFacadeRemote) PortableRemoteObject.narrow(remote,PayTmFacadeRemote.class);
        return ejb;
    }
}
