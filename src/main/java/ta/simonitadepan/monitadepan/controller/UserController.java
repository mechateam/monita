package ta.simonitadepan.monitadepan.controller;


import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.FaskesModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.service.FaskesService;
import ta.simonitadepan.monitadepan.service.ServerProperties;
import ta.simonitadepan.monitadepan.service.UserService;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    FaskesService faskesService;

    @Autowired
    UserService userService;

    @Autowired
    ServerProperties serverProperties;

    @RequestMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("listFaskes",faskesService.getAllFaskes());
        model.addAttribute("listKelurahan",serverProperties.getKelurahan());

        return "page-register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @ModelAttribute UserModel user,
            Model model,
            RedirectAttributes redirect
    ){

        if(userService.addUser(user) == null){
            redirect.addFlashAttribute("message","Username/Email/Nomor Telepon yang ingin dibuat sudah terdaftar. Pilih username/email/nomor telepon lain.");
            return "redirect:/user/register";
        }

        redirect.addFlashAttribute("msg","Akun anda sudah dibuat, silahkan login");
        return "redirect:/login";

    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("masuk_first",true);
        return "page-forget-password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        System.out.println("ini email "+email);
        String token = RandomString.make(30);

        try {
            if (userService.updateResetPasswordToken(token, email) == null){
                throw new UsernameNotFoundException("Email tidak ditemukan");
            }
            String resetPasswordLink = getSiteURL(request) + "/user/reset_password?token=" + token;
            sendmail(email,resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "page-forget-password";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model, RedirectAttributes redir) {
        UserModel user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            redir.addFlashAttribute("message", "Invalid Token");
            return "redirect:/login";
        }

        return "page-reset-password";

    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model,  RedirectAttributes redir) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        System.out.println(token);

        UserModel user = userService.getByResetPasswordToken(token);
        if (user == null){
            user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        model.addAttribute("title", "Reset your password");

        if (user == null) {
            redir.addFlashAttribute("message", "Invalid Token");
            return "redirect:/login";
        } else {
            userService.updatePassword(user, password);
            redir.addFlashAttribute("message", "You have successfully changed your password.");
            return "redirect:/login";
        }

    }

    @GetMapping("/ganti_password")
    public String showResetPasswordForm( Model model) {
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user == null) {
            model.addAttribute("message", "User tidak ditemukan");
            return "login";
        }

        return "page-reset-password";
    }


    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public void sendmail(String email, String resetPasswordLink) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistem.monita@gmail.com", "Simonita123$");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("sistem.monita@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Reset Password SIMONITA");
        msg.setContent("Reset Password SIMONITA", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Klik link ini untuk mengganti password: \n\n"+ resetPasswordLink, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        System.out.println("ini body part "+ messageBodyPart.getContent().toString());

        msg.setContent(multipart);
        Transport.send(msg);
    }

    @ResponseBody
    @GetMapping("loadfaskesbykelurahan/{kelurahan}")
    public List<FaskesModel> loadfaskesbykelurahan(@PathVariable String kelurahan){
        return faskesService.findByKelurahan(kelurahan);
    }
}
