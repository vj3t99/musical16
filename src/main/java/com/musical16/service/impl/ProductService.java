package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.musical16.Entity.ImageEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.converter.ProductConverter;
import com.musical16.dto.MessageDTO;
import com.musical16.dto.ProductDTO;
import com.musical16.repository.CategoryRepository;
import com.musical16.repository.ImageRepository;
import com.musical16.repository.OriginRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IHelpService;
import com.musical16.service.IProductService;

@Service
public class ProductService implements IProductService{
	
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
	public List<ProductDTO> findAll() {
		List<ProductEntity> e = productRepository.findAll();
		List<ProductDTO> dto = new ArrayList<>();
		for(ProductEntity each : e) {
			ProductDTO term = productConverter.toDTO(each);
			dto.add(term);			
		}
		return dto;
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
