<%@ page language="java"%>
<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- �ڽ��� �� ������ ���� ���� �˼��ְ� -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>���� ��� ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	
	//�˻� ���� Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("���ڸ� �����մϴ�.")
			return;
		}
	}

	document.detailForm.submit();
}

function fncSortProductList(currentPage, sortCode){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	document.detailForm.sortCode.value = sortCode;
	
	//�˻� ���� Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("���ڸ� �����մϴ�.")
			return;
		}
	}

	document.detailForm.submit();
}

function fncHiddingEmptyStock(currentPage, hiddingEmptyStock){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	document.detailForm.hiddingEmptyStock.value = hiddingEmptyStock;
	
	//�˻� ���� Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("���ڸ� �����մϴ�.")
			return;
		}
	}

	document.detailForm.submit();
	
}



function fncUpdateTranCodeByProd(currentPage, prodNo){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	
	//�˻� ���� Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("���ڸ� �����մϴ�.");
			return;
		}
	}
	
	var URI = "/updateTranCodeByProd.do?page=" + currentPage + "&menu=" + "${param.menu}" + "&prodNo=" + prodNo + "&tranCode=2";
	
	if("${empty search.searchKeyword}" != "true"){
		URI += "&searchCondition=" + "${search.searchCondition}" + "&searchKeyword=" + "${search.searchKeyword}";
	}
	
	location.href = URI;
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="listProduct.do?" method="post" onsubmit="return false">

<c:import url="../common/listPrinter.jsp">
	<c:param name="domainName" value="Product"/>
</c:import>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<input type="hidden" id="menu" name="menu" value=""/>
			<input type="hidden" id="sortCode" name="sortCode" value="${search.sortCode}"/>
			<input type="hidden" id="hiddingEmptyStock" name="hiddingEmptyStock" value="${search.hiddingEmptyStock}"/>
			
			<c:import url="../common/pageNavigator.jsp">
				<c:param name="domainName" value="Product"/>
			</c:import>	
		</td>
	</tr>
	<tr>
		<td align="center">
			<a href="javascript:fncSortProductList(${resultPage.currentPage},0)">��ǰ ��ȣ ��������</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},1)">��ǰ ��ȣ ��������</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},2)">��ǰ �̸� ��������</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},3)">��ǰ �̸� ��������</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},4)">���� ������</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},5)">���� ������</a>
		</td>
	</tr>
	<tr>
		<td align="center">
			<a href="javascript:fncHiddingEmptyStock(${resultPage.currentPage},true)">������ ��ǰ �����</a>
			&nbsp;
			<a href="javascript:fncHiddingEmptyStock(${resultPage.currentPage},false)">������ ��ǰ ����</a>
		</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>
