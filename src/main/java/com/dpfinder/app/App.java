package com.dpfinder.app;

import java.util.ArrayList;
import java.util.List;

import com.dpfinder.app.mappers.Contact;
import com.dpfinder.app.mappers.Match;
import com.dpfinder.app.services.ContactMatcher;

public class App {
    public static void main(String[] args) {
		List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1001, "C", "F", "mollis.lectus.pede@outlook.net", "", "449-6990 Tellus. Rd."));
        contacts.add(new Contact(1002, "C", "French", "mollis.lectus.pede@outlook.net", "39746", "449-6990 Tellus. Rd."));
        contacts.add(new Contact(1003, "Ciara", "F", "non.lacinia.at@zoho.ca", "39746", ""));

        ContactMatcher matcher = new ContactMatcher();
        List<Match> matches = matcher.findMatches(contacts);

        for (Match match : matches) {
            System.out.println(match);
        }
    }
}
