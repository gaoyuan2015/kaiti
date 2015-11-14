package nbu.file.manage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ZtfToXueKe {
	public static String PATH="./";
	public static HashMap<String, xueke_object> ZTFTOXUEKEMAP=new HashMap<String, xueke_object>();
	
	public xueke_object ztftoxueke(String ztf){
		xueke_object xo=null;
		
		for(int i=ztf.length();i>0;i--){
			if(ZtfToXueKe.ZTFTOXUEKEMAP.containsKey(ztf.substring(0, i))){
				//System.out.println(ztf.substring(0, i));
				xo=ZtfToXueKe.ZTFTOXUEKEMAP.get(ztf.substring(0, i));
				//System.out.println(xo.getXuekedaima());
				break;
			}
		}
		
		return xo;
	}
	
	public HashMap<String, xueke_object> getztftoxueke_map(String filename) throws IOException{
		List<String> list=new Txt().readTxt(PATH+filename);
		
		for(String s:list){
			xueke_object xo=new xueke_object();
			xo.setXueke_name(s.split("\t")[2]);
			xo.setXuekedaima(s.split("\t")[1]);
			ZTFTOXUEKEMAP.put(s.split("\t")[0], xo);
		}
		return ZTFTOXUEKEMAP;
	}
	
}
