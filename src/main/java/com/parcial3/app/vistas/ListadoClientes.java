package com.parcial3.app.vistas;


import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.parcial3.app.variables.Cliente;

@Component("listar") 

public class ListadoClientes extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		@SuppressWarnings("unchecked")
		List<Cliente> listadoClientes = (List<Cliente>) model.get("cliente");
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.open();
		
		PdfPTable tablaTitulo = new PdfPTable(1);
		PdfPCell celda = null;
		celda =new PdfPCell(new Phrase("Listado Clientes"));
		
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		tablaTitulo.addCell(celda);
		tablaTitulo.setSpacingAfter(30);

		
		PdfPTable tablaClientes = new PdfPTable(7);
		
		listadoClientes.forEach(cliente ->{
			
			tablaClientes.addCell(cliente.getId().toString());
			tablaClientes.addCell(cliente.getNombre());
			tablaClientes.addCell(cliente.getApellido());
			tablaClientes.addCell(cliente.getMail());
			
			tablaClientes.addCell(cliente.getCertificado());
			tablaClientes.addCell(cliente.getEstudios());
			tablaClientes.addCell(cliente.getImagen());
		
						
		});
		
		document.add(tablaTitulo);
		document.add(tablaClientes);
		
	}
}

