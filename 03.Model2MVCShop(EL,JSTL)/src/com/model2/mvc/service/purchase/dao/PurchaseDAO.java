package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
public class PurchaseDAO {

	public PurchaseDAO() {
	}

	// ���� ���� ����ȸ
	public Purchase findPurchase(int tranNo) throws Exception {
		System.out.println("::PurchaseDAO-find1 ����");
		Connection con = DBUtil.getConnection();

		String sql = "SELECT" + 
				" t.*" + 
				" FROM transaction t" + 
				" WHERE t.tran_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();
		Purchase purchase = null;
		if (rs.next()) {
			User user = new User();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			
			Product product = new Product();
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			
			purchase = new Purchase();
			purchase.setTranNo(tranNo);
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDlvyAddr(rs.getString("demailaddr"));// dlvy_addr
			purchase.setDlvyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setDlvyDate((rs.getString("dlvy_date") != null) ? rs.getString("dlvy_date").substring(0, 10)
					: rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));// order_date
		}

		stmt.close();
		rs.close();
		con.close();

		System.out.println("::PurchaseDAO-find1 ��");
		return purchase;
	}

	public Purchase findPurchase2(int prodNo) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "SELECT" + 
				" t.*" + 
				" from transaction t" + 
				" where t.prod_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		Purchase purchase = null;
		if (rs.next()) {
			User user = new User();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			
			Product product = new Product();
			product = new Product();
			product.setProdNo(prodNo);
			
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDlvyAddr(rs.getString("demailaddr"));// dlvy_addr
			purchase.setDlvyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setDlvyDate((rs.getString("dlvy_date") != null) ? rs.getString("dlvy_date").substring(0, 10)
					: rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));// order_date
		}

		stmt.close();
		rs.close();
		con.close();

		return purchase;
	}

	// ���� ��� ��ȸ
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("::getPurchaseList ����");
		// 1.DBUtil�� �̿��Ͽ� DB����
		Connection con = DBUtil.getConnection();

		// 2.DB�� ���� DML �ۼ� �� �� ����
		String sql = "SELECT" + 
				" u.user_id, p.prod_name, t.*" + 
				" from users u, product p, transaction t" + 
				" where t.prod_no = p.prod_no" + 
				" AND u.user_id = t.buyer_id" +
				" AND t.buyer_id = '" + buyerId + "'";

		// 3. �� Row�� ����
		int totalUnit = getTotalCount(sql);
		System.out.println("���� ��� �� ���� : " + totalUnit);

		// 4. ��ȯ�� map���� �� �� Row���� ����
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(totalUnit));

		// 5. search�� ���� ���� ������ ������ ���� ����
		// ������ ������ �������� ����Ʈ�� �ִ밪�� �Ѿ�� 1������ ���
		if (((search.getCurrentPage() - 1) * search.getPageSize() + 1) > totalUnit) {
			search.setCurrentPage(1);
		}
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		// 6. ResultSet�� Product�� list�� ����
		List<Purchase> list = new ArrayList<Purchase>();
		Purchase purchase = null;
		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));

			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDlvyAddr(rs.getString("demailaddr"));// dlvy_addr
			purchase.setDlvyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setDlvyDate((rs.getString("dlvy_date") != null) ? rs.getString("dlvy_date").substring(0, 10) : rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			list.add(purchase);
		}

		map.put("list", list);
		map.put("count", totalUnit);

		stmt.close();
		rs.close();
		con.close();

		return map;
	}

	// �ǸŸ�� ����
	public HashMap<String, Object> getSaleList(Search search) throws Exception {

		return null;
	}

	// ����
	public void insertPurchase(Purchase Purchase) throws Exception {
		System.out.println("::insertPurchase ����");
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO transaction VALUES(seq_transaction_tran_no.NEXTVAL, ?,?,?,?,?,?,?,?,sysdate,?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, Purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, Purchase.getBuyer().getUserId());
		stmt.setString(3, Purchase.getPaymentOption());
		stmt.setString(4, Purchase.getReceiverName());
		stmt.setString(5, Purchase.getReceiverPhone());
		stmt.setString(6, Purchase.getDlvyAddr());
		stmt.setString(7, Purchase.getDlvyRequest());
		stmt.setString(8, Purchase.getTranCode());
		stmt.setString(9, Purchase.getDlvyDate());

		System.out.println("insertPurchase : " + stmt.executeUpdate());

		stmt.close();
		con.close();
	}

	// ���� ���� ����
	public void updatePurchase(Purchase Purchase) throws Exception {
		System.out.println("::updatePurchase ����");
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction" + " SET"
				+ " payment_option = ?, receiver_name = ?, receiver_phone = ?, demailaddr = ?,"
				+ " dlvy_request = ?, dlvy_date = ?" + " WHERE tran_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, Purchase.getPaymentOption());
		stmt.setString(2, Purchase.getReceiverName());
		stmt.setString(3, Purchase.getReceiverPhone());
		stmt.setString(4, Purchase.getDlvyAddr());
		stmt.setString(5, Purchase.getDlvyRequest());
		stmt.setString(6, Purchase.getDlvyDate());
		stmt.setInt(7, Purchase.getTranNo());

		System.out.println("uptdate�� Purchase : " + Purchase);

		System.out.println("UpdatePurchase�� ����� �� �� : " + stmt.executeUpdate());

		stmt.close();
		con.close();
	}

	// �Ǹ� ���� �ڵ� ����
	public void updateTranCode(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET tran_status_code = ?";
		
		if(purchase.getTranNo() != 0) {
			sql += " WHERE tran_no = ?";
		}else if(purchase.getPurchaseProd().getProdNo() != 0) {
			sql += " WHERE prod_no = ?";
		}

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, purchase.getTranCode());
		if(purchase.getTranNo() != 0) {
			stmt.setInt(2, purchase.getTranNo());
		}else if(purchase.getPurchaseProd().getProdNo() != 0) {
			stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		}
		

		System.out.println("Update�� ����� �� �� : " + stmt.executeUpdate());

		stmt.close();
		con.close();
	}

	// �Խ��� Page ó���� ���� ��ü Row(totalCount) return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// �Խ��� currentPage Row �� return
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}
}
