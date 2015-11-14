package nbu.file.manage;

import java.io.IOException;
import java.util.HashMap;

public class XueKeToName {
	public static HashMap<String, String> XUEKE_DAIMATONAME=new HashMap<String, String>();
	
	public HashMap<String, String> xueketoname(String path) throws IOException{
		HashMap<String, xueke_object> map=new ZtfToXueKe().getztftoxueke_map(path);
		
		for(String s:map.keySet()){
			XUEKE_DAIMATONAME.put(map.get(s).getXuekedaima(), map.get(s).getXueke_name());
		}
		
		return XUEKE_DAIMATONAME;
	}
}
