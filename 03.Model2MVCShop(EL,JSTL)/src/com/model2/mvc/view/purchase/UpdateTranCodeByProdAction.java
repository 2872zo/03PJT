package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService purchaseService = new PurchaseServiceImpl();

		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdName(request.getParameter("prodNo"));
		purchase.setPurchaseProd(product);
		purchase.setTranCode(request.getParameter("tranCode"));

		purchaseService.updateTranCode(purchase);

		String targetURI ="redirect:/listProduct.do?page=" + request.getParameter("page") + "&menu=" + request.getParameter("menu");
		if(request.getParameter("searchCondition")!=null) {
			targetURI += "&searchCondition=" + request.getParameter("searchCondition");
			targetURI += "&searchKeyword=" + request.getParameter("searchKeyword");
		}
		
		System.out.println("UpdateByProdAction - targetURI : " + targetURI);
		
		return  targetURI;
	}

}
