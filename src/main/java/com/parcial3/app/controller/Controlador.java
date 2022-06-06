package com.parcial3.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;


import com.parcial3.app.bda.IClientebda;

import com.parcial3.app.service.SendMailService;
import com.parcial3.app.variables.Cliente;

@Controller
@SessionAttributes("cliente")

public class Controlador {

	@Autowired
	private IClientebda clienteBda;
	@Autowired
	private SendMailService sendMailService;

	@RequestMapping("/mail")
	public String index() {
		return "mail";
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
			@RequestParam("telefono") String telefono, @RequestParam("correo") String correoReceptor,
			@RequestParam("mensaje") String mensaje) {
		String correoRemitente = "sazcarateleal@gmail.com";
		String cuerpoCorreo = "Datos del Cliente:\n\n" + "Nombre: " + nombre + " " + apellido + "\nTelefono: "
				+ telefono + "\nCorreo electronico: " + correoReceptor + "\n\nSolicitud:\n\n" + mensaje;
		try {
			sendMailService.sendMail(correoRemitente, correoReceptor, cuerpoCorreo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/listar";
	}

	@RequestMapping(value = { "/listar", "resultado" }, method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("cliente", clienteBda.findAll());
		return "listar";

	}

	@RequestMapping(value = { "/inicio", "/", "index" })
	public String index1(Model model) {
		model.addAttribute("titulo", "INYECTINTAS");
		return "inicio";
	}

	@RequestMapping(value = { "/form" })
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Gestion de Empleados");

		return "form";

	}

	@RequestMapping(value = { "/detallecliente/{id}", "/pdf/{id}" })
	public String detalleProducto(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteBda.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Certificados clientes");
		return "detallecliente";
	}

	@RequestMapping(value = { "/form/{id}", "/pdf/{id}" })
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteBda.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile imagen, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Cliente");
			return "form";
		}

		if(!imagen.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources//static/images");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			
			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompeta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompeta, bytesImg);
				
				cliente.setImagen(imagen.getOriginalFilename());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		clienteBda.saved(cliente);
		status.setComplete();
		return "redirect:/listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if (id > 0) {
			clienteBda.delete(id);

		}
		return "redirect:/listar";
	}
	

}
