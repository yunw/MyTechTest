<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="../../../js/ext-4.0/resources/css/ext-all.css">
<script type="text/javascript" src="../../../js/ext-4.0/ext-debug.js"></script>
<script type="text/javascript">
	Ext.onReady(function() {

		function showwin() {//弹出windows的方法
			var win = new Ext.Window({
				id : 'win',
				name : 'win',
				width : 500,
				height : 400,
				items : {
					html : '<img src="/TechTest/freechart/WinServlet/" />'
				}
			});
			win.show();
		}

		var panel1;
		//页面下方生成的panel方法
		function showpanel1() {
			//判断是否存在panel
			if (Ext.getCmp("panel1") != null) {
				Ext.getCmp("panel1").destroy("panel1");//如果有就先remove掉panel，然后再新建
			}
			panel1 = Ext.create('Ext.Panel', {
				id : 'panel1',
				name : 'panel1',
				renderTo: Ext.getBody(),
				items : {
					html : '<img src="/TechTest/freechart/WinServlet/"/>'
				}
			});
		}
		
		//定义两个按钮
		Ext.create('Ext.Button', {
			id : 'showwindow',
			text : '弹出window',
			renderTo: Ext.getBody(),
			handler : function() {
				showwin();
			}
		});
		
		Ext.create('Ext.Button', {
			id : 'showpanel',
			text : '本页面生成图片',
			renderTo: Ext.getBody(),
			//handler : function() {
			//	showpanel1();
			//},
			listeners: {
		        click: function() {
		            //this == the button, as we are in the local scope
		            //this.setText('I was clicked!');
		        	showpanel1();
		        }
		    }

		});
		
	});
</script>
</head>
<body>
</body>
</html>