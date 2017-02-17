package harvester.controller;

import harvester.service.SqlUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import util.JsonResponse;
import util.MD5;
import util.StringUtil;
@WebServlet("/login")
public class Login extends HttpSimpleProcessing{

	private static final long serialVersionUID = 4196798775785804519L;

	@Override
	protected String processing(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String sign = req.getParameter("sign");
		if(StringUtil.isEmpty(userName,password)){
			return JsonResponse.fail("账号密码不能为空");
		}
		String localSign = MD5.digest(userName+password+Constant.MD5_KEY);
		if(!localSign.equals(sign)){
			return JsonResponse.fail("sign验证失败");
		}
		String sql = "select count(*) from user where userName=? and password=?";
		int count = SqlUtil.queryCount(sql, userName,password);
		if(count>0){
			return JsonResponse.success();
		}
		else{
			return JsonResponse.fail("账号密码错误");
		}
	}
}
