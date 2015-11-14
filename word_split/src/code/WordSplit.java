package code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nbu.file.manage.Txt;


public class WordSplit {
	
	public interface CLibrary extends Library {
		// 定义并初始化接口的静态变量   
		CLibrary Instance = (CLibrary) Native.loadLibrary(
				"E://gwz//workspace//NBU_SubjectIndexing//WebRoot//lib//nlpir//win32//NLPIRlkjs", CLibrary.class);
		
		public int NLPIR_Init(String sDataPath, int encoding,
				String sLicenceCode);
				
		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
				boolean bWeightOut);
		public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
				boolean bWeightOut);
		public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
		public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
		public String NLPIR_GetLastErrorMsg();
		public void NLPIR_Exit();
		public int NLPIR_SaveTheUsrDic();
		public boolean NLPIR_NWI_Start();
		public int NLPIR_NWI_AddFile(String sFilename);
		public boolean NLPIR_NWI_AddMem(String sText);
		public boolean NLPIR_NWI_Complete();
		public String NLPIR_NWI_GetResult(boolean s);
		public int NLPIR_NWI_Result2UserDict();

	}
	
	private static String dicPath=".\\dic.txt";        //词典路径
	public static String getDicPath() {
		return dicPath;
	}

	public static void setDicPath(String dicPath) {
		WordSplit.dicPath = dicPath;
	}

	private static String nativeBytes;
	public static List<String> allCiList;
	public static List<String> getAllCiList() {
		return allCiList;
	}

	public static void setAllCiList(List<String> allCiList) {
		WordSplit.allCiList = allCiList;
	}

	public static String getNativeBytes() {
		return nativeBytes;
	}

	public static void setNativeBytes(String nativeBytes) {
		WordSplit.nativeBytes = nativeBytes;
	}
	
	
	//NLPIR初始化
	public void nlpir_initialize(){
		String argu = "E://gwz//workspace//NBU_SubjectIndexing//WebRoot//lib//nlpir";
		// String system_charset = "GBK";//GBK----0
		//String system_charset = "UTF-8";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return;
		}
	}
	
	//用户词典加载
	public void add_dic(String path) throws IOException{
		List<String> list=new Txt().readTxt(path);
		for(String s:list){
			CLibrary.Instance.NLPIR_AddUserWord(s+" n");
		}
		CLibrary.Instance.NLPIR_SaveTheUsrDic();
		
	}
	
	//去除词典中词
	public void remove_dic(String ci) throws IOException{
	
		CLibrary.Instance.NLPIR_AddUserWord(ci);
		
		CLibrary.Instance.NLPIR_SaveTheUsrDic();
		
	}
	
	//分词
	public List<String> getwords(String input) throws IOException{
		
		List<String> list=new ArrayList<String>();
		
		nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(input, 1);

		for(String s:nativeBytes.split(" ")){
			if(s.contains("n")){
				list.add(s.split("\\/")[0]);
			}
		}
		
		return list;
	}
	
	//含词性分词
	public List<String> getwords_cixing(String input) throws IOException{
		
		List<String> list=new ArrayList<String>();
		
		nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(input, 1);

		for(String s:nativeBytes.split(" ")){
			
				list.add(s);
			
		}
		
		return list;
	}
	
	//新词发现
	public List<String> getnew_words(String p) throws IOException{
		CLibrary.Instance.NLPIR_NWI_Start();
		
		List<String> list=new ArrayList<String>();
		
		CLibrary.Instance.NLPIR_NWI_AddMem(p);
		
		CLibrary.Instance.NLPIR_NWI_Complete();
		String nativeStr = CLibrary.Instance.NLPIR_NWI_GetResult(false);
		for(String s:nativeStr.split("#"))
			list.add(s);
		return list;
	}
	
	public static void main(String[] args) throws IOException{
		new WordSplit().nlpir_initialize();
		List<String> s=new WordSplit().getnew_words("然而崇高的理想和残酷的现实之间存在深深的断层，小人物的身份和不切实际的梦想在一个人身上纠结，在个人自由大大获得解放的今天，却又凸显了小人物的迷茫和无助。而正是这种迷茫和无助加速了“屌丝”文化的产生和蔓延。相比较于冯小刚电影里的小人物，“屌丝”拥有相同甚至更加卑微的身份，却未必拥有多么宏大、崇高的理想，他们安于现状却又不满于现实，他们渴望成功，却又无法克服成功路上的种种困难。于是，他们选择了现实中的沉默，网络中的自嘲。群体自嘲是解构现实的武器 也是争取诠释自己生活的权利");
		for(String ss:s)
			System.out.println(ss);
	}
}

