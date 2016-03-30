package chouquxunce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import nbu.lib.Aim1DAO;
import nbu.lib.AimDAO;
import nbu.lib.SrcDAO;

public class chouqu {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<SrcDAO> list1 = new ArrayList<SrcDAO>();
		List<SrcDAO> list2 = new ArrayList<SrcDAO>();
		List<AimDAO> list3 = new ArrayList<AimDAO>();
		List<Aim1DAO> list4 = new ArrayList<Aim1DAO>();
		List<Integer> xun = new ArrayList<Integer>();
		List<Integer> ce = new ArrayList<Integer>();
		while (xun.size() < 180000) {
			int n = new Random().nextInt(192630);
			xun.add(n);
		}
		while (ce.size() < 1000) {
			int n = new Random().nextInt(192630);
			if (!xun.contains(n)) {
				ce.add(n);
			}
		}
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		@SuppressWarnings("rawtypes")
		Iterator it = xun.iterator();
		@SuppressWarnings("rawtypes")
		Iterator dt = ce.iterator();
		while (it.hasNext()) {
			int d = (int) it.next();
			String hql = "from SrcDAO where id=:A";
			Query q = s.createQuery(hql);
			if (hql.indexOf(":A") != -1) {
				q.setParameter("A", d);
			}
			list1.addAll(q.list());
		}
		while (dt.hasNext()) {
			int d = (int) dt.next();
			String hql = "from SrcDAO where id=:A";
			Query q = s.createQuery(hql);
			if (hql.indexOf(":A") != -1) {
				q.setParameter("A", d);
			}
			list2.addAll(q.list());
		}
		for (SrcDAO sss : list1) {
			AimDAO cs = new AimDAO();
			cs.setId(sss.getId());
			cs.setTitle(sss.getTitle());
			cs.setFenleihao(sss.getFenleihao());
			list3.add(cs);
		}
		for (SrcDAO sss : list2) {
			Aim1DAO cs = new Aim1DAO();
			cs.setId(sss.getId());
			cs.setTitle(sss.getTitle());
			cs.setFenleihao(sss.getFenleihao());
			list4.add(cs);
		}
		s.beginTransaction();
		for (AimDAO we : list3) {
			s.save(we);
		}
		s.getTransaction().commit();
		s.beginTransaction();
		for (Aim1DAO we : list4) {
			s.save(we);
		}
		s.getTransaction().commit();
		sf.close();
		s.close();
	}
}
