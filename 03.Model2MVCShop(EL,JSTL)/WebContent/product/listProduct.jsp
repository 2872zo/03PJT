<%@ page language="java"%>
<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 자신이 산 물건은 현재 상태 알수있게 -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>구매 목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	
	//검색 조건 Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("숫자만 가능합니다.")
			return;
		}
	}

	document.detailForm.submit();
}

function fncSortProductList(currentPage, sortCode){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	document.detailForm.sortCode.value = sortCode;
	
	//검색 조건 Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("숫자만 가능합니다.")
			return;
		}
	}

	document.detailForm.submit();
}

function fncHiddingEmptyStock(currentPage, hiddingEmptyStock){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	document.detailForm.hiddingEmptyStock.value = hiddingEmptyStock;
	
	//검색 조건 Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("숫자만 가능합니다.")
			return;
		}
	}

	document.detailForm.submit();
	
}



function fncUpdateTranCodeByProd(currentPage, prodNo){
	document.detailForm.currentPage.value = currentPage;
	document.detailForm.menu.value = "${param.menu}";
	
	//검색 조건 Validation Check
	if(document.detailForm.searchCondition.value != 1){
		if(isNaN(document.detailForm.searchKeyword.value)){
			alert("숫자만 가능합니다.");
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
			<a href="javascript:fncSortProductList(${resultPage.currentPage},0)">상품 번호 오름차순</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},1)">상품 번호 내림차순</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},2)">상품 이름 오름차순</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},3)">상품 이름 내림차순</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},4)">가격 낮은순</a>
			&nbsp;
			<a href="javascript:fncSortProductList(${resultPage.currentPage},5)">가격 높은순</a>
		</td>
	</tr>
	<tr>
		<td align="center">
			<a href="javascript:fncHiddingEmptyStock(${resultPage.currentPage},true)">재고없는 상품 숨기기</a>
			&nbsp;
			<a href="javascript:fncHiddingEmptyStock(${resultPage.currentPage},false)">재고없는 상품 보기</a>
		</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
