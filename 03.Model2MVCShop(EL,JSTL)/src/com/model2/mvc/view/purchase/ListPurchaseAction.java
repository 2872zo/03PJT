package com.model2.mvc.view.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService service = new PurchaseServiceImpl();
		User user = (User)request.getSession().getAttribute("user");

		int currentPage = 1;

		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} else if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));

		//DB 접속을 위한 search
		Search search = new Search();
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);

		//DB에 접속하여 결과를 Map으로 가져옴
		Map<String, Object> map = service.getPurchaseList(search, user.getUserId());

		//pageView를 위한 객체
		Page resultPage = new Page(currentPage, ((Integer) map.get("count")).intValue(),
				Integer.parseInt(getServletContext().getInitParameter("pageUnit")), pageSize);
		
		System.out.println("ListPurchaseAction-resultPage : " + resultPage);
		System.out.println("ListPurchaseAction-list.size() : " + ((List)map.get("list")).size());
		
		
		///title 설정
		String title = "구매 목록 조회";
		
		///colum 설정
		List<String> columList = new ArrayList<String>();
		columList.add("No");
		columList.add("회원ID");
		columList.add("회원명");
		columList.add("전화번호");
		columList.add("거래상태");
		columList.add("정보수정");
		
		///UnitList 설정
		List<List> unitList = new Vector<List>();
		List<String> UnitDetail = null;
		List<Purchase> purchaseList = (List<Purchase>)map.get("list");
		for(int i =0; i<purchaseList.size();i++) {
			UnitDetail = new Vector<String>();
			
			
			String aTag = "<a href='/getPurchase.do?tranNo="+purchaseList.get(i).getTranNo()+"'>";
			String aTagEnd = "</a>";
			UnitDetail.add(aTag + String.valueOf(i+1) + aTagEnd);
			
			String getUserTagStart = "<a href='/getUser.do?userId="+user.getUserId()+"'>";
			String getUserTagEnd = "</a>";
			UnitDetail.add(getUserTagStart + purchaseList.get(i).getBuyer().getUserId() + getUserTagEnd);
			
			UnitDetail.add(purchaseList.get(i).getReceiverName() != null? purchaseList.get(i).getReceiverName():"");
			UnitDetail.add(purchaseList.get(i).getReceiverPhone() != null? purchaseList.get(i).getReceiverPhone():"");
			
			//tranCode에 따른 상태값
			switch(purchaseList.get(i).getTranCode()) {
			case "0":
				UnitDetail.add("구매취소");
				break;
			case "1":
				UnitDetail.add("배송준비중");
				break;
			case "2":
				UnitDetail.add("배송중");
				break;
			case "3":
				UnitDetail.add("거래완료");
				break;
			}
			//배송중인 경우에만 수취완료 가능
			if(purchaseList.get(i).getTranCode().equals("2")) {
				UnitDetail.add("<a href='javascript:fncUpdatePurchaseCode(" + resultPage.getCurrentPage() + "," + purchaseList.get(i).getTranNo() + ");'>" + "수취확인</a>");
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
		request.setAttribute("resultPage", resultPage);

		return "forward:/purchase/listPurchase.jsp";
	}
}
