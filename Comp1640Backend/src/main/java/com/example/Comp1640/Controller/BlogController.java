package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Repository.BlogRepository;
import com.example.Comp1640.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping()
    public ResponseEntity<List<BlogDto>> getAllBlogDto() {
        return ResponseEntity.ok(blogService.getAllBlogDto());
    }

    @PostMapping("/save_blog")
    public ResponseEntity<String> saveNew(@ModelAttribute BlogDto blogDto,
                                          @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        System.out.println("hnoi");
//        System.out.println( file.getSize() + " hnoi");
        return ResponseEntity.ok(blogService.saveBlogDto(blogDto,file));
    }



    @GetMapping("/download/{blogId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + blog.getFileName())
                .header("Content-Type", blog.getFileType())
                .body(blog.getFileData());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlogDto(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @ModelAttribute BlogDto blogDto,
                                         @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {
        return ResponseEntity.ok(blogService.updateBlogDto(id, blogDto,file));
    }


}
