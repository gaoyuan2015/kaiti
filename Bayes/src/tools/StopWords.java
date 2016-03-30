package tools;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class StopWords {

	public List<String> stopWord(String word) throws IOException {
		List<String> list1 = new ArrayList<String>();
		String s = word.replaceAll("[a-z0-9]", "");  
		StringReader sr = new StringReader(s);
		IKSegmenter ik = new IKSegmenter(sr, true);
		Lexeme lex = null;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]{2,}");
		while ((lex = ik.next()) != null) {
			String str = lex.getLexemeText();
			Matcher m = p.matcher(str);
			if(m.find()){
			list1.add(str);
			}
		}
		return list1;
	}

//	public static void main(String[] args) throws Exception {
//		List<String> res = new StopWords()
//				.stopWord("哈哈fsdf15Q热立克次氏体");
//		System.out.println(res);
//	}
}
