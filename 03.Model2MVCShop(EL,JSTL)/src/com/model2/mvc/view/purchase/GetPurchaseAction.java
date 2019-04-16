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
		
		//���Ź�� �ڵ忡 ���� String ��ȯ
		String paymentOption = null;
		switch(Integer.parseInt(purchase.getPaymentOption())) {
			case 0 :
				paymentOption = "���ݱ���";
				break;
			case 1 :
				paymentOption = "�ſ뱸��";
				break;
		}
		
		List<String> purchaseList = new ArrayList<String>();
		purchaseList.add("���� �� ��ȸ");
		purchaseList.add("��ǰ��ȣ,"+purchase.getPurchaseProd().getProdName());
		purchaseList.add("�����ھ��̵�,"+purchase.getBuyer().getUserId());
		purchaseList.add("���Ź��,"+paymentOption);
		purchaseList.add("�������̸�,"+purchase.getReceiverName());
		purchaseList.add("�����ڿ���ó,"+purchase.getReceiverPhone());
		purchaseList.add("�������ּ�,"+purchase.getDlvyAddr());
		purchaseList.add("���ſ�û����,"+purchase.getDlvyRequest());
		purchaseList.add("��������,"+purchase.getDlvyDate());
		purchaseList.add("�ֹ���,"+purchase.getOrderDate());
		
		request.setAttribute("list", purchaseList);
		request.setAttribute("purchase", purchase);
		
		System.out.println("getPurchaseAction�� getTranCode�� : "+purchase.getTranCode());
		
		return "forward:/purchase/getPurchase.jsp";
	}
}
