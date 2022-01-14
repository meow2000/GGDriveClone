package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.entity.PlanEntity;
import com.project.GGDriveClone.repository.PlanRepository;
import com.project.GGDriveClone.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlanRepository planRepository;




    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;




    public PlanEntity findPlan(Long pid) {
        return planRepository.findPlanEntityById(pid);
    }


    public UserEntity findUser(String username) {
        return userRepository.findUserEntityByName(username);
    }

    public UserEntity findUser(Long uid) {
        return userRepository.findUserEntityById(uid);
    }

    public List<UserEntity> getAllUser() {



        return userRepository.findAll();
    }

    public void saveUser(UserEntity user) {
        user.setCreated_time(new Timestamp(System.currentTimeMillis()));
        user.setUpdated_time(new Timestamp(System.currentTimeMillis()));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> listAll() {
        return userRepository.findAll();
    }

    public boolean checkAccountNotExists(String name, String email) {
        if (userRepository.findUserEntityByName(name) == null && userRepository.findUserEntityByEmail(email) == null) {
            return true;
        }
        return false;
    }

    public boolean checkAccountExists(String name, String email) {
        if (userRepository.findUserEntityByName(name) != null || userRepository.findUserEntityByEmail(email) != null) {
            return true;
        }
        return false;
    }


    public int register(UserEntity user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {

        if ( user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            return 0;
        }
        if (checkAccountNotExists(user.getName(), user.getEmail()) == true) {
                Long pid = 1l;
                PlanEntity plan = findPlan(pid);
                user.setPlan(plan);
                user.setStorage(0L);
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                String randomCode = RandomString.make(64);
                user.setVerificationCode(randomCode);
                user.setEnabled(false);
                user.setCreated_time(new Timestamp(System.currentTimeMillis()));
                user.setUpdated_time(new Timestamp(System.currentTimeMillis()));
                user.setRole("USER");
                user.setStorage(0l);
                userRepository.save(user);
                sendVerificationEmail(user, siteURL);
                return 1;
        }

        return 2;
    }

    private void sendVerificationEmail(UserEntity user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "testmail.hust@gmail.com\n";
        String senderName = "Group 2";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Group 2.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/api/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }

    public boolean verify(String verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }
}
