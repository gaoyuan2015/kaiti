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
		System.out.println("In process....");
		System.out.println(ss.getLeibie() + "：" + probility);
		crs.add(cr);
	}	
	System.out.println("计算完毕，正在进行最后排序。。。");
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
	return result;
}
@SuppressWarnings("static-access")
public static void main(String[] args) throws Exception {
	String str ="目的分析毛细支气管炎患儿医院感染相关因素,评估治疗疗效,为临床治疗提供参考。方法选择2012年8月-2013年8月住院期间感染患儿132例,分析感染病毒、病原菌种类及相关因素,评价治疗后的疗效,应用SPSS 15.0统计软件进行数据分析,计数资料采用χ2检验。结果 132例毛细支气管炎患儿中,108例患儿属于病毒感染,占81.82%,共检测出病毒116株,其中呼吸道合胞病毒(RSV)检出最多占46.55%,其次为柯萨奇病毒(CBV)占9.48%;24例患儿属于病原菌感染,占18.18%,共检测出病原菌34株,其中大肠埃希菌检出最多占41.18%,其次为金黄色葡萄球菌、肺炎克雷伯菌,分别占32.35%、17.65%;男性、年龄≤6个月、早产儿、父母有呼吸道疾病等的患儿发生医院感染的比例高于女性、年龄≤6个月、非早产儿和父母无呼吸道疾病患儿,差异均有统计学意义(P<0.05);治疗和护理7d后,治愈94例、好转36例、无效2例,有效率为98.48%。结论毛细支气管炎患儿发生医院感染受多种因素影响,不同患儿感染的感染源不同,需要不同的针对性治疗,同时进行合理的护理干预,能够有效控制病情并较快治愈康复。";
	List<String> keywords = new ChouquKey().run(str, 0.2);
	System.out.println(keywords);
	String res = fenlei(keywords);
	System.out.println("分类结果：   "+res);
}
}
