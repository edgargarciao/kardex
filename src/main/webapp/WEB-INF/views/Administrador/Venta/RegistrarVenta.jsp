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

	<script type="text/javascript">
    	var productos = [];
    	var cantidades = [];
    	
    	cargarPrecios();
    	cargarStock();
    	
    	
    	function cargarPrecios(){
    		var pre = document.getElementById("precios").value;
    		pre = pre.substring(1,pre.length-1);
    		if(pre != ""){
    			var pres = pre.split(",");
    			for (i = 0; i < pres.length; i++) { 
    				var cod = pres[i].split("=")[0].trim();
    				var pr = pres[i].split("=")[1].trim();
    				var producto = {codigo:cod, precio:pr};
    				productos.push(producto);
    			}
    		}
    		console.log(productos);
    		document.getElementById("bootstrap-data-table").deleteRow(1);
    	}
    
    	function cargarStock(){
    		var pre = document.getElementById("cantidades").value;
    		pre = pre.substring(1,pre.length-1);
    		if(pre != ""){
    			var pres = pre.split(",");
    			for (i = 0; i < pres.length; i++) { 
    				var cod = pres[i].split("=")[0].trim();
    				var pr = pres[i].split("=")[1].trim();
    				var producto = {codigo:cod, stock:pr};
    				cantidades.push(producto);
    			}
    		}
    		console.log(cantidades);
    	}
    
    	
		function agregarProducto(){
			var e = document.getElementById("producto");
			var productoId = e.options[e.selectedIndex].value;
			var nombre = e.options[e.selectedIndex].text;
			var cantidad = document.getElementById("cantidad").value;
									
			
			
			if(productoId > 0 && cantidad > 0){
				
				var result = $.grep(productos, function(e){ return e.codigo == productoId; });
				var resultCantidad = $.grep(cantidades, function(e){ return e.codigo == productoId; });
				if(Number(resultCantidad[0].stock) >= Number(cantidad)){
					crearItemEnTablaDeProductos(result[0].codigo,result[0].precio, cantidad,nombre);
					resultCantidad[0].stock = resultCantidad[0].stock - cantidad;
				}else{
					pintarRegistroNoExitoso("La cantidad disponible de '"+nombre+"' es " +resultCantidad[0].stock+" cantidad(es)","errorStock");
				}
			}
			
		}
		
		function crearItemEnTablaDeProductos(codigo, precio, cantidad,nombre){
		    var table = document.getElementById("bootstrap-data-table");
		    var random =  Math.floor(Math.random() * (10000 - 1000) + 1000);
 			var row = table.insertRow(table.rows.length);
		    
		    var cell0 = row.insertCell(0);
		    var cell1 = row.insertCell(1);
		    var cell2 = row.insertCell(2);
		    var cell3 = row.insertCell(3);
		    var cell4 = row.insertCell(4);
		    var cell5 = row.insertCell(5);
		    var cell6 = row.insertCell(6);
		    cell0.innerHTML = random;
		    cell1.innerHTML = nombre;
		    cell2.innerHTML = cantidad;
		    cell3.innerHTML = precio;
		    cell4.innerHTML = (precio*cantidad);
		    cell5.innerHTML = "<button type=\"button\" onclick=\"eliminarItem("+random+")\" class=\"btn btn-danger\"><i class=\"fa fa-trash-o\"></i>&nbsp;</button>";
		    cell6.innerHTML = codigo;
		    cell6.style.visibility = 'hidden'
		    modificarTotal();
		}
		
		function eliminarItem(indice){
		    var table = document.getElementById('bootstrap-data-table');
		    for (var r = 0, n = table.rows.length; r < n; r++) {
		    	if(table.rows[r].cells[0].innerHTML == indice){
		    		console.log(Number(table.rows[r].cells[6].innerHTML.trim()));
		    		var resultCantidad = $.grep(cantidades, function(e){ return e.codigo == Number(table.rows[r].cells[6].innerHTML.trim()) });
		    		resultCantidad[0].stock = resultCantidad[0].stock + Number(table.rows[r].cells[2].innerHTML.trim());
		    		
		    		document.getElementById("bootstrap-data-table").deleteRow(r);
		    		modificarTotal();
		    		break;
		    	}	
		    }
		}
		
		function modificarTotal(){
			var table = document.getElementById('bootstrap-data-table');
			var valor = 0;
		    for (var r = 1, n = table.rows.length; r < n; r++) {		    	
		    	valor = valor + Number(table.rows[r].cells[4].innerHTML.trim());		    		
		    }
		    
		    document.getElementById("totalsi").value =	(valor);
		    document.getElementById("iva").value = (valor*19/100);		    
		    document.getElementById("total").value = (((valor) + ((valor*19)/100)) + ((valor*2)/100));
		}
		
		function registrarVenta(){
			console.log("entro");
			var vendedor = document.getElementById("vendedor").value;
			//var cliente = document.getElementById("cliente").value;
			
			
			var totalsi = document.getElementById("totalsi").value;
			var iva = document.getElementById("iva").value;
			var total = document.getElementById("total").value;
			
			//if(vendedor > 0 && cliente > 0){
				
				if(vendedor > 0){
			
						var ruta = document.getElementById("ruta").value;
						
						var table = document.getElementById('bootstrap-data-table');
						var detallesVentas = [];
					    for (var r = 1, n = table.rows.length; r < n; r++) {
					    						    	 
					    	  
							var detalleVenta = {
									  producto: 		    { codigo :  Number(table.rows[r].cells[6].innerHTML.trim())},								
									  cantidad: 			Number(table.rows[r].cells[2].innerHTML.trim())
							};	
					    	  detallesVentas.push(detalleVenta);
					    }					

						var formData = {
								  vendedor: 		    {codigo:vendedor},								
								  detalles: 			detallesVentas,
						          total:				totalsi,
						          iva:					iva,
						          totalFactura:			total				
						};
						
						$.ajax({
							type : "POST",
							contentType : "application/json",
							url : ruta + "/servicios/guardarVenta",
							data: JSON.stringify(formData),
							success : function(result) {
								
								if(result.trim() == 'REGISTRO EXITOSO'){
									
									$('#formRealizarVenta').trigger("reset");
								    var table = document.getElementById('bootstrap-data-table');
								    for (var r = 1, n = table.rows.length; n > 1;) {
								    	
								    		document.getElementById("bootstrap-data-table").deleteRow(r);
								    		n = table.rows.length;								    										    		
								    }
									pintarRegistroExitoso();	
									document.getElementById("totalsi").value = 0;
									document.getElementById("iva").value = 0;
									document.getElementById("total").value = 0;
									window.location.href = ruta+ "/ventas";
								}else{
									pintarRegistroNoExitoso(result.trim(),"error");
								}
							},
							error : function(e) {
								pintarRegistroNoExitoso("Error en el sistema. Contacte al administrador.","error");
								console.log("ERROR: ", e);
							}
						});	
				
			}else{
				pintarRegistroNoExitoso("Campos invalidos.","error");
			}									
			
		}
		
		function pintarRegistroExitoso(){
			var exito = document.getElementById("exito");
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-success alert-dismissible fade show");
			var texto = document.createTextNode("Registro exitoso");       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			exito.appendChild(div);
		}
		
		function pintarRegistroNoExitoso(mensaje, lugar){
			var error = document.getElementById(lugar);
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-danger alert-dismissible fade show");
			var texto = document.createTextNode(mensaje);       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			error.appendChild(div);
		}
		
    </script>

</body>
</html>
