package com.parkingapp.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class DbConfig {

    // https://campus.cs.le.ac.uk/labsupport/usinglinux/mysqlaccountdetails
	private String USERNAME = "am984";
	private String PASSWORD = "miP3eiph"; // in ~/.my.cnf
	
//	private String HOST = "mysql.mcscw3.le.ac.uk";
//	private int PORT = 3306;
	
//  ssh -fNg -L 3307:mysql.mcscw3.le.ac.uk:3306 am984@xanthus.mcscw3.le.ac.uk
	private String HOST = "127.0.0.1";
	private int PORT = 3307;
			
    @Bean
    public DriverManagerDataSource dataSource() {		
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // jdbc:mysql://host:port/db
        ds.setUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + USERNAME); // + "?autoReconnect=true&useSSL=false";
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }
}
