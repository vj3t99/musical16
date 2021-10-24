package com.musical16.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musical16.dto.ImageDTO;
import com.musical16.dto.MessageDTO;
import com.musical16.service.IFileStorageService;
import com.musical16.service.IImageService;


@RestController
public class ImageAPI {
	
	@Autowired
	private IImageService imageService;
	
	@Autowired
    private IFileStorageService fileStorageService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/uploadFile/{id}")
    public ImageDTO uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id") Long id, HttpServletRequest req) {
        return imageService.save(file,id,req);
    }
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/uploadMultipleFiles/{id}")
    public List<ImageDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@PathVariable("id") Long id, HttpServletRequest req) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,id,req))
                .collect(Collectors.toList());
    }

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteFile/{id}")
	public MessageDTO deleteFile(@PathVariable("id") Long id) {
		return imageService.deleteFile(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteAllFile/{id}")
	public MessageDTO deleteAllFile(@PathVariable("id") Long id) {
		return imageService.deleteAllFile(id);
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
