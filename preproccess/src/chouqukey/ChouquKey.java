package chouqukey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbu.lib.IdfDAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import test.StopWords;

public class ChouquKey {
public Map<String,Integer> cimap1 = null;
public static final int num = 2000000; 
public List<String> getKey(List<String> list,double scale){
	double a = scale;  
	Map<String,Double> tfidf = new  HashMap<String,Double>();
	List<String> list2 = new ArrayList<String>();
	Map<String,Integer> cimap = new HashMap<String, Integer>();
	for(String s : list){
		if(cimap.containsKey(s)){
			cimap.put(s, cimap.get(s)+1);
		}else{
			cimap.put(s, 1);
		}
	}
	cimap1 = cimap;
	cimap = null;
	int i=0;
    for(String str : list){
    	i++;
    	if(i%20==0){
    		System.out.println(new Date()+"已经处理"+i+"个词语了。你看速度怎么样？");
    	}
	double tf = getTf(str);
	double idf = getIdf(str);
	tfidf.put(str, tf*idf);
    }
    List<Map.Entry<String, Double>> list1 = new ArrayList<Map.Entry<String, Double>>(
			tfidf.entrySet());
	Collections.sort(list1, new Comparator<Map.Entry<String, Double>>() {
		public int compare(Map.Entry<String, Double> o1,
				Map.Entry<String, Double> o2) {
			int res = 0;
			if (o1.getValue() - o2.getValue() > 0) {
				res = -1; // 降序
			} else if (o1.getValue() - o2.getValue() < 0) {
				res = 1;
			}
			return (res);
		}
	}); 
	for(int n=0;n<a*list1.size();n++){
		list2.add(list1.get(n).getKey());
	}
	return list2;
}
public List<String> getKey(List<String> list,int count){
	Map<String,Double> tfidf = new  HashMap<String,Double>();
	List<String> list2 = new ArrayList<String>();
	Map<String,Integer> cimap = new HashMap<String, Integer>();
	for(String s : list){
		if(cimap.containsKey(s)){
			cimap.put(s, cimap.get(s)+1);
		}else{
			cimap.put(s, 1);
		}
	}
	cimap1 = cimap;
	cimap = null;
	int i=0;
    for(String str : list){
    	i++;
    	if(i%20==0){
    		System.out.println(new Date()+"已经处理"+i+"个词语了。你看速度怎么样？");
    	}
	double tf = getTf(str);
	double idf = getIdf(str);
	tfidf.put(str, tf*idf);
    }
    List<Map.Entry<String, Double>> list1 = new ArrayList<Map.Entry<String, Double>>(
			tfidf.entrySet());
	Collections.sort(list1, new Comparator<Map.Entry<String, Double>>() {
		public int compare(Map.Entry<String, Double> o1,
				Map.Entry<String, Double> o2) {
			int res = 0;
			if (o1.getValue() - o2.getValue() > 0) {
				res = -1; // 降序
			} else if (o1.getValue() - o2.getValue() < 0) {
				res = 1;
			}
			return (res);
		}
	}); 
	for(int n=0;n<count;n++){
		list2.add(list1.get(n).getKey());
	}
	return list2;
}
private double getTf(String str) {
	int M = 0;
	for(String s:cimap1.keySet()){
		M=M+cimap1.get(s);
	}
	return (double)cimap1.get(str)/M;
}
@SuppressWarnings("unchecked")
private double getIdf(String str) {
	int t = 0;
	Configuration cfg = new Configuration();
	SessionFactory sf = cfg.configure().buildSessionFactory();
	Session s = sf.openSession();
	Query q = s.createQuery("from IdfDAO where ci="+"'"+str+"'");
	List<IdfDAO> zy = null;
	try {
		zy = q.list();
	if(zy.size()==1){
	   t = zy.get(0).getWendangshu();
	}
	}catch (Exception e) {
	}
	double res = Math.log((double)num/(t+1));
	return res;
}
public static List<String> run(String str,int count) throws Exception{
	ChouquKey ck = new ChouquKey();
	String qx = str.replaceAll("(?!)[^a-zA-Z0-9\u4e00-\u9fa5]","");
	char[] carr = qx.toCharArray();
	for(int i=0;i<qx.length();i++){
		if(carr[i]<0xFF){
			carr[i]=' ';
		}
	}
	String zy = String.copyValueOf(carr).trim();
	List<String> words = new StopWords().stopWord(zy);
	List<String> key =ck.getKey(words,count);
	return key;
}
	public static List<String> run(String str,double scale) throws Exception{
	ChouquKey ck = new ChouquKey();
	String qx = str.replaceAll("(?!)[^a-zA-Z0-9\u4e00-\u9fa5]","");
	char[] carr = qx.toCharArray();
	for(int i=0;i<qx.length();i++){
		if(carr[i]<0xFF){
			carr[i]=' ';
		}
	}
	String zy = String.copyValueOf(carr).trim();
	List<String> words = new StopWords().stopWord(zy);
	List<String> key =ck.getKey(words,scale);
	return key;
}
public static void main(String[] args) throws Exception {
	String content = "目的分析毛细支气管炎患儿医院感染相关因素,评估治疗疗效,为临床治疗提供参考。方法选择2012年8月-2013年8月住院期间感染患儿132例,分析感染病毒、病原菌种类及相关因素,评价治疗后的疗效,应用SPSS 15.0统计软件进行数据分析,计数资料采用χ2检验。结果 132例毛细支气管炎患儿中,108例患儿属于病毒感染,占81.82%,共检测出病毒116株,其中呼吸道合胞病毒(RSV)检出最多占46.55%,其次为柯萨奇病毒(CBV)占9.48%;24例患儿属于病原菌感染,占18.18%,共检测出病原菌34株,其中大肠埃希菌检出最多占41.18%,其次为金黄色葡萄球菌、肺炎克雷伯菌,分别占32.35%、17.65%;男性、年龄≤6个月、早产儿、父母有呼吸道疾病等的患儿发生医院感染的比例高于女性、年龄≤6个月、非早产儿和父母无呼吸道疾病患儿,差异均有统计学意义(P<0.05);治疗和护理7d后,治愈94例、好转36例、无效2例,有效率为98.48%。结论毛细支气管炎患儿发生医院感染受多种因素影响,不同患儿感染的感染源不同,需要不同的针对性治疗,同时进行合理的护理干预,能够有效控制病情并较快治愈康复。";
	List<String> res = run(content,10);
	System.out.println(res);
}
}
