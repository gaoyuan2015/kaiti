package test;

import java.io.IOException;
import java.util.List;

import tools.StopWords;

public class tt {
public static void main(String[] args) throws IOException {
	StopWords sw = new StopWords();
	List<String> list = sw.stopWord("为了评估环氧丙烷蒸气-铝粉-空气杂混合物的爆炸危险性,在5L圆柱形爆炸装置中分别对铝粉、《三国演义》环氧丙烷蒸气及铝粉与环氧丙烷蒸气共存条件下的杂混合物进行了爆炸浓度下限的实验研究。结果表明:环氧丙烷蒸气可使杂混合物的爆炸下限浓度降低;杂混合物的最大爆炸压力上升速率由于环氧丙烷蒸气的存在而增强;当铝粉浓度较低时,环氧丙烷蒸气的加入使最大爆炸压力明显增加,之后随着铝粉浓度的增加,最大爆炸压力反而减小。");
	System.out.println(list);
}
}
