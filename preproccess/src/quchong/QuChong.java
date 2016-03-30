package quchong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nbu.lib.Aim2DAO;
import nbu.lib.SrcDAO1;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class QuChong {
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		String hql = "from SrcDAO1 ";
		Query q = s.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<SrcDAO1> list111 = q.list();
		List<Aim2DAO> list1 = new ArrayList<Aim2DAO>();
		HashSet<String> list2 = new HashSet<String>();
		for (SrcDAO1 sss : list111) {
			list2.add(sss.getZt() + "_" + sss.getXk());
		}
		for (String str : list2) {
			Aim2DAO ad = new Aim2DAO();
			ad.setZt(str.split("_")[0]);
			ad.setXk(str.split("_")[1]);
			list1.add(ad);
		}
		s.beginTransaction();
		for (Aim2DAO we : list1) {
			try {
				s.save(we);
			} catch (HibernateException e) {
				System.out.println(e);
			}
		}
		s.getTransaction().commit();
		sf.close();
		s.close();

	}
}
