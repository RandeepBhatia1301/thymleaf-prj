package org.ril.hrss.service.org_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

@RestController
public class EmailServiceDemo {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.senderemail}")
    private String senderEmail;

    @Value("${spring.appurl}")
    private String appUrl;

    @RequestMapping("/sendMail")
    public Boolean sendMail(String email, String firstName, String password) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        email = "anshika.mittal@webdunia.net";
        firstName = "anshika";
        password = "admin@123";
        try {
            helper.setTo(email);
            helper.setFrom(senderEmail);
            helper.setText("this email is test for control panel");
            helper.setText("Hi " + firstName + ",\n" +
                    "\n" +
                    "Thank you for choosing Communities. \n" +
                    "\n" +
                    "Here are your account credentials: \n" +
                    "\n" +
                    "Admin Login Url : " + appUrl + " \n" +
                    "Username :" + email + "\n" +
                    "Password : " + password + " \n" +
                    "\n" +
                    "Please follow the instructions below to complete integration of your account. PLEASE ADD ANY POINTS WHICH FIND IS RELEVANT\n" +
                    "\n" +
                    "Please contact our support team in case you need any assitance. \n" +
                    "\n" +
                    "\n" +
                    "Regards,\n" +
                    "Communities Team");
            helper.setSubject("Welcome to communities");
            sender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

