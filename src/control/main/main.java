package control.main;

import model.business.*;
import view.main.*;

/**
* @author Cianc
* @version ����ʱ�䣺2019��4��28�� ����2:41:51
* @ClassName main
* @Description control
*/
public class main {
	mainView main = null;
	public static void main(String []args) {
		init();
		operaControl.init();
		mainView main = new mainView();
		main.buildCourse();
	}
	
	public static void init() {
		try {
			Class.forName(inForm.JDBC_DRIVE);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
