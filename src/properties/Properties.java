package properties;

public class Properties {

	public final static double ALIGNMENT_WEIGHT=0.9;
	public final static double DEPENDENCY_WEIGHT=0.1;
	public final static double GAMA=0.00001;
	
	public final static String WORD_EMB_PATH=
	//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\Tools and Resources\\glove.6B\\glove.6B.300d.txt";
	"resources\\glove.840B.300d.txt";
	
	public final static String PARENT_FOLDER_PATH="data\\stsTDIDF\\2017\\STS2017";
	public  static String FOLDER_NAME="";//"STS2012-en-train.input.gs.MSRpar";
	public  static String FOLDER_PATH=PARENT_FOLDER_PATH+"\\"+FOLDER_NAME;
	
	public  static String STOP_WORDS_PATH=
			"resources\\BigStopList.txt";
	
	public  static String INPUT_FILE_PATH=
			FOLDER_PATH+"\\tokenized_"+FOLDER_NAME+".txt";
			//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines-play.txt";
			//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\Debug_Dependency.txt";
	
	public  static String INPUT_TFIDF_FILE=
			FOLDER_PATH+"\\wtfidf\\TFIDF_"+FOLDER_NAME;//+".txt";
	
	public  static String DEP_INPUT_FILE_PATH=
			FOLDER_PATH+"\\dep_"+FOLDER_NAME+".txt";
			//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\Dependency.txtSTS2016.input.headlines_Dep.txt";
	
	public  static String OUPUT_FILE_PATH=
			FOLDER_PATH+"\\out_TFIDF_"+FOLDER_NAME+".txt";
			//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\output.headlines-30-tfidf-word.txt";
	
	
	public  static String DETILED_OUPUT_FILE_PATH=
			FOLDER_PATH+"\\out_TFIDF_detailed_"+FOLDER_NAME+".txt";
			//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines-Det30-tfidf-word.txt";

	/*public final static String GOLD_FILE_PATH=
			"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.gs.headlines.txt";
	*/
	public final static int MAXIMUM_LINES_COUNT=10000;//just for testing
	//public final static int START_LINES_COUNT=373;//just for testing
	
	public final static double WORD_NET_SYNONYM_STRENGTH=1.0;
	public final static double WORD_NET_HYPERNOM_STRENGTH=1.0;
	public final static boolean USE_WORD_NET_HYPERNOM=true;
	
	public final static String SENT_1_LANG="ENG";
	public final static String SENT_2_LANG="ENG";
	
	public final static String STOP_WORD="STOP_WORD"; 
	
	public final static String CONVERSION_OUPUT_FILE_PATH="FileNotNeededAnyMore";
//			"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines_Converted.txt";

}
