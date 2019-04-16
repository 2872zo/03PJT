package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {
	}

	public void insertProduct(Product product) throws Exception {
		// 1.DBUtil�� �̿��Ͽ� DB����
		Connection con = DBUtil.getConnection();

		// 2.DB�� ���� DML �ۼ� �� �� ����
		String sql = "INSERT INTO Product VALUES(seq_product_prod_no.NEXTVAL,?,?,?,?,?,SYSDATE)";
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println(product.getManuDate());
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate().replaceAll("\\-", ""));
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());

		// 3.����
		System.out.println("���� : " + stmt.executeUpdate());

		stmt.close();
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		// 1.DBUtil�� �̿��Ͽ� DB����
		Connection con = DBUtil.getConnection();

		// 2.DB�� ���� DML �ۼ� �� �� ����
		String sql = "SELECT"
				+ " p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code"
				+ " FROM product p, transaction t" + " WHERE p.prod_no = t.prod_no(+)" + " AND p.prod_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();

		// 3. ������� ProductVO�� ��Ƽ� ����
		Product product = null;
		if (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tran_status_code"));
		}

		stmt.close();
		rs.close();
		con.close();

		return product;
	}

	public HashMap<String, Object> getProductList(Search search) throws Exception {
		System.out.println("getProductList ����");
		// 1.DBUtil�� �̿��Ͽ� DB����
		Connection con = DBUtil.getConnection();

		// 2.DB�� ���� DML �ۼ� �� �� ����
		String sql = "SELECT " + " p.*, t.tran_status_code" + " FROM product p, transaction t"
				+ " WHERE p.prod_no = t.prod_no(+)";
		if (!CommonUtil.null2str(search.getSearchKeyword()).equals("")) {
			if (search.getSearchCondition().equals("0")) {
				sql += " AND p.prod_no = '" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " AND p.prod_name like '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " AND p.price = '" + search.getSearchKeyword() + "'";
			}
		}
		sql += " ORDER BY p.prod_no";
		
		System.out.println("search.getSearchKeyword() : " + CommonUtil.null2str(search.getSearchKeyword()));
		
		// 3. �� Row�� ����
		int totalUnit = getTotalCount(sql);
		System.out.println("��ǰ �� �ټ� : " + totalUnit);
		
		// 4. ��ȯ�� map���� �� �� Row���� ����
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(totalUnit));
		
		// 5. search�� ���� ���� ������ ������ ���� ����
		// ������ ������ ����Ʈ�� �ִ밪�� �Ѿ�� 1������ ���
		if(((search.getCurrentPage()-1)*search.getPageSize()+1) > totalUnit) {
			search.setCurrentPage(1);
		}
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		
		// 6. ResultSet�� Product�� list�� ����
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tran_status_code"));
			list.add(product);
		}

		System.out.println("list.size() : " + list.size());
		map.put("list", list);
		System.out.println("map.size() : " + map.size());

		stmt.close();
		rs.close();
		con.close();

		return map;
	}

	public void updateProduct(Product product) throws Exception {
		// 1.DBUtil�� �̿��Ͽ� DB����
		Connection con = DBUtil.getConnection();

		System.out.println("update �� CommonUtil.toDateStr(product.getManuDate()) : " + CommonUtil.toDateStr(product.getManuDate()));
		
		// 2.DB�� ���� DML �ۼ� �� �� ����
		String sql = "UPDATE product set prod_name = ?, prod_detail = ?, manufacture_day =?, price = ?, image_file = ? WHERE prod_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate().replaceAll("-", ""));
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());

		System.out.println("updateProduct�� ����� �� �� : " + stmt.executeUpdate());

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

	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
				" 	FROM (	"+sql+" ) inner_table "+
				"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();

		System.out.println("UserDAO :: make SQL :: "+ sql);	

		return sql;
	}
}
