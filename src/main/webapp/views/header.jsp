<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta charset="utf-8">
		
		<title>Mister Gifts</title>
		
		<link rel="icon" href="<c:url value="/resources/pictures/favicon.ico" />" />
		<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
		
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

        <style>
            #page {
                display: none;
            }

            #loading {
                display: block;
                position: absolute;
                top: 0;
                left: 0;
                z-index: 100;
                width: 100vw;
                height: 100vh;
                background-color: #245682;
                background-image: url('<c:url value="/resources/pictures/spin.gif" />');
                background-repeat: no-repeat;
                background-position: center;
            }
        </style>

        <script type="text/javascript">
            function onReady(callback) {
                var intervalID = window.setInterval(checkReady, 500);
                function checkReady() {
                    if (document.getElementsByTagName('body')[0] !== undefined) {
                        window.clearInterval(intervalID);
                        callback.call(this);
                    }
                }
            }

            function show(id, value) {
                document.getElementById(id).style.display = value ? 'block' : 'none';
            }

            onReady(function () {
                var lks = document.getElementsByTagName('a');

                for (var i = 0; i < lks.length; i++) {
                    var href = lks[i].href;
                    if (href.charAt(href.length - 1) != '#' && lks[i].target != '_blank') {
                        lks[i].addEventListener('click', function (e) {
                            show('page', false);
                            show('loading', true);
                        });
                    }
                }

                show('page', true);
                show('loading', false);
            });

        </script>
		
	</head>
	<body>

    <div id="loading"></div>

    <img class="rubban" src="<c:url value="/resources/pictures/rubban.png" />" style="z-index: 1000; position: absolute; top: 0; left: 0;" />

        <div id="wrap">

            <jsp:include page="menu.jsp"></jsp:include>