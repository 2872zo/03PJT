package com.model2.mvc.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class GetUserAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId=request.getParameter("userId");
		
		UserService userService=new UserServiceImpl();
		User user=userService.getUser(userId);
		
		List<String> userDetailList = new ArrayList<String>();
		userDetailList.add("회원 정보 조회");
		userDetailList.add("아이디,"+user.getUserId());
		userDetailList.add("이름,"+user.getUserName());
		userDetailList.add("주소,"+(user.getAddr() != null?user.getAddr():""));
		userDetailList.add("휴대전화번호,"+(user.getPhone() != null?user.getPhone():""));
		userDetailList.add("이메일,"+(user.getEmail() != null?user.getEmail():""));
		userDetailList.add("가입일자,"+(user.getRegDate() != null?user.getRegDate():""));
		
		request.setAttribute("list", userDetailList);
		
		return "forward:/user/getUser.jsp";
	}
}