package com.dpfinder.app.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dpfinder.app.mappers.Contact;
import com.dpfinder.app.mappers.Match;

public class ContactMatcher {
	private final Map<String, Integer> fieldWeights;

	public ContactMatcher() {
		fieldWeights = new HashMap<>();
		fieldWeights.put("email", 2);
		fieldWeights.put("firstName", 1);
		fieldWeights.put("lastName", 1);
		fieldWeights.put("zipCode", 0);
		fieldWeights.put("address", 1);
	}

	public List<Match> findMatches(List<Contact> contacts) {
		List<Match> matches = new ArrayList<>();

		for (int i = 0; i < contacts.size(); i++) {
			for (int j = i + 1; j < contacts.size(); j++) {
				Contact c1 = contacts.get(i);
				Contact c2 = contacts.get(j);
				String accuracy = evaluateMatch(c1, c2);

				if (!accuracy.equals("None")) {
					matches.add(new Match(c1.getContactID(), c2.getContactID(), accuracy));
				}
			}
		}
		return matches;
	}

	private String evaluateMatch(Contact c1, Contact c2) {
		int score = 0;

		for (Map.Entry<String, Integer> entry : fieldWeights.entrySet()) {
			String fieldName = entry.getKey();
			int weight = entry.getValue();

			try {
				Field field = Contact.class.getDeclaredField(fieldName);
				field.setAccessible(true);

				Object value1 = field.get(c1);
				Object value2 = field.get(c2);

				if (value1 != null && value1.equals(value2)) {
					score += weight;
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (score >= 4) {
			return "High";
		} else if (score >= 1) {
			return "Low";
		} else {
			return "None";
		}
	}
}
