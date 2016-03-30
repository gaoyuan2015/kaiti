package statisticsDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nbu.lib.AimDAO;
import nbu.lib.SrcDAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LeiStatistics {
	public static HashMap<String, Integer> leimap = new HashMap<String, Integer>();
	private final static String logfile = "f:" + File.separator + "logs"
			+ File.separator + "toutiao.log";

	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new FileOutputStream(new File(logfile))));
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		Query q = s.createQuery("from SrcDAO s");
		@SuppressWarnings("unchecked")
		List<SrcDAO> list111 = q.list();
		// float N = list111.size();
		for (SrcDAO bd : list111) {
			String leibie = bd.getFenleihao();
			if (leimap.containsKey(leibie)) {
				leimap.put(leibie, leimap.get(leibie) + 1);
			} else {
				leimap.put(leibie, 1);
			}
		}
		// int a = 0;
		List<AimDAO> list1 = new ArrayList<AimDAO>();
		for (SrcDAO ss : list111) {
			// float m = leimap.get(ss)/N;
			if (leimap.get(ss.getFenleihao()) > 99) {
				AimDAO p = new AimDAO();
				p.setId(ss.getId());
				p.setTitle(ss.getTitle());
				p.setFenleihao(ss.getFenleihao());
				list1.add(p);
			}
		}
			s.beginTransaction();
			for (AimDAO we : list1) {

				s.save(we);

			}
			s.getTransaction().commit();
			if (s != null) {
				s.close();
			}
			// System.out.println("类别:"+ss+"数量为:"+leimap.get(ss)+";样本数量为:"+N+":比重为:"+m);
		
		// System.out.println("大于99的类别一个"+a+"个");
}
}