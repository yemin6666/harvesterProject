package harvester.controller;

import harvester.service.SqlUtil;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import util.JsonResponse;
import util.MD5;
import util.StringUtil;
@WebServlet("/register")
public class Register extends HttpSimpleProcessing{

	private static final long serialVersionUID = 2855335071556888894L;
	
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
		if(!userNameIsUsable(userName)){
			return JsonResponse.fail("用户名已存在");
		}
		String sql = "insert into user(userName,password) values(?,?)";
		int count = SqlUtil.executeUpdate(sql, userName,password);
		if(count==1){
			return JsonResponse.success();
		}else{
			return JsonResponse.fail("注册失败");
		}
	}
	
	private boolean userNameIsUsable(String userName) throws SQLException{
		String sql = "select * from user where userName=? ";
		int count = SqlUtil.queryCount(sql, userName);
		if(count>0)
			return false;
		return true;
	}
	
}
