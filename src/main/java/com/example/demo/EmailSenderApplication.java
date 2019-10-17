package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootApplication
public class EmailSenderApplication implements CommandLineRunner {


    @Autowired
    private JavaMailSender javaMailSender;

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("Wysyłam mail'a...");


        try {
            //sendEmail();
            sendEmailWithAttachment();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Gotowe!");

    }


    void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("Adres-email");

        msg.setSubject("Temat");
        msg.setText("Tresc mail'a");
        javaMailSender.send(msg);

    }
    void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        //true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("Adres-email");

        helper.setSubject("Temat mail'a");

        // default = text/plain
        helper.setText("Wiadomosc");

        // true = text/html
        helper.setText("<h1>Sprawdz zalacznik!</h1>", true);

        helper.addAttachment("zdjecie.jpg", new ClassPathResource("test.jpg"));

        javaMailSender.send(msg);

    }
}