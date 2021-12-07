package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CategoryEntity;
import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.converter.ProductConverter;
import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.request.InputProduct;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.CategoryRepository;
import com.musical16.repository.ImageRepository;
import com.musical16.repository.OriginRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.service.IHelpService;
import com.musical16.service.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Value(value = "${jpa.page.limit}")
	private int PAGE_LIMIT;
	
	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private OriginRepository originRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private IHelpService helpService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public Page<ProductDTO> search(String key, Integer page) {
		Page<ProductDTO> result = new Page<>();
		List<ProductDTO> list = new ArrayList<>();
		Integer index ;
		try {
			if(page<=1) {
				index = 1;
			}else {
				index = page;
			}
		} catch (NullPointerException e) {
			index = 1;
		}
		Pageable pageable = new PageRequest(index-1, PAGE_LIMIT, Direction.ASC, "id");
		for(ProductEntity each : productRepository.search(key, pageable)) {
			list.add(productConverter.toDTO(each));
		}
		result.setPage(index);
		result.setTotalPage((int)Math.ceil((double) productRepository.search(key).size()/PAGE_LIMIT));
		result.setList(list);
		return result;
	}
	
	@Override
	public Page<ProductDTO> findAll(Long id, Integer page, String[] sort) {
		Page<ProductDTO> result = new Page<>();
		Integer index = null;
		List<Order> listorders = new ArrayList<>();
		try {
			if(page<=0||page==null) {
				index = 1;
			}else {
				index=page;
			}
		} catch (NullPointerException e) {
			index = 1;
		}
		try {
			for(String each : sort) {
				if(each.equals("gia-thap-den-cao")) {
					listorders.add(new Order(Direction.ASC, "price"));
				}else if(each.equals("gia-cao-den-thap")) {
					listorders.add(new Order(Direction.DESC, "price"));
				}else if(each.equals("z-a")) {
					listorders.add(new Order(Direction.DESC, "name"));
				}else if(each.equals("a-z")) {
					listorders.add(new Order(Direction.ASC, "name"));
				}
			}
		} catch (NullPointerException e) {
			listorders.add(new Order(Direction.DESC, "id"));
		}
		
		Sort sorts = new Sort(listorders);
		Pageable pageable = new PageRequest(index -1, PAGE_LIMIT, sorts);
		org.springframework.data.domain.Page<ProductEntity> listEntity = null;
		List<ProductDTO> list = new ArrayList<>();
		
		if(id!=null) {
			CategoryEntity category = categoryRepository.findOne(id);
			listEntity = productRepository.findByCategories(category, pageable);
			result.setTotalPage((int) Math.ceil((double) productRepository.findByCategories(category).size()/PAGE_LIMIT));
		}else {
			listEntity = productRepository.findAll(pageable);
			result.setTotalPage((int) Math.ceil((double) productRepository.count()/PAGE_LIMIT));
		}
		for(ProductEntity each : listEntity) {
			list.add(productConverter.toDTO(each));
		}
		result.setPage(index);
		result.setList(list);
		
		return result;
	}

	@Override
	public ResponseEntity<?> save(InputProduct input, HttpServletRequest req) {
		ResponseDTO<ProductDTO> result = new ResponseDTO<>();
		try {
			ProductEntity product = new ProductEntity();
			if(input.getId()!=null) {
				if(productRepository.findOne(input.getId())!=null) {
					if(categoryRepository.findOne(input.getCategoryId())==null) {
						result.setMessage("Mã danh mục không tồn tại");
						return ResponseEntity.badRequest().body(result);
					}else if(originRepository.findOne(input.getOriginId())==null){
						result.setMessage("Mã xuất xứ không tồn tại");
						return ResponseEntity.badRequest().body(result);
					}else {
						ProductEntity old = productRepository.findOne(input.getId());
						product = productConverter.toEntity(input, old);
						product.setModifiedBy(helpService.getName(req));
						product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
						productRepository.save(product);
						productRepository.save(product);
						result.setMessage("Đã cập nhật thành công sản phẩm "+product.getName());
						ProductDTO productRep = productConverter.toDTO(product);
						result.setObject(productRep);
						return ResponseEntity.ok(result);
					}
				}else {
					result.setMessage("Mã sản phẩm không tồn tại");
					return ResponseEntity.badRequest().body(result);
				}
			}else {
				if(categoryRepository.findOne(input.getCategoryId())==null) {
					result.setMessage("Mã danh mục không tồn tại");
					return ResponseEntity.badRequest().body(result);
				}else if(originRepository.findOne(input.getOriginId())==null){
					result.setMessage("Mã xuất xứ không tồn tại");
					return ResponseEntity.badRequest().body(result);
				}else {
					product = productConverter.toEntity(input);
					product.setCreatedBy(helpService.getName(req));
					product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					productRepository.save(product);
					result.setMessage("Đã thêm thành công sản phẩm "+product.getName());
					ProductDTO productRep = productConverter.toDTO(product);
					result.setObject(productRep);
					return ResponseEntity.ok(result);
				}
			}
		}  catch (InvalidDataAccessApiUsageException e2) {
			result.setMessage("Đã có lỗi xảy ra !");
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		ResponseDTO<ProductDTO> result = new ResponseDTO<>();
		if(productRepository.findOne(id)!=null) {
			ProductEntity e = productRepository.findOne(id);
			productRepository.delete(e);
			ProductDTO productDTO = productConverter.toDTO(e);
			result.setMessage("Đã xóa sản phẩm "+e.getName());
			result.setObject(productDTO);
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Không tìm thấy hoặc mã sản phẩm không hợp lệ !");
			return ResponseEntity.badRequest().body(result);
		}
	}

	@Override
	public ResponseEntity<?> findOne(long id) {
		ProductDTO product = new ProductDTO();
		ProductEntity e = new ProductEntity();
		if(productRepository.findOne(id)!=null) {
			e = productRepository.findOne(id);
			product = productConverter.toDTO(e);
			return ResponseEntity.ok(product);
		}else {
			return ResponseEntity.badRequest().body(product);
		}
	}

	@Override
	public ResponseEntity<?> showAll() {
		List<ProductDTO> list = new ArrayList<>();
		for(ProductEntity each : productRepository.findAll()) {
			list.add(productConverter.toDTO(each));
		}
		return ResponseEntity.ok(list);
	}
}
