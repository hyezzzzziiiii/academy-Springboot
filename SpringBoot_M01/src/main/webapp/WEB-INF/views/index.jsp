<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/headerfooter01/header.jsp" %>

<!--메인 이미지 들어가는 곳 시작 --->
 <div id="main_img"><img src="images/main_img.jpg" 
    			style="border-radius:20px 20px 20px 20px;border:2px solid white;"></div>
<!--메인 이미지 들어가는 곳 끝--->
  <div id="front"><h2> New Item</h2>
  	<div id="bestProduct">         
      <c:forEach items="${newProductList }"  var="productVO">
        <div id="item"><a href="productDetail?pseq=${productVO.pseq}">
			<img src="product_images/${productVO.image}" />
            <h3> ${productVO.name} - 
            <fmt:formatNumber value="${productVO.price2}" 
            	type="currency"></fmt:formatNumber>
            </h3></a></div>
      </c:forEach></div>
   </div>
   <div class="clear"></div>
   <div id="front"><h2> Best Item</h2><div id="bestProduct">         
        <c:forEach items="${bestProductList}"  var="productVO">
          <div id="item"><a href="productDetail?pseq=${productVO.pseq}">
			<img src="product_images/${productVO.image}" />
           <h3> ${productVO.name} - 
           <fmt:formatNumber value="${productVO.price2}" 
           		type="currency"></fmt:formatNumber>
            </h3></a></div>
    	</c:forEach></div>
  		<div class="clear"></div>
  </div>

<%@ include file="include/headerfooter01/footer.jsp" %> 