package com.example.SpringBoot.AuthREST;


import org.github.andythsu.GCP.Services.Datastore.DatastoreData;
import org.github.andythsu.GCP.Services.Datastore.DatastoreService;
import org.github.andythsu.GCP.Services.Email.Mail;
import org.github.andythsu.GCP.Services.Email.MailContent;
import org.github.andythsu.GCP.Services.Token.AuthToken;
import org.github.andythsu.GCP.Services.Token.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
    public void acquireToken(){
       AuthToken authToken = tokenUtil.acqureToken();
        // save to db
        DatastoreData dd = new DatastoreData();
        dd.put(DatastoreService.DatastoreColumns.CREATEDAT, authToken.getCreatedAt());
        dd.put(DatastoreService.DatastoreColumns.EXPIREDAT, authToken.getExpiredAt());
        dd.put(DatastoreService.DatastoreColumns.TOKEN, authToken.getToken());
        DatastoreService.saveByKind(DatastoreService.DatastoreKinds.AUTH, dd, null);
        // send email to user
        String body = new StringBuilder()
                .append("Token: ")
                .append(authToken.getToken())
                .append("\n")
                .append("Expiry Date: ")
                .append(authToken.getExpiredAtTime())
                .toString();
        MailContent mailContent = new MailContent().subject("Token").body(body);
        Mail.sendEmail(mailContent);
    }

}
