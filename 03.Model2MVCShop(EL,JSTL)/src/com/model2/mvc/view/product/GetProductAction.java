package com.model2.mvc.view.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public GetProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetURI = null;
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));

		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);

		System.out.println(request.getParameter("menu"));
		request.setAttribute("product", product);
		
		//list ���·� product ����
		List<String> productList = new ArrayList<String>();
		productList.add("��ǰ �� ��ȸ");
		productList.add("��ǰ��ȣ,"+product.getProdNo());
		productList.add("��ǰ��,"+product.getProdName());
		productList.add("��ǰ�̹���,"+(product.getFileName() != null?"<img src='../images/uploadFiles/"+product.getFileName()+"'/>":"<img src='http://placehold.it/400x400'/>"));
		productList.add("��ǰ������,"+product.getProdDetail());
		productList.add("��������,"+product.getManuDate());
		productList.add("����,"+product.getPrice());
		productList.add("�������,"+product.getRegDate());
		
		request.setAttribute("list", productList);

		if (request.getParameter("menu") == null || request.getParameter("menu").equals("search")) {
			targetURI = "forward:/product/getProduct.jsp";
		} else if (request.getParameter("menu").equals("manage")) {
			targetURI = "forward:/updateProductView.do";
		}

		
		
		String history = null;
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie tempCookie = cookies[i];
				if (tempCookie.getName().equals("history")) {
					history = tempCookie.getValue();
				}
			}
			
			if (history != null) {
				if(history.indexOf(String.valueOf(product.getProdNo())) == -1) {
					cookie = new Cookie("history", history + "," + String.valueOf(product.getProdNo()));
				}
			} else {
				history = String.valueOf(product.getProdNo());
				cookie = new Cookie("history", history);
			}
			
			if(cookie != null) {
				response.addCookie(cookie);
			}
		}
		return targetURI;
	}

}
