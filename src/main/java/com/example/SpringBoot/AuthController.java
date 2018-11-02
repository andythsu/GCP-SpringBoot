package com.example.SpringBoot;

import org.github.andythsu.GCP.Services.DatastoreService;
import org.github.andythsu.GCP.Services.Email.Mail;
import org.github.andythsu.GCP.Services.Email.MailContent;
import org.github.andythsu.GCP.Services.Token.AuthToken;
import org.github.andythsu.GCP.Services.Token.TokenUtil;
import org.github.andythsu.GCP.Services.UtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private Logger log = LoggerFactory.getLogger(AuthController.class);
    public static String AUTH_KIND = "auth";

    @RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
    public void getToken(){
        String token_col = "Token";
        AuthToken token = TokenUtil.acqureToken();

        // save to db
        Map<String, Object> data = new HashMap<>();
        data.put(UtilService.commonNames.CREATEDAT, token.getCreatedAt());
        data.put(UtilService.commonNames.EXPIREDAT, token.getExpiredAt());
        data.put(token_col, token.getToken());
        DatastoreService.saveByKind(AUTH_KIND, data);
        // send email to user
        String body = new StringBuilder()
                .append("Token: ")
                .append(token.getToken())
                .append("\n")
                .append("Expiry Date: ")
                .append(token.getExpiredAtTime())
                .toString();
        MailContent mailContent = new MailContent().subject("Token").body(body);
        Mail.sendEmail(mailContent);
    }

}
