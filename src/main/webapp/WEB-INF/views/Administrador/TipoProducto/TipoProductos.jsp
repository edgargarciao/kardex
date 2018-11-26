<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

	<!--/ Left Panel -->

	<!-- Right Panel -->

	<div id="right-panel" class="right-panel">

		<!-- Header-->
		<header id="header" class="header">

			<div class="header-menu">
				<div class="col-sm-9">
					<a id="menuToggle" class="menutoggle pull-left"> <i
						class="fa fa fa-tasks"> </i>
					</a>
					<div class="page-header float-left">
						<div class="page-title">
							<ol class="breadcrumb text-right">
								<li><a href="${contextPath}/indexAdmin">Panel de
										control</a></li>
								<li><a href="#">Tipos de
										producto </a></li>
							</ol>
						</div>
					</div>
				</div>
				<%@ include file="../General/Configuracion.jsp"%>
			</div>

		</header>
		<!-- /header -->


		<!-- Div content 3 -->
		<div class="content mt-3">
			<!-- Div animated -->
			<div class="animated fadeIn">
				<!-- Row -->
				<div class="row">
					<!-- Col 12 -->
					<div class="col-md-12">
						<!-- Card -->
						<div class="card">
							<div class="card-header">
								<strong class="card-title">Tipos de productos</strong>
							</div>
							<div class="card-body">
								<!-- /Card -->

								<!-- Si hubo un registro exitoso muestra el mensaje-->
								<c:if test="${not empty result}">
									<div
										class="sufee-alert alert with-close alert-success alert-dismissible fade show">
										<c:out value='${result}' />
										<button type="button" class="close" data-dismiss="alert"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
								</c:if>

								<!-- Boton que indica la accion para registrar una actividad -->
								<a href="registrarTipoProducto" class="btn btn-success">Registrar
									tipo de producto</a> <br> <br>
								<!-- Tabla con las actividades -->
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th scope="col" style="width: 40%">Nombre</th>
											<th scope="col" style="width: 40%">Descripcion</th>
											<th scope="col" style="width: 20%">Acción</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="tipoProducto" items="${tipoProductos}">
											<tr>
												<td scope="row">${tipoProducto.nombre}</td>
												<td scope="row">${tipoProducto.descripcion}</td>
												<th style="display: none">${tipoProducto.codigo}</th>
												<td><a
													href="${contextPath}/actualizarTipoProducto?id=${tipoProducto.codigo}">
														<button class="btn btn-outline-primary">
															<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
														</button>
												</a> <a
													href="${contextPath}/eliminarTipoProducto?id=${tipoProducto.codigo}">
														<button class="btn btn-outline-danger">
															<i class="fa fa-trash" aria-hidden="true"></i>
														</button>
												</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- /Tabla -->
							</div>
							<!-- /card-body -->
						</div>
						<!-- /card -->
					</div>
					<!-- /Col 12 -->
				</div>
				<!-- /Row -->
			</div>
			<!-- .animated -->
		</div>
		<!-- .content -->
	</div>
	<!-- /#right-panel -->

	<!-- Right Panel -->

	<!-- Carga de los archivos Javascript -->
	<%@ include file="../General/scripts.jsp"%>

</body>
</html>
