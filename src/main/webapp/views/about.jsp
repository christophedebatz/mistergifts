<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<jsp:include page="header.jsp"></jsp:include>
 
<div class="container">

	<img src="<c:url value="/resources/pictures/java.png" />" class="img-rounded" style="padding: 50px; float: right;" />

	<div class="jumbotron">

		<h2>A propos de MisterGifts</h2>
		<h3>Général</h3>
	
		<p>
			MisterGifts est une application permettant à un groupe de personnes de s'offrir des cadeaux de façon anonyme. Chaque personne présente une liste de ses souhaits et chaque autre personne peut réserver un cadeau à offrir. De ce fait, chacun recevra un cadeau qui lui plaira mais cela évite le ridque de doublon dans les cadeaux offerts.
		</p>
		
		<p>&nbsp;</p>
		
		<h3>Développeur</h3>
			
		<p>
			<b>Christophe de Batz</b>,<br />
			Ingénieur Efrei, promotion 2014 (mention SI)
		</p>
		
		<p>&nbsp;</p>
		
		<h3>Technique</h3>
		
		<p>
			<b>System</b><br />
			<a href="http://www.raspberrypi.org" target="_blank">RaspBian (Linux)</a>
		</p>
		
		<p>
			<b>Server</b><br />
			<a href="http://tomcat.apache.org/download-80.cgi" target="_blank">Apache Tomcat 8</a>
		</p>
		
		<p>
			<b>Language</b><br />
			<a href="http://www.oracle.com/technetwork/java/javaee/overview/index.html" target="_blank">Java EE 7</a>
		</p>
		
		<p>
			<b>Technologies</b><br />
			<a href="http://spring.io/" target="_blank">Spring 4</a> (MVC, Security)
		</p>
	
	</div>
</div>
	
<jsp:include page="footer.jsp"></jsp:include>