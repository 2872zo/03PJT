package com.model2.mvc.view.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.HttpUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	public ListProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search search = new Search();

		search.setSearchCondition(request.getParameter("searchCondition"));
		if (!(CommonUtil.null2str(request.getParameter("searchKeyword")).equals(""))) {
			if (request.getMethod().equals("GET")) {
				search.setSearchKeyword(HttpUtil.convertKo(request.getParameter("searchKeyword")));
			} else {
				search.setSearchKeyword(request.getParameter("searchKeyword"));
			}
		}

		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} else if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		search.setCurrentPage(currentPage);

		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);

		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);

		Page resultPage = new Page(currentPage, ((Integer) map.get("count")).intValue(),
				Integer.parseInt(getServletContext().getInitParameter("pageUnit")), pageSize);
		System.out.println(resultPage);
		
		String aTagEnd = "</a>";
		
		///title 설정
		//menu의 값에 따른 출력 구별
		String title = null;
		if(request.getParameter("menu").equals("search")) {
			title = "상품 목록 조회";
		}else {
			title = "판매 목록 관리";
		}
		
		///colum 설정
		List<String> columList = new ArrayList<String>();
		columList.add("No");
		columList.add("상품명");
		columList.add("가격");
		columList.add("등록일");
		columList.add("현재상태");
		
		///UnitList 설정
		List<List> unitList = new Vector<List>();
		List<String> UnitDetail = null;
		List<Product> productList = (List<Product>)map.get("list");
		for(int i =0; i<productList.size();i++) {
			System.out.println(productList.get(i));
			UnitDetail = new Vector<String>();
			UnitDetail.add(String.valueOf(i+1));
			//a태그 유무 확인
			if(!(request.getParameter("menu").equals("manage") && productList.get(i).getProTranCode() != null)) {
				String aTagGetProductStart = "<a href='/getProduct.do?prodNo="+productList.get(i).getProdNo()+"&menu="+request.getParameter("menu")+"'>";
				
				UnitDetail.add(aTagGetProductStart + productList.get(i).getProdName() + aTagEnd);
			}
			else {
				UnitDetail.add(productList.get(i).getProdName());
			}
			UnitDetail.add(String.valueOf(productList.get(i).getPrice()));
			UnitDetail.add(String.valueOf(productList.get(i).getRegDate()));
			if(productList.get(i).getProTranCode() != null) {
				if(request.getParameter("menu").equals("manage")) {
					switch(productList.get(i).getProTranCode()) {
					case "1":
						String aTagUpdateTranCodeStart = "<a href=\"javascript:fncUpdateTranCodeByProd("+ resultPage.getCurrentPage() + "," + productList.get(i).getProTranCode() +");\">";
						UnitDetail.add("배송준비중&nbsp;" + aTagUpdateTranCodeStart + "배송 출발"+aTagEnd);
						break;
					case "2":
						UnitDetail.add("배송중");
						break;
					case "3":
						UnitDetail.add("거래완료");
						break;
					}
				}else {
					UnitDetail.add("재고없음");
				}
			
			}else {
				UnitDetail.add("판매중");
			}
			
			unitList.add(UnitDetail);
		}
		
		
		///출력을 위한 Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("title", title);
		resultMap.put("columList", columList);
		resultMap.put("unitList", unitList);
		
		/// M,V 연결
		request.setAttribute("map", resultMap);
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);

		return "forward:/product/listProduct.jsp";
	}

}
