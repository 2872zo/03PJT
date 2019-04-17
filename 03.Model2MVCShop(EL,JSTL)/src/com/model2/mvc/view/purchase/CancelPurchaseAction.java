package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class CancelPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setTranCode("0");
		
		purchaseService.updateTranCode(purchase);
		
		return "redirect:/listPurchase.do";
	}

}
