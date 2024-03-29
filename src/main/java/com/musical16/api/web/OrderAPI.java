package com.musical16.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musical16.dto.order.OrderDTO;
import com.musical16.dto.request.InputOrderAdmin;
import com.musical16.dto.request.InputOrderDTO;
import com.musical16.service.IOrdersService;

@RestController
public class OrderAPI {

	@Value("${jpa.page.limit}")
	private Integer LIMIT_ITEM;
	
	@Autowired
	private IOrdersService ordersService;
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/order")
	public List<OrderDTO> findAll(HttpServletRequest req){
		return ordersService.findAll(req);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/order/{id}")
	public ResponseEntity<?> findOne(HttpServletRequest req, @PathVariable("id") Long id){
		return ordersService.findAll(req, id);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/order")
	public ResponseEntity<?> createOrder(HttpServletRequest req) {
		return ordersService.createOrder(req);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/order")
	public ResponseEntity<?> createOrder(HttpServletRequest req, @RequestBody @Valid InputOrderDTO order) {
		return ordersService.createOrder(req,order);
	}
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/order/{id}")
	public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id,HttpServletRequest req) {
		return ordersService.cancelOrder(id,req);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	@GetMapping("/manageOrder")
	public List<OrderDTO> showAll(){
		return ordersService.findAll();
	}
	
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	@GetMapping("/manageOrder/{id}")
	public ResponseEntity<?> showOne(@PathVariable("id")Long id){
		return ordersService.findOne(id);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	@PutMapping("/manageOrder")
	public ResponseEntity<?> updateOrder(HttpServletRequest req, @Valid @RequestBody InputOrderAdmin order) {
		return ordersService.updateOrder(req,order);
	}
	
}
