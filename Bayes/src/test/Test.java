package test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import chouqukey.ChouquKey;
import nbu.lib.dao.ClassifyResult;
import nbu.lib.dao.Pro1;

public class Test {
@SuppressWarnings({ "unchecked", "rawtypes" })
public static String fenlei(List<String> wordList) throws Exception{
//	StopWords sw = new StopWords();
//	List<String> wordList = sw.stopWord(str);
	List<ClassifyResult> crs = new ArrayList<ClassifyResult>();
	Configuration cfg = new Configuration();
	SessionFactory sf = cfg.configure().buildSessionFactory();
	Session s = sf.openSession();
	Query q = s.createQuery("from Pro1 s");
	List<Pro1> list = q.list();
	float probility = 0.0F;
	for(Pro1 ss:list){	
		probility = new Manager().calcProd(wordList, ss.getLeibie());//计算给定的文本属性向量在给定的分类Ci中的分类条件概率
		//保存分类结果
		ClassifyResult cr = new ClassifyResult();
		cr.setClassification(ss.getLeibie());;//分类
		cr.setProbility(probility);//关键字在分类的条件概率
//		System.out.println("In process....");
//		System.out.println(ss.getLeibie() + "：" + probility);
		crs.add(cr);
	}	
//	System.out.println("计算完毕，正在进行最后排序。。。");
	java.util.Collections.sort(crs,new Comparator() 
	{
		public int compare(final Object o1,final Object o2) 
		{
			final ClassifyResult m1 = (ClassifyResult) o1;
			final ClassifyResult m2 = (ClassifyResult) o2;
			final double ret = m1.getProbility() - m2.getProbility();
			if (ret < 0) 
			{
				return 1;
			} 
			else 
			{
				return -1;
			}
		}
	});
	//返回概率最大的分类
	String result = crs.get(0).getClassification();
//	String result2 = crs.get(1).getClassification();
//	String result3 = crs.get(2).getClassification();
//	String result = result1 + "\n" +result2 + "\n"+result3;
	return result;
}
@SuppressWarnings("static-access")
public static void main(String[] args) throws Exception {
	String str ="目的分析江西省丙肝流行现状,为制定防治对策提供科学依据。方法采用描述流行病学方法对丙肝发病情况和流行特征进行统计与分析。结果九年间全省报告发病数和发病率呈逐年上升趋势。全年均有发病、季节性不明显、部分重点地市发病率高、年龄范围覆盖青壮老年、男性明显多于女性、职业分布以农民、家务及待业和离退休人员为主。结论江西省丙肝发病率呈快速增长趋势,重点应加强大众宣传教育、提高基层卫生机构防控意识,规范血制品管理与使用。";
	List<String> keywords = new ChouquKey().run(str, 3);
	System.out.println(keywords);
	String res = fenlei(keywords);
	System.out.println("分类结果：   "+res);
}
}
