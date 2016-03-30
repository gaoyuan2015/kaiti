package chouqukey;

import java.util.List;
import nbu.lib.AimKeyDAO;
import nbu.lib.ItfDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import test.WordSplit;

public class Train {
	public static final int num = 10000;
	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		WordSplit sw = new WordSplit();
		for (int d = 1; d <= num; d++) {
			ItfDAO itf = (ItfDAO) s.get(ItfDAO.class, d);
			List<String> words = sw.stopWord(itf.getZhaiyao());
			for (String str : words) {
				AimKeyDAO cw = new AimKeyDAO();
				cw.setCi(str);
				cw.setWendanghao(d);
				s.beginTransaction();
				s.save(cw);
			}
			if (d % 10000 == 0) {
				s.getTransaction().commit();
			}
		}
		sf.close();
		s.close();
	}
}
