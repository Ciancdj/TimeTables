package model.business;
import java.awt.Color;
import java.util.Calendar;

/**
* @author Cianc
* @version ����ʱ�䣺2019��4��28�� ����3:05:04
* @ClassName ������
* @Description ������
*/
public class inForm {
	// ���ݿ��û���������
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	// ����ͼ������С
	public static int WIDTH = 618;
	public static int HEIGHT = 950;
	// JDBC �����������ݿ�URL
	public static final String JDBC_DRIVE = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/sctimetable?useSSL=false&allowPublicKeyRetrieval=true";
	public static final String[] WEEK = {"��һ","�ܶ�","����","����","����","����","����"};
	// ��������
	public static String imgPath = null;
	public static Calendar courseStart = null;
	// �˻���Ϣ
	public static String Name = "";
	public static String Account = "";
	public static String Password = "";
	// ��ɫ����
	public static Color[] color = {
			Color.green, Color.MAGENTA, Color.yellow, Color.PINK, Color.LIGHT_GRAY, Color.cyan, Color.orange 
	};
}
