
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
		