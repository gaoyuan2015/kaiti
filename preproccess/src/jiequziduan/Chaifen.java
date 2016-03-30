package jiequziduan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nbu.lib.Aim2DAO;
import nbu.lib.AimYuLiaoDAO;
import nbu.lib.SrcDAO1;
import nbu.lib.SrcYuLiaoDAO;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Chaifen {
	public static void main(String orgs[]) {
		String regEx="[`~!@#$%^&*()--+=|{}':;',//[//]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
//		String regEx= "^[a-zA-Z].*"; 
		String ss = "F20;F59;F71;G25;P31;P96;R1;R11;R20;R31;R36;R44;R82;S1;S23;S3;S77;S78;S79;S88;S89;S9;TB1;TB4;TB5;TB6;TN0;TN8;TS9;TU3;TU7;TU8;TU9;U67;Z22";
		Pattern p = Pattern.compile(regEx);
//		Pattern p = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session s = sf.openSession();
//		String hql = "from SrcDAO1 ";
//		Query q = s.createQuery(hql);
//		List<SrcDAO1> list111 = q.list();
		String[] st = ss.split(";");
		List<String> stt = new ArrayList<String>();
		for(int i=0;i<st.length;i++){
			stt.add(st[i]);
		}
		int t=0;
		for (int i=1;i<=10000;i++) {
			t++;
			System.out.println(new Date()+"已处理"+t+"条数据。");
			SrcYuLiaoDAO sss = (SrcYuLiaoDAO) s.get(SrcYuLiaoDAO.class, i);
			AimYuLiaoDAO cs = new AimYuLiaoDAO();			
//			cs.setId(sss.getId());
			cs.setTitle(sss.getTitle());
			cs.setZhaiyao(sss.getZhaiyao());
			cs.setKeywords(sss.getKeywords());
			String str = sss.getFenleihao();
//			cs.setFenleihao(str);
			Matcher m = p.matcher(str);
			String mat = m.replaceAll("").trim();
			if (mat.length() >2) {
				String ma = mat.substring(0,3);
				if(stt.contains(ma)){
				cs.setFenleihao(mat);
				}else{
				cs.setFenleihao(ma);
				}
			}else{
				cs.setFenleihao(mat);
			}
			if(m.matches()){
				s.beginTransaction();
				s.save(cs);
			}
			if(i%1000==0){
				s.getTransaction().commit();
			}
		}
		sf.close();
		s.close();

	}

}
