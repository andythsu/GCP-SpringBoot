package com.example.SpringBoot.AuthREST;

import org.github.andythsu.GCP.Services.Datastore.DatastoreData;
import org.github.andythsu.GCP.Services.Datastore.DatastoreService;
import org.github.andythsu.GCP.Services.Email.Mail;
import org.github.andythsu.GCP.Services.Email.MailContent;
import org.github.andythsu.GCP.Services.Token.AuthToken;
import org.github.andythsu.GCP.Services.Token.TokenUtil;
import com.example.SpringBoot.DatastoreColumns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    private Logger log = LoggerFactory.getLogger(AuthController.class);
    public static String AUTH_KIND = "auth";
    public static String TOKEN_COL = "Token";

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
    public void getToken(){
        AuthToken token = tokenUtil.acqureToken();
        // save to db
        DatastoreData dd = new DatastoreData();
        dd.put(DatastoreColumns.CREATEDAT, token.getCreatedAt());
        dd.put(DatastoreColumns.EXPIREDAT, token.getExpiredAt());
        dd.put(TOKEN_COL, token.getToken());
        DatastoreService.saveByKind(AUTH_KIND, dd, null);
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
