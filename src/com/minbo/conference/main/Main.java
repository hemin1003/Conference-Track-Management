package com.minbo.conference.main;

import java.util.List;

import com.minbo.conference.helper.Controller;
import com.minbo.conference.helper.DataFileReader;
import com.minbo.conference.service.vo.Track;

/**
 * 执行入口
 * @author Minbo
 */
public class Main {

	public static void main(String[] args) {
		try {
			//1. 获得文件内容
			List<String> input = DataFileReader.readFile();
			
			//2. 分析
			List<Track> output = Controller.process(input);
			
			Main m = new Main();
			m.output(input);
			System.out.println();
			System.out.println("============================");
			//3. 输出结果
			m.result(output);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void output(List<String> input){
		System.out.println("\nInput : \n");
		for (String s : input) {
			System.out.println(s);
		}
	}
	
	public void result(List<Track> output) {
		System.out.println("\nOutput : \n");
		for (Track t : output) {
			System.out.println("Track : " + t.getID() + "\n");
			//计算并输出
			t.output();
		}
	}
}
