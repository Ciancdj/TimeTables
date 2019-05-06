package model.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import model.cao.operaCourse;
import model.cao.operaUser;

/**
* @author Cianc
* @version 创建时间：2019年4月21日 上午9:59:26
* @ClassName webObtain
* @Description used httpclien5 and jsoup to get website information
*/
public class webObtain {
	// users Information
	private String userAccount;
	private String userPassword;
	private String csrftoken;
	private String mCookies;
	private String webURL;
	private Calendar date = null;
	private int FUN_NUM = 2;
	private String[] Surl = null;
	private String[] gnmkdm = null;
	/**
	 * To init the user information
	 * @param account user account
	 * @param password	user password
	 * @param url	web url
	 */
	public webObtain(String account, String password, String url) {
		userAccount = account;
		userPassword = password;
		webURL = url;
		date = Calendar.getInstance();
		Surl = new String[FUN_NUM];
		gnmkdm = new String[FUN_NUM];
		for(int index = 0; index < FUN_NUM; index++) {
			Surl[index] = gnmkdm[index] = "";
		}
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			mCookies = response.getFirstHeader("Set-Cookie").getValue();
			Matcher tem = getTarget(mCookies, "(.*?);");
			if(tem.find()) {
				mCookies = tem.group(1);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int checkLogin() {
		int ret = 1;
		String html = this.getHTML(webURL + "/xtgl/login_slogin.html?time=" + date.getTimeInMillis());
		getCsrftoken(html);
		List<NameValuePair> paramList = new ArrayList<>();
		paramList.add(new BasicNameValuePair("csrftoken", csrftoken));
		paramList.add(new BasicNameValuePair("yhm", userAccount));
		paramList.add(new BasicNameValuePair("mm", userPassword));
		paramList.add(new BasicNameValuePair("mm", userPassword));
		html = sendPostResponse(webURL + "/xtgl/login_slogin.html?time=" + date.getTimeInMillis(), 
				new UrlEncodedFormEntity(paramList, Consts.UTF_8));
		// use jsoup to get course.grade function url
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("#cdNav");
		html = trs.toString();
		if(html == null || html.equals("")) {
			ret = -1;
		}
//		String courseRule = "'(.*?)','(.*?)','学生课表查询'";
//		String gradeRule = "'(.*?)','(.*?)','学生成绩查询'";
//		Matcher tem = getTarget(html, courseRule);
//		if(tem.find()) {
//			ret = 1;
//		}
//		System.out.println(ret);
		return ret;
	}
	
	public Matcher getTarget(String text, String rule) {
		Pattern p = Pattern.compile(rule);
		Matcher m = p.matcher(text);
		return m;
	}

	public void login() {
		String html = this.getHTML(webURL + "/xtgl/login_slogin.html?time=" + date.getTimeInMillis());
		getCsrftoken(html);
		List<NameValuePair> paramList = new ArrayList<>();
		paramList.add(new BasicNameValuePair("csrftoken", csrftoken));
		paramList.add(new BasicNameValuePair("yhm", userAccount));
		paramList.add(new BasicNameValuePair("mm", userPassword));
		paramList.add(new BasicNameValuePair("mm", userPassword));
		html = sendPostResponse(webURL + "/xtgl/login_slogin.html?time=" + date.getTimeInMillis(), 
				new UrlEncodedFormEntity(paramList, Consts.UTF_8));
		// use jsoup to get course.grade function url
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("#cdNav");
		html = trs.toString();
		String courseRule = "'(.*?)','(.*?)','学生课表查询'";
		String gradeRule = "'(.*?)','(.*?)','学生成绩查询'";
		Matcher tem = getTarget(html, courseRule);
		if(tem.find()) {
			gnmkdm[0] = tem.group(1);
			Surl[0] = tem.group(2);
		}
		tem = getTarget(html, gradeRule);
		if(tem.find()) {
			gnmkdm[1] = tem.group(1);
			Surl[1] = tem.group(2);
		}
	}
	
	public String getCourse(String year, String month, String name) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(webURL+"/kbcx/xskbcx_cxXsKb.html?"
				+ "gnmkdm=" + gnmkdm[0] + "&xnm=" + year + "&xqm=" + month);
		httpGet.setHeader("Cookie", mCookies);
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200) {
				entity = response.getEntity();
				JSONObject Main = JSON.parseObject(EntityUtils.toString(entity, "utf-8").trim());
				JSONObject xsxx = Main.getJSONObject("xsxx");
				JSONArray jarr = Main.getJSONArray("kbList");
				if(name.equals("") || name == null) {
					operaUser.addUserInfor(userAccount, xsxx.getString("XM"));
					inForm.Name = xsxx.getString("XM");
				}
				operaCourse.clearCourseTable();
				/**
				 * cd-id 课室号
				 * cdmc 详细课室号
				 * jc 详细上课节号
				 * jcor 节号
				 * kcmc 课名
				 * xm 教师名
				 * xqj 星期序号
				 * XH_ID 学号
				 * XM 姓名
				 * zcd 详细上课时间
				 */
				for(int index = 0; index < jarr.size(); index++) {
					JSONObject tem = jarr.getJSONObject(index);
//					System.out.println(
//							"cdmc:" + tem.getString("cdmc") + "\njc:" + tem.getString("jc") 
//							+ "\nkcmc:" + tem.getString("kcmc") + "\nxm:" + tem.getString("xm") 
//							+ "\nxqj:" + tem.getString("xqj") + "\nxqjmc:" + tem.getString("xqjmc")
//							+ "\nzcd:" + tem.getString("zcd")
//							);
//					System.out.println("----------------------------------------");
					operaCourse.addCourse(tem.getString("cd_id"),tem.getString("cdmc"), tem.getString("kcmc"), tem.getString("xm"),
							Integer.parseInt(tem.getString("xqj")), tem.getString("jc"), tem.getString("zcd"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * use param url to get the url html text
	 * @param url taget url
	 * @return return html text
	 */
	public String getHTML(String url) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		Calendar date = Calendar.getInstance();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Cookie", mCookies);
		HttpResponse response = null;
		HttpEntity entity = null;
		String HTMLtext = "";
		try {
			response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200) {
				entity = response.getEntity();
				HTMLtext = EntityUtils.toString(entity, "utf-8");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HTMLtext;
	}
	
	/**
	 * use jsoup to get the csrftoken value
	 * @param HTML html text
	 */
	public void getCsrftoken(String HTML) {
		Document doc = Jsoup.parse(HTML);
		Elements trs = doc.select("#csrftoken");
		csrftoken = trs.attr("value");
	}
	
	public String sendPostResponse(String url, UrlEncodedFormEntity entity) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		Calendar date = Calendar.getInstance();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Cookie", mCookies);
		httpPost.setEntity(entity); 
		HttpResponse response = null;
		String html = "";
		try {
			response = httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 302) {
				html = getHTML(webURL + "/xtgl/index_initMenu.html");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}
}
