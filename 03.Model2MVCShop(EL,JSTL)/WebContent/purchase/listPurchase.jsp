<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		document.detailForm.currentPage.value = currentPage;
		document.detailForm.menu.value =  "${param.menu}";
		
		document.detailForm.submit();
	}
	
	function fncUpdatePurchaseCode(currentPage,tranNo){		
		var URI = "/updateTranCode.do?page="+currentPage+"&tranNo="+tranNo+"&tranCode=3";
		
		console.log(URI);
		
		location.href = URI;
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<c:import url="../common/listPrinter.jsp">
	<c:param name="domainName" value="Purchase"/>
</c:import>

<%-- 
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	
	<c:forEach begin="0" end="${fn:length(list)-1}" step="1" var="i">
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/getPurchase.do?tranNo=${list[i].tranNo}">${i+1}</a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=${list[i].buyer.userId}">${list[i].buyer.userId}</a>
		</td>
		<td></td>
		<td align="left">${list[i].receiverName}</td>
		<td></td>
		<td align="left">${list[i].receiverPhone}</td>
		<td></td>
		<td align="left">현재
			<c:choose>
				<c:when test="${list[i].tranCode eq '1'}">배송준비중	</c:when>
				<c:when test="${list[i].tranCode eq '2'}">배송중</c:when>
				<c:when test="${list[i].tranCode eq '3'}">거래완료</c:when>
				<c:otherwise>재고없음</c:otherwise>
			</c:choose>
				상태 입니다.</td>
		<td></td>
		<c:if test="${list[i].tranCode eq '2'}">	
			<td align="left">
				<a href="javascript:fncUpdatePurchaseCode(${resultPage.currentPage},${list[i].tranNo})">수취확인</a>
			</td>
		</c:if>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
	
</table>

--%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>
		 <input type="hidden" id="menu" name="menu" value=""/>
		
		 <c:import url="../common/pageNavigator.jsp">
			<c:param name="domainName" value="Purchase"/>
		</c:import>	
			
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>