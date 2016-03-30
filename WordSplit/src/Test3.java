import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


public class Test3 {
	
//	public static final String stopWordTable = "StopWordTable.txt";
	    public List<String> stopWord(String word) throws IOException {  
//	        String text="基于java语言开发的轻量级的中文分词工具包深圳的";  
	        List<String> list1 = new ArrayList<String>();
	        //			BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stopWordTable))));
//	        Set<String> stopWordSet = new HashSet<String>();  
	        //初如化停用词集  
//	        
	        StringReader sr=new StringReader(word);  
	        IKSegmenter ik=new IKSegmenter(sr, true);  
	        Lexeme lex=null;  
	        while((lex=ik.next())!=null){ 
	        	String str = lex.getLexemeText();
//	        	if(stopWordSet.contains(str)){
//	        		continue;
//	        	}
//	            System.out.print(str+" ");
	            list1.add(str);
	        }  
	        return list1;
	    }  
		 public static void main(String[] args) throws Exception {
			 List<String> res = new Test3().stopWord("为了评估环氧丙烷蒸气-铝粉-空气杂混合物的爆炸危险性,在5L圆柱形爆炸装置中分别对铝粉、环氧丙烷蒸气及铝粉与环氧丙烷蒸气共存条件下的杂混合物进行了爆炸浓度下限的实验研究。结果表明:环氧丙烷蒸气可使杂混合物的爆炸下限浓度降低;杂混合物的最大爆炸压力上升速率由于环氧丙烷蒸气的存在而增强;当铝粉浓度较低时,环氧丙烷蒸气的加入使最大爆炸压力明显增加,之后随着铝粉浓度的增加,最大爆炸压力反而减小。");
			 System.out.println(res);
		 }
	}
	  
