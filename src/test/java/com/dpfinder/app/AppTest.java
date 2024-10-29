package com.dpfinder.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dpfinder.app.mappers.Contact;
import com.dpfinder.app.mappers.Match;
import com.dpfinder.app.services.ContactMatcher;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private ContactMatcher matcher;
    private List<Contact> contacts;

    @BeforeEach
    void setUp() {
        matcher = new ContactMatcher();
        contacts = new ArrayList<>();

        contacts.add(new Contact(1001, "C", "F", "mollis.lectus.pede@outlook.net", "", "449-6990 Tellus. Rd."));
        contacts.add(new Contact(1002, "C", "French", "mollis.lectus.pede@outlook.net", "39746", "449-6990 Tellus. Rd."));
        contacts.add(new Contact(1003, "Ciara", "F", "non.lacinia.at@zoho.ca", "39746", ""));
        contacts.add(new Contact(1004, "C", "F", "mollis.lectus.pede@outlook.net", "39746", "449-6990 Tellus. Rd."));
    }

    @Test
    void testHighAccuracyMatch() {
        List<Match> matches = matcher.findMatches(contacts);

        // Verificamos que exista una coincidencia de alta precisión
        Match highAccuracyMatch = matches.stream()
                .filter(match -> match.getAccuracy().equals("High"))
                .findFirst()
                .orElse(null);

        assertEquals(1001, highAccuracyMatch.getSourceID());
        assertEquals(1002, highAccuracyMatch.getMatchID());
        assertEquals("High", highAccuracyMatch.getAccuracy());
    }

    @Test
    void testLowAccuracyMatch() {
        List<Match> matches = matcher.findMatches(contacts);

        // Verificamos que exista una coincidencia de baja precisión
        Match lowAccuracyMatch = matches.stream()
                .filter(match -> match.getAccuracy().equals("Low"))
                .findFirst()
                .orElse(null);

        assertEquals(1001, lowAccuracyMatch.getSourceID());
        assertEquals(1003, lowAccuracyMatch.getMatchID());
        assertEquals("Low", lowAccuracyMatch.getAccuracy());
    }

    @Test
    void testNoMatch() {
        List<Contact> noMatchContacts = new ArrayList<>();
        noMatchContacts.add(new Contact(2001, "John", "Doe", "john.doe@example.com", "12345", "123 Main St."));
        noMatchContacts.add(new Contact(2002, "Jane", "Smith", "jane.smith@example.com", "67890", "456 Elm St."));

        List<Match> matches = matcher.findMatches(noMatchContacts);

        // Verificamos que no existan coincidencias
        assertEquals(0, matches.size());
    }

    @Test
    void testMultipleHighAccuracyMatches() {
        contacts.add(new Contact(1005, "C", "F", "mollis.lectus.pede@outlook.net", "39746", "449-6990 Tellus. Rd."));
        
        List<Match> matches = matcher.findMatches(contacts);
        
        // Verificamos que existan múltiples coincidencias de alta precisión
        long highAccuracyCount = matches.stream()
                .filter(match -> match.getAccuracy().equals("High"))
                .count();
                
        assertEquals(3, highAccuracyCount); // debería haber tres coincidencias de alta precisión
    }
}
