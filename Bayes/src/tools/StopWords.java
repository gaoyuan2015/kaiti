package tools;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class StopWords {

	public List<String> stopWord(String word) throws IOException {
		List<String> list1 = new ArrayList<String>();
		StringReader sr = new StringReader(word);
		IKSegmenter ik = new IKSegmenter(sr, true);
		Lexeme lex = null;
		while ((lex = ik.next()) != null) {
			String str = lex.getLexemeText();
			list1.add(str);
		}
		return list1;
	}

	public static void main(String[] args) throws Exception {
		List<String> res = new StopWords()
				.stopWord("为了评估环氧丙烷蒸气X射线衍射-铝粉-空气杂混合物的爆炸危险性RNA病毒,在5L圆柱形爆炸装置中分别对铝粉、《三国演义》环氧丙烷蒸气及铝粉与环氧丙烷蒸气共存条件下的杂混合物进行了爆炸浓度下限的实验研究。结果表明:环氧丙烷蒸气可使杂混合物的爆炸下限浓度降低;杂混合物的最大爆炸压力上升速率由于环氧丙烷蒸气的存在而增强;当铝粉浓度较低时,环氧丙烷蒸气的加入使最大爆炸压力明显增加,之后随着铝粉浓度的增加,最大爆炸压力反而减小。");
		System.out.println(res);
	}
}
