package com.dpfinder.app.services;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.dpfinder.app.mappers.Contact;
import com.dpfinder.app.mappers.Match;

public class ContactMatcher {
	private final Map<String, Integer> fieldWeights;
	private static final int HIGH_SCORE = 4;
	private static final int LOW_SCORE = 1;

	// Creamos un mapa que asocia cada campo con un peso
	public ContactMatcher() {
		fieldWeights = new HashMap<>();
		fieldWeights.put("email", 2);
		fieldWeights.put("firstName", 1);
		fieldWeights.put("lastName", 1);
		fieldWeights.put("zipCode", 0);
		fieldWeights.put("address", 1);
	}

	// Recorremos la estructura recibida con un stream
	public List<Match> findMatches(List<Contact> contacts) {
        return IntStream.range(0, contacts.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, contacts.size())
                        .mapToObj(j -> {
                            Contact c1 = contacts.get(i);
                            Contact c2 = contacts.get(j);
                            String accuracy = evaluateMatch(c1, c2);
                            return accuracy.equals("None") ? null : new Match(c1.getContactID(), c2.getContactID(), accuracy);
                        })
                        .filter(match -> match != null)
                )
                .collect(Collectors.toList());
    }

	private String evaluateMatch(Contact c1, Contact c2) {
		int score = 0;

		// Usamos reflexión para obtener el valor de cada campo en tiempo de ejecución
		// Esto se hace para adaptarse a cualquier cambio que ocurra en la clase Contact
		
		// Recorremos cada campo usando el Map, comparamos cada campo de los contactos entre sí
		// y si coincide, sumamos el peso al total
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

		
		// Definimos un sistema de puntaje de acuerdo al peso, esto se puede configurar según lo deseado
		if (score >= HIGH_SCORE) {
			return "High";
		} else if (score >= LOW_SCORE) {
			return "Low";
		} else {
			return "None";
		}
	}
}
