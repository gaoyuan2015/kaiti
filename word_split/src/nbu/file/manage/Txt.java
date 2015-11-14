package nbu.file.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Txt {
	//读取txt文件
	public List<String> readTxt(String path) throws IOException{
		List<String> list=new ArrayList<String>();
		
		File file=new File(path); 
		BufferedReader rd=new BufferedReader(new FileReader(file));
		
		String s=null;
		while((s=rd.readLine())!=null){
			if(s.equals("")){
				
			}else{
				list.add(s);
			}
		}
		return list;
	}
	//写入txt文件
	public void writeTxt(String content,String path) throws IOException{
		OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(path, true), "utf-8");
        outs.write(content);
        outs.close();
	}
}
