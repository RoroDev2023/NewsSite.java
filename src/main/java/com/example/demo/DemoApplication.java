package com.example.demo;
import java.util.List;
import com.example.demo.entity.News;
import com.example.demo.newsdao.newsDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	private void createMultipleNews(newsDAO newsDAO) {
		System.out.println("Creating multiple news at once to showcase the auto-increment feature");

		News tempnews1 = new News("BBC");
		News tempnews2 = new News("CNN News");
		News tempnews3 = new News("Forbes");

		System.out.println("Saving news into database");
		newsDAO.saveNews(tempnews1);
		newsDAO.saveNews(tempnews2);
		newsDAO.saveNews(tempnews3);

	}

	private void findAllNews(newsDAO newsDAO) {
		List<News> news = newsDAO.findAllNews();
		for (News tempnews : news) {
			System.out.println(tempnews);
		}
	}
}
