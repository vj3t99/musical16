package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.converter.ProductConverter;
import com.musical16.dto.product.ProductDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.dto.response.Page;
import com.musical16.repository.CategoryRepository;
import com.musical16.repository.ImageRepository;
import com.musical16.repository.OriginRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.service.IFileStorageService;
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
	
	@Autowired
	private IFileStorageService fileStorageService;
	
	@Override
	public Page<ProductDTO> search(String key) {
		Page<ProductDTO> result = new Page<>();
		List<ProductDTO> list = new ArrayList<>();
		for(ProductEntity each : productRepository.search(key)) {
			list.add(productConverter.toDTO(each));
		}
		result.setList(list);
		return result;
	}
	
	@Override
	public Page<ProductDTO> findAll(Integer page, String[] sort) {
		Page<ProductDTO> result = new Page<>();
		Integer index = null;
		List<Order> listorders = new ArrayList<>();
		
		try {
			try {
				if(page<=0||page==null) {
					index = 1;
				}else {
					index=page;
				}
			} catch (NullPointerException e) {
				index = 1;
			}
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
			
		}finally {
			
			
			Pageable pageable;
			if(listorders.size()!=0) {
				Sort sorts = new Sort(listorders);
				pageable = new PageRequest(index -1, PAGE_LIMIT,sorts);
			}else {
				pageable = new PageRequest(index -1, PAGE_LIMIT);
			}
			
			List<ProductDTO> list = new ArrayList<>();
			org.springframework.data.domain.Page<ProductEntity> listEntity = productRepository.findAll(pageable);
			for(ProductEntity each : listEntity) {
				list.add(productConverter.toDTO(each));
			}
			result.setPage(index);
			result.setList(list);
			result.setTotalPage((int) Math.ceil((double) productRepository.count()/PAGE_LIMIT));
		}
		
		return result;
	}

	@Override
	public MessageDTO save(ProductDTO productDTO, HttpServletRequest req) {
		MessageDTO message = new MessageDTO();
		try {
			ProductEntity product = new ProductEntity();
			if(productDTO.getId()!=null) {
				if(productRepository.findOne(productDTO.getId())!=null) {
					if(categoryRepository.findOne(productDTO.getCategoryId())==null) {
						message.setMessage("Mã danh mục không tồn tại");
					}else if(originRepository.findOne(productDTO.getOriginId())==null){
						message.setMessage("Mã xuất xứ không tồn tại");
					}else {
						ProductEntity old = productRepository.findOne(productDTO.getId());
						product = productConverter.toEntity(productDTO, old);
						if(product.getQuantity()>0) {
							product.setStatus(1);
						}else {
							product.setStatus(0);
						}
						product.setModifiedBy(helpService.getName(req));
						product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
						productRepository.save(product);
						message.setMessage("Đã cập nhật thành công sản phẩm "+product.getName());
					}
				}else {
					message.setMessage("Mã sản phẩm không tồn tại");
				}
			}else {
				if(categoryRepository.findOne(productDTO.getCategoryId())==null) {
					message.setMessage("Mã danh mục không tồn tại");
				}else if(originRepository.findOne(productDTO.getOriginId())==null){
					message.setMessage("Mã xuất xứ không tồn tại");
				}else {
					product = productConverter.toEntity(productDTO);
					if(product.getQuantity()>0) {
						product.setStatus(1);
					}else {
						product.setStatus(0);
					}
					product.setCreatedBy(helpService.getName(req));
					product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					productRepository.save(product);
					message.setMessage("Đã thêm thành công sản phẩm "+product.getName());
				}
			}
		} catch (DataIntegrityViolationException e) {
			message.setMessage("Code sản phẩm đã tồn tại, vui lòng tạo code khác");
		} catch (InvalidDataAccessApiUsageException e2) {
			message.setMessage("Bạn đã nhập thiếu cột hoặc sai key");
		}
		return message;
	}

	@Override
	public MessageDTO delete(Long id) {
		MessageDTO message = new MessageDTO();
		if(productRepository.findOne(id)!=null) {
			ProductEntity e = productRepository.findOne(id);
			List<ImageEntity> images = imageRepository.findByProducts(e);
			for(ImageEntity each : images) {
				imageRepository.delete(each);
				fileStorageService.deleteFile(each.getName());
			}
			productRepository.delete(e);
			message.setMessage("Đã xóa sản phẩm "+e.getName());
		}else {
			message.setMessage("Không tìm thấy mã sản phẩm");
		}
		return message;
	}

	@Override
	public ProductDTO findOne(long id) {
		ProductDTO product = new ProductDTO();
		ProductEntity e = new ProductEntity();
		if(productRepository.findOne(id)!=null) {
			e = productRepository.findOne(id);
			product = productConverter.toDTO(e);
		}else {
			product.setMessage("Không tìm thấy mã sản phẩm");
		}
		return product;
	}
}
