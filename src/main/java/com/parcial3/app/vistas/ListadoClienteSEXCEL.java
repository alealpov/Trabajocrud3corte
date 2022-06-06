package com.parcial3.app.vistas;



import java.util.List;
import java.util.Map;

 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.parcial3.app.variables.Cliente;





@Component("listar.xlsx")
public class ListadoClienteSEXCEL extends AbstractXlsxView{

	
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachent; filename= \"listado-clientes.xlsx\"");
		Sheet hoja = workbook.createSheet("Clientes");
		
		Row filaTitulo = hoja.createRow(0);
		Cell celda = filaTitulo.createCell(0);
		celda.setCellValue("LISTADO GENERAL DE EMPLEADOS");
		Row filaData = hoja.createRow(2);
		String[] columnas = {"ID","NOMBRES","APELLIDOS","CORREO","FECHA","DOCUMENTO","ESTUDIOS","IMAGEN"};
		
		
		for (int i = 0; i < columnas.length; i++) {
			celda= filaData.createCell(i);
			celda.setCellValue(columnas[i]);
		}
	List <Cliente>listaC=(List<Cliente>) model.get("cliente");
	
	int numFila=3;
	
	CellStyle dataStyle = workbook.createCellStyle();
	dataStyle.setDataFormat((short)14);

	hoja.setColumnWidth(7, 3000);
	hoja.setColumnWidth(6, 3000);
	hoja.setColumnWidth(5, 7000);
	hoja.setColumnWidth(4, 3000);
	hoja.setColumnWidth(3, 7000);
	hoja.setColumnWidth(2, 3000);
	hoja.setColumnWidth(1, 3000);

	
	for (Cliente cliente : listaC) {
	
		filaData= hoja.createRow(numFila);
		filaData.createCell(0).setCellValue(cliente.getId());
		filaData.createCell(1).setCellValue(cliente.getNombre());
		filaData.createCell(2).setCellValue(cliente.getApellido());
		filaData.createCell(3).setCellValue(cliente.getMail());
		filaData.createCell(5).setCellValue(cliente.getEstudios());
		filaData.createCell(6).setCellValue(cliente.getCertificado());
		filaData.createCell(7).setCellValue(cliente.getImagen());
		
		
		Cell cell = filaData.createCell(4);
		cell.setCellValue(cliente.getFecha());
		cell.setCellStyle(dataStyle);
	
		numFila ++;
		
	}
	
	}

}

