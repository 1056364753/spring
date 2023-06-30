package com.lb.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	}

	public static Integer sum(Parm p) {
		Integer sum = 0;
		sum = p.getA() + p.getB();
		return sum;
	}
}
