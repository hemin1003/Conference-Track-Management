package com.minbo.conference.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.minbo.conference.properties.Configurator;

public class DataFileReader {

	/**
	 * 取出文件内容
	 * @return
	 */
	public static List<String> readFile() {
		String filename = Configurator.getConferenceConfigData().getInputfilepath();
		List<String> inputList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				inputList.add(strLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputList;
	}
	
}
