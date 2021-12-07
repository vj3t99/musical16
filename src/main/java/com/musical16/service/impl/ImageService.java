package com.musical16.service.impl;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.Entity.ImageEntity;
import com.musical16.converter.ImageConverter;
import com.musical16.dto.product.ImageDTO;
import com.musical16.dto.response.MessageDTO;
import com.musical16.repository.ImageRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IHelpService;
import com.musical16.service.IImageService;

@Service
public class ImageService implements IImageService{
	@Autowired
	private ImageConverter imageConverter;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private IFileStorageService fileStorageService;
	
	@Autowired
	private IHelpService helpService;

	@Override
	public ImageDTO save(MultipartFile file, Long id, HttpServletRequest req) {
//		ImageDTO imageDTO = new ImageDTO();
//		ImageEntity image = new ImageEntity();
//		if(productRepository.findOne(id)!=null) {
//			String filename = fileStorageService.storeFile(file);
//			String url = helpService.getSiteURL(req) + "/downloadFile/"+filename;			
//			image.setUrl(url);
//			image.setProducts(productRepository.findOne(id));
//			image.setCreatedBy(helpService.getName(req));
//			image.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//			imageRepository.save(image);
//			imageDTO = imageConverter.toDTO(image);
//		}else {
//			imageDTO.setMessage("Không tìm thấy mã sản phẩm");
//		}
//		return imageDTO ;
		return null;
	}

	@Override
	public MessageDTO deleteFile(Long id) {
//		ImageEntity image = imageRepository.findOne(id);
//		String message ;
//		if(image!=null) {
//			if(fileStorageService.deleteFile(image.getName())) {
//				imageRepository.delete(image);
//				message = "Xóa thành công hình "+image.getName();
//			}else {
//				message = "Failed";
//			}
//		}else {
//			message = "Mã hình ảnh không tồn tại";
//		}
//		return new MessageDTO(message);
		return null;
	}

	@Override
	public MessageDTO deleteAllFile(Long id) {
//		String message;
//		ProductEntity product = productRepository.findOne(id);
//		if(product!=null) {
//			List<ImageEntity> image = imageRepository.findByProducts(product);
//			for(ImageEntity each: image) {
//				if(fileStorageService.deleteFile(each.getName())) {
//					imageRepository.delete(each);
//				}
//			}
//			message = "Xóa thành công tất cả hình ảnh của sản phẩm "+product.getName();
//		}else {
//			message = "Mã sản phẩm không tồn tại";
//		}
//		return new MessageDTO(message);
		return null;
	}


	
}
