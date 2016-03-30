package jiequziduan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nbu.lib.AimYuLiaoDAO;
import nbu.lib.SrcYuLiaoDAO;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ziduanchaifen {
	private final static String logfile = "g:" + File.separator + "logs" + File.separator +"yuliao.log";
	public static void main(String[] args) throws Exception {
		System.setOut(new PrintStream(new FileOutputStream(new File(logfile))));
		String regEx= "^[a-zA-Z].*";
		Pattern p = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
	Configuration cfg = new Configuration();
	SessionFactory sf = cfg.configure().buildSessionFactory();
	Session s = sf.openSession();
	String hql = "from SrcYuLiaoDAO ";
	Query q = s.createQuery(hql);
	@SuppressWarnings("unchecked")
	List<SrcYuLiaoDAO> list111 = q.list();
	List<AimYuLiaoDAO> list2 = new ArrayList<AimYuLiaoDAO>();
	int t =0;
	for (SrcYuLiaoDAO sss : list111) {
		t++;
		if(t%10000 == 0){
			System.out.println(new Date()+"数理数据"+t+"条了。");
		}
		String str[] = null;
		String aa = sss.getFenleihao().trim();
		Matcher m = p.matcher(aa);
		if(m.matches()){
		try {
			str = aa.split(";");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(sss.getFenleihao()+sss.getId());
			continue;
		}
		if(str.length == 1){
//		for(String ss:str){
		AimYuLiaoDAO cs = new AimYuLiaoDAO();			
			cs.setTitle(sss.getTitle());
			cs.setZhaiyao(sss.getZhaiyao());
			cs.setKeywords(sss.getKeywords());
			cs.setFenleihao(str[0]);
			list2.add(cs);
//		}
		}
		}else{
			continue;
		}

	}
	s.beginTransaction();
	for (AimYuLiaoDAO we : list2) {
		try {
			s.save(we);
		} catch (HibernateException e) {
		}
	}
	s.getTransaction().commit();
	sf.close();
	s.close();
	}
}
