package nbu.file.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CiToXueKe {
	public static HashMap<String, List<String>> CI_XUEKE_MAP=new HashMap<String, List<String>>();
	
	public HashMap<String, List<String>> ciXueKeMap(String path) throws IOException{
		List<String> list=new Txt().readTxt(path);
		List<String> cilist=new ArrayList<String>();
		for(String s:list){
			cilist.add(s.split("\t")[0]);
		}
		
		List<String> cilist1=new CiToXueKe().removeDuplicateWithOrder(cilist);
		System.out.println(cilist.size());
		System.out.println(cilist1.size());
		for(String ci:cilist1){
			List<String> list2=new ArrayList<String>();
			for(String s:list){
				if(s.split("\t")[0].equals(ci)){
					list2.add(s.split("\t")[1]);
				}
			}
			CI_XUEKE_MAP.put(ci, list2);
		}
		
		return CI_XUEKE_MAP;
		
	}
	
	public static List removeDuplicateWithOrder(List list) {
	      Set set = new HashSet();
	      List newList = new ArrayList();
	      for (Iterator iter = list.iterator(); iter.hasNext();) {
	           Object element = iter.next();
	            if (set.add(element))
	                newList.add(element);
		        }
		        return newList;
		    }
	
	public static void main(String[] args) throws IOException{
		new CiToXueKe().ciXueKeMap("xueke_name_dic.txt");
	}
}
