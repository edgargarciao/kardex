
<!-- 
		Se crea una variable con la url actual 
		debido a que esta puede cambiar segun la 
		direccion de despligue EJE= "https://todouno.kardex.com"
	-->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<!-- Left Panel -->

<aside id="left-panel" class="left-panel ">
	<nav class="navbar navbar-expand-sm navbar-default ">

		<div class="navbar-header ">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#main-menu" aria-controls="main-menu"
				aria-expanded="false" aria-label="Toggle navigation">
				<i class="fa fa-bars"></i>
			</button>
			<a class="navbar-brand " href="./"><img
				src="resources/images/logoOscuro.png" alt="Logo"></a> 
		</div>

		<div id="main-menu" class="main-menu collapse navbar-collapse ">
			<ul class="nav navbar-nav">
				<li><a href="indexAdmin"> <i
						class="menu-icon fa fa-dashboard"></i>Panel de control
				</a></li>
				<h3 class="menu-title">Negocio</h3>
				<!-- /.menu-title -->
				<li><a href="${contextPath}/tipoProductos"> <i
						class="menu-icon fa fa-reorder"></i>Tipos de producto
				</a></li>
				<li><a href="${contextPath}/productos"> <i class="menu-icon ti-list-ol"></i>Productos
				</a></li>
				<li><a href="${contextPath}/vendedores"> <i
						class="menu-icon ti-user"></i>Vendedores
				</a></li>
				<li><a href="${contextPath}/ventas"> <i class="menu-icon fa fa-shopping-bag"></i>Ventas
				</a></li>				
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
</aside>
<!-- /#left-panel -->

<!-- Left Panel -->