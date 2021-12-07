package com.musical16.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musical16.Entity.CommentEntity;
import com.musical16.Entity.CommentReplyEntity;
import com.musical16.Entity.ProductEntity;
import com.musical16.Entity.UserEntity;
import com.musical16.converter.CommentConverter;
import com.musical16.converter.CommentReplyConverter;
import com.musical16.dto.comment.CommentDTO;
import com.musical16.dto.comment.CommentReplyDTO;
import com.musical16.dto.request.InputComment;
import com.musical16.dto.request.InputCommentReply;
import com.musical16.dto.response.Page;
import com.musical16.dto.response.ResponseDTO;
import com.musical16.repository.CommentReplyRepository;
import com.musical16.repository.CommentRepository;
import com.musical16.repository.ProductRepository;
import com.musical16.repository.UserRepository;
import com.musical16.service.ICommentService;
import com.musical16.service.IHelpService;

@Service
public class CommentService implements ICommentService{
	
	@Value("${jpa.page.limit}")
	private Integer PAGE_LIMIT;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentConverter commentConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IHelpService helpService;
	
	@Autowired
	private CommentReplyRepository commentReplyRepository;
	
	@Autowired
	private CommentReplyConverter commentReplyConverter;

	@Override
	public Page<CommentDTO> findAll(Long id, Integer page) {
		Page<CommentDTO> result = new Page<>();
		ProductEntity productEntity = productRepository.findOne(id);
		Integer index;
		try {
			if(page==null||page<=0) {
				index = 1;
			}else {
				index = page;
			}
		} catch (NullPointerException e) {
			index = 1;
		}
		Pageable pageable = new PageRequest(index - 1, PAGE_LIMIT);
		List<CommentDTO> list = new ArrayList<>();
		for(CommentEntity each : commentRepository.findByProduct(productEntity, pageable)) {
			list.add(commentConverter.toDTO(each));
		}
		result.setList(list);
		result.setPage(index);
		List<CommentEntity> temp = commentRepository.findByProduct(productEntity);
		result.setTotalPage(temp.size());
		return result;
	}

	@Override
	public ResponseEntity<?> save(InputComment comment, HttpServletRequest req) {
		ResponseDTO<CommentDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		if(comment.getId()==null) {
			ProductEntity product = productRepository.findOne(comment.getProductId());
			if(product!=null) {
				CommentEntity commentEntity = new CommentEntity();
				commentEntity.setUser(user);
				commentEntity.setCreatedBy(user.getUserName());
				commentEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				commentEntity.setMessage(comment.getMessage());
				commentEntity.setProduct(product);
				commentEntity.setStatus(0);
				commentRepository.save(commentEntity);
				result.setObject(commentConverter.toDTO(commentEntity));
				result.setMessage("Thêm mới bình luận thành công");
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("id sản phẩm không hơp lệ");
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			CommentEntity commentEntity = commentRepository.findByIdAndUser(comment.getId(), user);
			if(commentEntity!=null) {
				commentEntity.setMessage(comment.getMessage());
				commentEntity.setModifiedBy(user.getUserName());
				commentEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				commentRepository.save(commentEntity);
				result.setObject(commentConverter.toDTO(commentEntity));
				result.setMessage("Sửa bình luận thành công");
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Bạn không thể sửa bình luận này");
				return ResponseEntity.badRequest().body(result);
			}
		}
		
	}

	@Override
	public ResponseEntity<?> delete(Long id, HttpServletRequest req) {
		ResponseDTO<CommentDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CommentEntity commentEntity = commentRepository.findByIdAndUser(id, user);
		if(commentEntity!=null) {
			commentRepository.delete(commentEntity);
			result.setMessage("Xóa bình luận thành công");
			result.setObject(commentConverter.toDTO(commentEntity));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Bạn không thể xóa bình luận này");
			return ResponseEntity.badRequest().body(result);
		}
		
	}

	@Override
	public ResponseEntity<?> saveReply(InputCommentReply commentReply, HttpServletRequest req) {
		ResponseDTO<CommentReplyDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		if(commentReply.getId()==null) {
			CommentEntity comment = commentRepository.findOne(commentReply.getCommentId());
			if(comment!=null) {
				CommentReplyEntity commentReplyEntity = new CommentReplyEntity();
				commentReplyEntity.setUser(user);
				commentReplyEntity.setCreatedBy(user.getUserName());
				commentReplyEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				commentReplyEntity.setMessage(commentReply.getMessage());
				commentReplyEntity.setComment(comment);
				commentReplyEntity.setStatus(0);
				commentReplyRepository.save(commentReplyEntity);
				result.setObject(commentReplyConverter.toDTO(commentReplyEntity));
				result.setMessage("Thêm mới phản hồi bình luận thành công");
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("id bình luận không hợp lệ");
				return ResponseEntity.badRequest().body(result);
			}
		}else {
			CommentReplyEntity commentReplyEntity = commentReplyRepository.findByIdAndUser(commentReply.getId(), user);
			if(commentReplyEntity!=null) {
				commentReplyEntity.setMessage(commentReply.getMessage());
				commentReplyEntity.setModifiedBy(user.getUserName());
				commentReplyEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				commentReplyRepository.save(commentReplyEntity);
				result.setObject(commentReplyConverter.toDTO(commentReplyEntity));
				result.setMessage("Sửa phản hồi bình luận thành công");
				return ResponseEntity.ok(result);
			}else {
				result.setMessage("Bạn không thể sửa phản hồi bình luận này");
				return ResponseEntity.badRequest().body(result);
			}
		}
	}

	@Override
	public ResponseEntity<?> deleteReply(Long id, HttpServletRequest req) {
		ResponseDTO<CommentReplyDTO> result = new ResponseDTO<>();
		UserEntity user = userRepository.findByUserName(helpService.getName(req));
		CommentReplyEntity commentReplyEntity = commentReplyRepository.findByIdAndUser(id, user);
		if(commentReplyEntity!=null) {
			commentReplyRepository.delete(commentReplyEntity);
			result.setMessage("Xóa phản hồi bình luận thành công");
			result.setObject(commentReplyConverter.toDTO(commentReplyEntity));
			return ResponseEntity.ok(result);
		}else {
			result.setMessage("Bạn không thể xóa phản hồi bình luận này");
			return ResponseEntity.badRequest().body(result);
		}
	}

	
}
