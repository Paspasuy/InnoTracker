package org.innotrackers.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.innotrackers.demo.utils.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Objects;


@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	@Autowired
	private DatabaseSeeder databaseSeeder;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}
	@Override
	public void run(ApplicationArguments appArgs) throws Exception {
		String[] args = appArgs.getSourceArgs();
		if (args.length == 0) {
			System.out.println("Please provide component to launch");
			return;
		}
		String command = args[0].toLowerCase();
		switch (command) {
			case "web":
//				SpringApplication.run(DemoApplication.class, args);
				break;
			case "populate":
				databaseSeeder.populateDatabase();
				System.exit(0);
				break;
			case "depopulate":
				databaseSeeder.cleanDatabase();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid argument.");
		}
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			HikariDataSource ds = (HikariDataSource) ctx.getBean("dataSource");

			System.out.println(ds.getHikariConfigMXBean());
			System.out.println(ds.getDataSourceClassName());
			System.out.println(ds.getDataSourceProperties());
			System.out.println(ds.getHealthCheckProperties());
//			System.out.println(ds.getConnection());

		};
	}


}
