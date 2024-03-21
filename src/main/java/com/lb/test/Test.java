package com.lb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.URL;
public class Test {

	public static void main(String[] args) {
		
		String a = "593;409001;419001;120000004593";
		System.out.println(a.indexOf("120000004593"));
		List<Map> regList = new ArrayList();
		
		for(int i = 0;i<2;i++) {
			Map map = new HashMap();
			if(i==0) {
				map.put("AmLockId", 1);
		        map.put("AcctId", 1);
			}else {
				map.put("AmLockId", 1);
		        map.put("AcctId", 2);
			}

	        regList.add(map);
		}
		System.out.println(regList);
        
        
        
		Parm parm = new Parm();
		parm.setA(1);
		parm.setB(2);
		Integer sum = sum(parm);
		System.out.println(sum);

		String apiKey = "bed05f065beed344046407b73c4855c7";
		String city = "Beijing";
		String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			System.out.println(response.toString());
			// Parse the JSON response and extract the weather information
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Integer sum(Parm p) {
		Integer sum = 0;
		sum = p.getA() + p.getB();
		return sum;
	}
}
