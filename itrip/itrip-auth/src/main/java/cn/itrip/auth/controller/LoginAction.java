package cn.itrip.auth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ytx.org.apache.http.HttpResponse;
import ytx.org.apache.http.HttpStatus;
import ytx.org.apache.http.client.methods.HttpGet;
import ytx.org.apache.http.impl.client.DefaultHttpClient;
import ytx.org.apache.http.util.EntityUtils;

@Controller
@RequestMapping(value = "/WeCat")
  
public class LoginAction {

	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("=======进入login=========>>");
		String url = "https://open.weixin.qq.com/connect/qrconnect?";
		url += "appid=wx9c8b33b59d002f38";
		url += "&redirect_uri=" + URLEncoder.encode("http://f019bd81.ngrok.io/wecat/WeCat/callBackLogin", "GBK");
		url += "&response_type=code&scope=snsapi_login";
		url += "&state=" + request.getSession().getId() + "#wechat_redirect";
		System.out.println("url>>>" + url);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/callBackLogin")
	public String callBackLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("callBackLogin....");
		// return "redirect:../loginSuccess.jsp";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code=" + code);
		System.out.println("state=" + state);
		// 获得access_token数据，获得访问令牌。等下要通过令牌去获得用户的信息
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
		url += "appid=wx9c8b33b59d002f38";
		url += "&secret=79a95a7c92f76128c57a6e5756dd1057";
		url += "&code=" + code + "&grant_type=authorization_code";
		// 要去执行这个URL，并通过这个URL获得返回值
		JSONObject jsonObject = this.httpGet(url);
		String at = jsonObject.getString("access_token");
		String openId = jsonObject.getString("openid");
		System.out.println("at="+at);
		url="https://api.weixin.qq.com/sns/userinfo?access_token="+at+"&openid="+openId;
		jsonObject = this.httpGet(url);
		return "../loginSuccess";
	}

	/**
	 * 发送get请求 http://www.cnblogs.com/QQParadise/articles/5020215.html
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				System.out.println("strResult..." + strResult);
				String str= new String(strResult.getBytes("ISO-8859-1"), "UTF-8");
				System.out.println("str..." + str);
				/** 把json字符串转换成json对象 **/
				jsonResult = JSON.parseObject(str);
				System.out.println("strResult=" + str);
			} else {
				System.out.println("读取数据失败..");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
}
// The ends;
