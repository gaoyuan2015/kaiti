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
	String result1 = crs.get(0).getClassification();
	String result2 = crs.get(1).getClassification();
	String result3 = crs.get(2).getClassification();
	String result = result1 + "\n" +result2 + "\n"+result3;
	return result;
}
@SuppressWarnings("static-access")
public static void main(String[] args) throws Exception {
	String str ="2013年，中国海洋产业告别了低迷的市场困境，驶入快速发展期，海水淡化行业也经历了翻天覆地的变化，尤其体现在海水净化设备和技术的使用及获取信息的渠道方式越来越多样化，国内外海水综合利用的飞速发展给行业带来了新的挑战与机遇，同时也表明海水淡化行业迎来了攸关行业发展的关键转型期，那么海水淡化市场正面临 着怎样的转型与机遇？在整个宏观经济环境处于转型期的大背景下，海水淡化行业又面临着怎样的挑战？ 为了更好的解读2015年海水淡化市场发展的良好态势，海水淡化展（简称）举办主题为“转型与机遇”的国际海水淡化设备与应用博览会高峰论坛。处于转型期的海洋产业市场创新速度令人窒息，新兴的技术与模式不断颠覆着海洋产业的发展，十八大的召开为海洋产业的发展同样带来了新的发展契机，届时将有专家为您详细解读十八大的政策优势及机遇。 本次以“转型与机遇”为主题的国际海水淡化设备与应用博览会高峰论坛将于2016年4月22日在宁波举行，旨在为大家解读处于转型期的海水淡化市场正面临哪些挑战与机遇，行业同仁共同探讨行业未来发展方向。 论坛主题：海水淡化产业的“转型与机遇” 地 ? 点：宁波国际会展中心-会议厅 举办时间：2016年4月22日 主? 办? 方：北京中瑞特展览有限公司 支持单位：中国国家海洋局、欧洲脱盐学会?";
	List<String> keywords = new ChouquKey().run(str, 0.1);
	System.out.println(keywords);
	String res = fenlei(keywords);
	System.out.println("分类结果：   "+res);
}
}
