package ru.fomin.nyakashop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.fomin.nyakashop.entity.UserEn;
import ru.fomin.nyakashop.repository.UserRepo;

@SpringBootApplication
public class NyakaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(NyakaShopApplication.class, args);
	}

}
