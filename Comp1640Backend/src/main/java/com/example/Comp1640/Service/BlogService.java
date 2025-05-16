package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.BlogDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    List<BlogDto>  getAllBlogDto();

    String saveBlogDto(BlogDto blogDto, MultipartFile file) throws IOException;

    void deleteBlogDto(Long id);

    String updateBlogDto(Long id, BlogDto blogDto, MultipartFile file) throws IOException;

}
