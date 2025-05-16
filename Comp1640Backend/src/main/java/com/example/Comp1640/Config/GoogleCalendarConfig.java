package com.example.Comp1640.Config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.*;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.*;
import com.google.api.services.calendar.*;
import com.google.api.services.calendar.Calendar;

import java.io.*;
import java.util.*;


public class GoogleCalendarConfig {
    private static final String APPLICATION_NAME = "YourApp";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    public static Credential authorize() throws Exception {
        InputStream in = new FileInputStream("credentials.json"); // client ID/secret từ Google Console
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens"))) // nơi lưu token sau login
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8081)
                .setCallbackPath("/oauth2callback")
                .build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    }

    public static Calendar getCalendarService() throws Exception {
        Credential credential = authorize();
        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
