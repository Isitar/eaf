package client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.util.StopWatch;

import bank.Account;
import bank.AccountService;

@SpringBootApplication
public class Client {

	@Value("${web.host}")
	String host;

	@Value("${web.port}")
	int port;

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

	@Bean
	CommandLineRunner run(AccountService service) {
		return args -> {
			StopWatch sw = new StopWatch();
			sw.start();
			for (int i = 0; i < 1000; ++i) {
				service.insertAccount(new Account("Mueller"));
				List<Account> accounts = service.getAccounts("Mueller");
			}
			sw.stop();
			System.out.println("elapsed ms: " + sw.getLastTaskTimeMillis());
			// System.out.println(accounts.size() + " accounts returned");
		};
	}

	@Bean
	AccountService getHttpInvokerProxy() {
		HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
		String url = String.format("http://%s:%s/lab-remoting/HttpInvoke", host, port);
		proxy.setServiceUrl(url);
		proxy.setServiceInterface(AccountService.class);
		proxy.afterPropertiesSet();
		return (AccountService) proxy.getObject();
	}

	//@Bean
	AccountService getHessianProxy() {
		HessianProxyFactoryBean proxy = new HessianProxyFactoryBean();
		String url = String.format("http://%s:%s/lab-remoting/Hessian", host, port);
		proxy.setServiceUrl(url);
		proxy.setServiceInterface(AccountService.class);
		proxy.afterPropertiesSet();
		return (AccountService) proxy.getObject();
	}

}
