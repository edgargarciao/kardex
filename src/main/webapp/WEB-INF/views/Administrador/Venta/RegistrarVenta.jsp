<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<%@ include file="../General/css.jsp"%>
</head>
<body>
	<!-- Left Panel -->

	<%@ include file="../General/LeftPanel.jsp"%>

	<!-- /#left-panel -->

	<!-- Right Panel -->

	<div id="right-panel" class="right-panel">


		<!-- Header-->
		<header id="header" class="header">

			<div class="header-menu">
				<div class="col-sm-9">
					<a id="menuToggle" class="menutoggle pull-left"><i
						class="fa fa fa-tasks"></i></a>
					<div class="page-header float-left">
						<div class="page-title">
							<ol class="breadcrumb text-right">
								<li><a href="${contextPath}/indexAdmin">Panel de
										control</a></li>
								<li><a href="${contextPath}/ventas">Ventas </a></li>
								<li class="active"><a href="#"> Registrar venta</a></li>
							</ol>
						</div>
					</div>
				</div>
				<%@ include file="../General/Configuracion.jsp"%>
			</div>

		</header>
		<!-- /header -->
		<!-- Header-->


		<!-- Contenedor del formulario -->
		<div class="content mt-3">
			<div class="animated fadeIn">
				<div class="row">
					<div class="col-md-12">

						<div class="card">
							<!-- Titulo de la ventana -->
							<div class="card-header">
								<strong class="card-title">Registrar venta</strong>
							</div>
							<div class="card-body">
								<!-- muestra los mensajes -->
								<div id="exito"></div>
								<div id="error"></div>
								<!-- Si hubo un error en el registro muestra el mensaje-->
								<c:if test="${not empty wrong}">
									<div
										class="sufee-alert alert with-close alert-danger alert-dismissible fade show">
										<c:out value='${wrong}' />
										<button type="button" class="close" data-dismiss="alert"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
								</c:if>

								<!-- Formulario -->
								<form:form id="formVenta" action="guardarVenta" method="post"
									modelAttribute="venta" enctype="multipart/form-data">

									<div class="card">
										<div class="card-header">
											<strong class="card-title">Datos generales de la
												venta</strong>
										</div>

										<input type="hidden" id="ruta" class="form-control"
											value="${contextPath}" />

										<div class="card-body">
											<input type="hidden" id="ruta" class="form-control"
												value="${contextPath}" />
											<!-- Si hubo un error en el registro muestra el mensaje-->
											<div id="exito"></div>
											<div id="error"></div>


											<div class="form-group">
												<label for="email-input" class=" form-control-label">Seleccione
													el vendedor</label>
												<form:select path="vendedor.codigo" id="vendedor"
													class="form-control">
													<form:option value="0" label="Seleccione el vendedor" />
													<form:options items="${vendedores}" />
												</form:select>
											</div>


										</div>
									</div>
									<!-- END CARD -->

									<div class="card">
										<div class="card-header">
											<strong class="card-title">Productos</strong>
										</div>

										<div class="card-body">


											<div id="errorStock"></div>



											<input type="hidden" id="precios" value="${precioproductos}">
											<input type="hidden" id="cantidades"
												value="${cantidadproductos}">

											<!-- Insertar producto -->



											<div class="form-group">
												<label for="email-input" class=" form-control-label">Seleccione
													el producto a agregar</label>
												<form:select path="producto" id="producto"
													class="form-control">
													<form:option value="0" label="Seleccione el producto" />
													<form:options items="${productos}" />
												</form:select>
											</div>
											<div class="form-group">
												<label for="email-input" class=" form-control-label">Cantidad</label>
												<input type="number" min="1" id="cantidad"
													class="form-control" placeholder="3" aria-invalid="false" />
											</div>

											<!-- Boton para Eliminar los datos -->
											<button type="button" class="btn btn-success"
												onclick="agregarProducto()">Agregar producto</button>
											</br> </br>
											<!--  Productos -->

											<table id="bootstrap-data-table"
												class="table table-striped table-bordered">
												<thead>
													<tr>
														<th>Codigo</th>
														<th>Producto</th>
														<th>Cantidad</th>
														<th>Precio unitario</th>
														<th>precio total</th>
														<th>acción</th>
													</tr>
												</thead>

												<tbody id="bodyproductos">



												</tbody>
											</table>
										</div>
									</div>

									<!-- END CARD -->

									<div class="card">
										<div class="card-header">
											<strong class="card-title">Total</strong>
										</div>

										<div class="card-body">
											<!-- Total  -->

											<div class="row">
												<div class="col-lg-3">

													<div class="form-group">
														<label for="email-input"
															style="display: block; text-align: center;"
															class=" form-control-label">Total sin impuesto</label> <input
															id="totalsi" class="form-control" placeholder="0"
															aria-invalid="false" required="true"
															value="${categoria.nombre}" readonly />
													</div>

												</div>
												<div class="col-lg-3">

													<div class="form-group">
														<label for="email-input"
															style="display: block; text-align: center;"
															class=" form-control-label">Iva (19%)</label> <input
															id="iva" class="form-control" placeholder="0"
															aria-invalid="false" required="true"
															value="${categoria.nombre}" readonly />
													</div>

												</div>
												<div class="col-lg-3">

													<div class="form-group">
														<label for="email-input"
															style="display: block; text-align: center;">Total</label>
														<input id="total" class="form-control" placeholder="0"
															aria-invalid="false" required="true"
															value="${categoria.descripcion}" readonly />
													</div>

												</div>
											</div>

										</div>
									</div>

									<!-- Boton para registrar los datos -->
									<button type="button" class="btn btn-success"
										onclick="registrarVenta()">Realizar venta</button>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- .animated -->
		</div>
		<!-- .content -->


	</div>
	<!-- /#right-panel -->

	<!-- Right Panel -->


	<!-- Carga de los archivos Javascript -->
	<%@ include file="../General/scripts.jsp"%>
	
	<script src="resources/assets/js/server/venta.js"></script>
	

</body>
</html>
