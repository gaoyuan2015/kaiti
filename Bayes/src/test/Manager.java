package test;

import java.io.IOException;
import java.util.List;

import nbu.lib.dao.Pro1;
import nbu.lib.dao.Pro2;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Manager {
	/**
	 * 计算给定的样本属性向量X在给定的分类Cj中的类条件概率 <code>ClassConditionalProbability</code>连乘值
	 * 
	 * @param X
	 *            给定的样本属性向量
	 * @param Cj
	 *            给定的类别
	 * @return 分类条件概率连乘值，即<br>
	 * @throws IOException
	 */
	public float calcProd(List<String> X, String Cj) throws IOException {
		float ret = 1.0F;
		// 类条件概率连乘
		for (String ss : X) {

			// 因为结果过小，因此在连乘之前放大1000倍，这对最终结果并无影响，因为我们只是比较概率大小而已
			ret *= pxc(ss, Cj) * 1000;
		}
		// 再乘以先验概率
		ret *= calculatePc(Cj);
		System.out.println(Cj+"  概率 probility为：" + ret);
		return ret;
	}

	private float pxc(String ss, String cj) throws IOException {
		float Nxc = getCountContainKeyOfClassification(cj, ss);
//		System.out.println("Nxc=" + Nxc);
		float Nc = getTrainingNumOfClassification(cj);
//		System.out.println("Nc=" + Nc);
		// int M = 35986; //样本训练得到的数据
		float M = (float) 30000; // 对Pro2训练结果继续查询：distinct ci 197684
//		float V = (float) 75; // 样本训练得到的数据
		float ret = (Nxc + 1) / (Nc + M);
		return ret;
	}

	/**
	 * 返回给定分类中包含关键字／词的样本数目
	 * 
	 * @param classification
	 *            给定的分类
	 * @param key
	 *            给定的关键字／词
	 * @return 给定分类中包含关键字／词的样本数目
	 */
	@SuppressWarnings("unchecked")
	public float getCountContainKeyOfClassification(String cj, String ss)
			throws IOException {
		float ret = 0;
		// 词和类别能唯一确定一个count
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		String hql = "from Pro2 where ci=:A and xk=:B";
		Query q = s.createQuery(hql);
		if (hql.indexOf(":A") != -1 && hql.indexOf(":B") != -1) {
			q.setParameter("A", ss);
			q.setParameter("B", cj);
		}
		List<Pro2> list = null;
		try {
			list = q.list();
			for (Pro2 bd : list) {
				ret = (float)bd.getWendangshu();
			}
		} catch (Exception e) {
		} finally {
			if (s != null)
				s.close();
			sf.close();
		}
		return ret;
	}

	private float getTrainingNumOfClassification(String cj) {
		float a = 0;
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		String hql = "from Pro1 where leibie=" + "'" + cj + "'";
		Query q = s.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Pro1> list111 = q.list();
		for (Pro1 ss : list111) {
			a = (float)ss.getPindu();
		}
		s.close();
		sf.close();
		return a;
	}

	private float calculatePc(String cj) {
		float a = 0;
		final int m = 1278586;
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		String hql = "from Pro1 where leibie=" + "'" + cj + "'";
		Query q = s.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Pro1> list111 = q.list();
		for (Pro1 ss : list111) {
			a = ss.getPindu();
		}
		s.close();
		sf.close();
		return (float) a / m;
	}
}
