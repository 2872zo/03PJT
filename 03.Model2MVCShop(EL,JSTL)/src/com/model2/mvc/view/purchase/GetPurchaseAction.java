package com.model2.mvc.view.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		//구매방법 코드에 따른 String 반환
		String paymentOption = null;
		switch(Integer.parseInt(purchase.getPaymentOption())) {
			case 0 :
				paymentOption = "현금구매";
				break;
			case 1 :
				paymentOption = "신용구매";
				break;
		}
		
		List<String> purchaseList = new ArrayList<String>();
		purchaseList.add("구매 상세 조회");
		purchaseList.add("물품번호,"+purchase.getPurchaseProd().getProdName());
		purchaseList.add("구매자아이디,"+purchase.getBuyer().getUserId());
		purchaseList.add("구매방법,"+paymentOption);
		purchaseList.add("구매자이름,"+purchase.getReceiverName());
		purchaseList.add("구매자연락처,"+purchase.getReceiverPhone());
		purchaseList.add("구매자주소,"+purchase.getDlvyAddr());
		purchaseList.add("구매요청사항,"+purchase.getDlvyRequest());
		purchaseList.add("배송희망일,"+purchase.getDlvyDate());
		purchaseList.add("주문일,"+purchase.getOrderDate());
		
		request.setAttribute("list", purchaseList);
		request.setAttribute("purchase", purchase);
		
		System.out.println("getPurchaseAction의 getTranCode값 : "+purchase.getTranCode());
		
		return "forward:/purchase/getPurchase.jsp";
	}
}
