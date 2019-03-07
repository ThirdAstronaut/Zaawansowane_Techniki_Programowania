package com.pwr.ztp_lab;

import com.pwr.ztp_lab.models.Book;
import com.pwr.ztp_lab.models.Order;
import com.pwr.ztp_lab.repositories.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Date;

@SpringBootApplication
public class ZtpLabApplication implements CommandLineRunner {

    @Autowired
    public ZtpLabApplication(CustomRepository<Book> bookCustomRepository, CustomRepository<Order> orderCustomRepository) {
        this.bookCustomRepository = bookCustomRepository;
        this.orderCustomRepository = orderCustomRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ZtpLabApplication.class, args);
    }

    private final CustomRepository<Book> bookCustomRepository;
    private final CustomRepository<Order> orderCustomRepository;

    @Override
    public void run(String... args) throws Exception {
        Book b = new Book();
        b.setTitle("Main book");
        b.setIsbn("9999999911111");
        b.setAuthor("John Sam");
        bookCustomRepository.save(b);

        Order o = new Order();
        o.setName("Main Customer");
        o.setOrderDate(new Date());
        o.setBook(b);

        orderCustomRepository.save(o);


        String filename = "Objects.txt";
        try{
           /* FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(b);
            out.writeObject(o);

            out.close();
            file.close();*/

            File file = new File("JavaBook1.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(b.toString());
            bw.write(o.toString());
            bw.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
