import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


public class CopyOfTest3 {
	
//	public static final String stopWordTable = "StopWordTable.txt";
	    public List<String> stopWord(String word) throws IOException {  
//	        String text="基于java语言开发的轻量级的中文分词工具包深圳的";  
	        List<String> list1 = new ArrayList<String>();
//			BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stopWordTable))));
//	        Set<String> stopWordSet = new HashSet<String>();  
//	        //初如化停用词集  
//	        String stopWord = null;  
//	        for(; (stopWord = StopWordFileBr.readLine()) != null;){  
//	            stopWordSet.add(stopWord);  
//	        } 
	        StringReader sr=new StringReader(word);  
	        IKSegmenter ik=new IKSegmenter(sr, true);  
	        Lexeme lex=null;  
	        int pos = 0;
//	        String wordname = null;
//	        inital();
	        while((lex=ik.next())!=null){ 
	        	String str = lex.getLexemeText();
	        	pos = lex.getBeginPosition();
//	        	if(stopWordSet.contains(str)){
//	        		continue;
//	        	}
////	            System.out.print(str+" ");
	            list1.add(str);
	        }  
	        return list1;
	    }  
		 public static void main(String[] args) throws Exception {
			 List<String> res = new CopyOfTest3().stopWord("钢铁是怎么炼成的");
			 System.out.println(res);
		 }
	}
	  
