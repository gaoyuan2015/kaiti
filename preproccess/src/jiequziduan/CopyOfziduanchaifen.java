package jiequziduan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nbu.lib.AimYuLiaoDAO;
import nbu.lib.SrcYuLiaoDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CopyOfziduanchaifen {
	private final static String logfile = "g:" + File.separator + "logs" + File.separator +"yuliao.log";

	public static void main(String[] args) throws Exception {
		System.setOut(new PrintStream(new FileOutputStream(new File(logfile))));
		String regEx = "^[a-zA-Z].*";
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
		// String hql = "from SrcYuLiaoDAO where id=:A";
		// Query q = s.createQuery(hql);
		for (int d = 1; d <= 2000000; d++) {
			if (d % 10000 == 0) {
				System.out.println(new Date() + "数理数据" + d + "条了。");
			}
			// q.setParameter("A", d);
			// List<SrcYuLiaoDAO> list111 = q.list();
			// List<AimYuLiaoDAO> list2 = new ArrayList<AimYuLiaoDAO>();
			SrcYuLiaoDAO sss = (SrcYuLiaoDAO) s.get(SrcYuLiaoDAO.class, d);
			String str[] = null;
			String aa = sss.getFenleihao().trim();
			Matcher m = p.matcher(aa);
			if (m.matches()) {
				try {
					str = aa.split(";");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(sss.getFenleihao() + sss.getId());
					continue;
				}
				if (str.length == 1) {
					AimYuLiaoDAO cs = new AimYuLiaoDAO();
					cs.setTitle(sss.getTitle());
					cs.setZhaiyao(sss.getZhaiyao());
					cs.setKeywords(sss.getKeywords());
					cs.setFenleihao(str[0]);
					s.beginTransaction();
					s.save(cs);
				}
			} 
			if (d % 10000 == 0) {
				s.getTransaction().commit();
			}
		}
		sf.close();
		s.close();
	}
}
