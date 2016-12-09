package com.minbo.conference.properties;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minbo.conference.properties.vo.ConferenceConfigData;

/**
 * 资源文件处理
 * @author Minbo
 */
public class Configurator {

	// 资源文件读取
	private static String propfilename = "res/conference.properties.json";
	private final static ConferenceConfigData data;

	static {
		Configurator sc = new Configurator();
		data = (ConferenceConfigData) sc.getPropertiesFromFile(propfilename, new TypeReference<ConferenceConfigData>() {
		});
	}

	public static ConferenceConfigData getConferenceConfigData() {
		return data;
	}

	static String readFile(final String path, final Charset encoding) throws IOException {
		final byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	private <T> Object getPropertiesFromFile(String filename, TypeReference<T> t) {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try {
			obj = mapper.readValue(readFile(filename, StandardCharsets.UTF_8), t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}