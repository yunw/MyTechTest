package com.test.example.json.jackson.serializer;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.htrace.fasterxml.jackson.core.JsonGenerator;
import org.apache.htrace.fasterxml.jackson.core.JsonProcessingException;
import org.apache.htrace.fasterxml.jackson.databind.JsonSerializer;
import org.apache.htrace.fasterxml.jackson.databind.SerializerProvider;

public class CustomDoubleSerializer extends JsonSerializer<Double> {

	private DecimalFormat df = new DecimalFormat("##.00");

	@Override
	public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		String s = df.format(value);
		for (;;) {
			if (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			} else if (s.endsWith(".")) {
				s = s.substring(0, s.length() - 1);
				break;
			} else {
				break;
			}
				
		}

		jgen.writeString(s);
	}
}
