package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbu.lib.dao.TestDAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import chouqukey.ChouquKey;

public class PTest {
	private final static String logfile = "f:" + File.separator + "logs"
			+ File.separator + "bayes" + File.separator +"bayesce500.log"; // 日志文件
	@SuppressWarnings({ "static-access", "unchecked" })
	public static void main(String[] args) throws Exception {
		int count = 0;
		int num = 0;
		float recall = 0, precision = 0;
		Map<String, Integer> rightcount = new HashMap<String, Integer>();
		Map<String, Integer> recallcount = new HashMap<String, Integer>();
		Map<String, Integer> precisioncount = new HashMap<String, Integer>();
		File file = new File(logfile);
		System.setOut(new PrintStream(new FileOutputStream(file)));
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		String hql = "from TestDAO where id>="+1+" and id<="+500;
		Query q = s.createQuery(hql);
		List<TestDAO> test = q.list();
		s.close();
		sf.close();
		Test tt = new Test();
		ChouquKey cq = new ChouquKey();
		for (TestDAO td : test) {
			List<String> keywords = cq.run(td.getZhaiyao(), 3);
			String res = tt.fenlei(keywords);
			String leibie = td.getXk();
			if (res.equals(leibie)) {
				count++;
				if (rightcount.containsKey(leibie)) {
					rightcount.put(leibie, rightcount.get(leibie) + 1);
				} else {
					rightcount.put(leibie, 1);
				}
			}
			if (recallcount.containsKey(leibie)) {
				recallcount.put(leibie, recallcount.get(leibie) + 1);
			} else {
				recallcount.put(leibie, 1);
			}
			if (precisioncount.containsKey(res)) {
				precisioncount.put(res, precisioncount.get(res) + 1);
			} else {
				precisioncount.put(res, 1);
			}
			num++;
			if (num % 10 == 0) {
				System.out.println(new Date() + "已测试了" + num + "条，正确分类" + count
						+ "条。");
			}
			
		}
		System.out.println(new Date() + "测试数据" + num + "条，分类正确" + count + "条。");
		
			for (String ss : recallcount.keySet()) {
				if(rightcount.keySet().contains(ss) & recallcount.keySet().contains(ss)){
				recall = (float) rightcount.get(ss) / recallcount.get(ss);
				precision = (float) rightcount.get(ss) / precisioncount.get(ss);
				}
				if(!rightcount.keySet().contains(ss)){
					recall = 0;
					precision = 0;
				}
				if(!recallcount.keySet().contains(ss)){
					recall = 1;
					precision = 1;
				}
				System.out.println(new Date() + "测试数据" + num + "条，类别" + ss
						+ "的查全率为" + recall);
				System.out.println(new Date() + "测试数据" + num + "条，类别" + ss
						+ "的查准率为" + precision);
			}
		
	
	}
}
