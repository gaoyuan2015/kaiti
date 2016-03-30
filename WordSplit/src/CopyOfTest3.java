import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


public class CopyOfTest3 {
	
//	public static final String stopWordTable = "StopWordTable.txt";
	    public List<String> stopWord(String word) throws IOException {  
//	        String text="����java���Կ����������������ķִʹ��߰����ڵ�";  
	        List<String> list1 = new ArrayList<String>();
//			BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stopWordTable))));
//	        Set<String> stopWordSet = new HashSet<String>();  
//	        //���绯ͣ�ôʼ�  
//	        String stopWord = null;  
//	        for(; (stopWord = StopWordFileBr.readLine()) != null;){  
//	            stopWordSet.add(stopWord);  
//	        } 
	        StringReader sr=new StringReader(word);  
	        IKSegmenter ik=new IKSegmenter(sr, true);  
	        Lexeme lex=null;  
	        @SuppressWarnings("unused")
			int pos = 0;
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
			 List<String> res = new CopyOfTest3().stopWord("��������ô���ɵ�");
			 System.out.println(res);
		 }
	}
	  
