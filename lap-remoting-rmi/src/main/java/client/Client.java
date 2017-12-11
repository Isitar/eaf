package client;

import java.rmi.Naming;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.support.RemoteInvocation;

import bank.Account;
import bank.AccountService;

@SpringBootApplication
public class Client {

	@Value("${rmi.host}")
	String host;

	@Value("${rmi.port}")
	int port;

	@Value("${serviceName}")
	String serviceName;

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

	@Bean
	CommandLineRunner run(AccountService service) {
		service.insertAccount(new Account("Pascal"));
		service.insertAccount(new Account("Pascal"));
		service.insertAccount(new Account("Pascal"));
		service.insertAccount(new Account("Pascal"));
		System.out.println("Pascal Accounts (should be n*4): " + service.getAccounts("Pascal").size());

		return args -> {
			System.out.println("Client started");
			Object proxy = Naming.lookup("rmi://localhost:1099/AccountService");

			org.springframework.remoting.rmi.RmiInvocationHandler rmiHandler = (org.springframework.remoting.rmi.RmiInvocationHandler) proxy;

			RemoteInvocation inv = new RemoteInvocation();
			inv.setMethodName("getAccounts");
			inv.setParameterTypes(new Class[] { String.class });
			inv.setArguments(new Object[] { "Pascal" });
			proxy = rmiHandler.invoke(inv);
			java.util.List<Account> accs = (List<Account>) proxy;
			for (Account account : accs) {
				System.out.println(account.getName());
			}

			// Object proxy = service;
			System.out.println("proxy.getClass() = " + proxy.getClass().getCanonicalName());
			System.out.println("implemented interfaces:");
			for (Class<?> c : proxy.getClass().getInterfaces()) {
				System.out.println("> " + c.getCanonicalName());
			}
		};
	}

	@Bean
	AccountService getProxy() {
		RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
		String url = String.format("rmi://%s:%s/%s", host, port, serviceName);
		proxy.setServiceUrl(url);
		proxy.setServiceInterface(AccountService.class);
		proxy.afterPropertiesSet();
		return (AccountService) proxy.getObject();
	}

}
