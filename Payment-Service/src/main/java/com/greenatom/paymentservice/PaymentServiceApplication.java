package com.greenatom.PaymentService;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.IOException;

@SpringBootApplication
@EnableKafka
public class PaymentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Bean
	public JsonDeserializer jsonDeserializer() {
		return new JsonDeserializer() {
			@Override
			public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
				return null;
			}
		};
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
