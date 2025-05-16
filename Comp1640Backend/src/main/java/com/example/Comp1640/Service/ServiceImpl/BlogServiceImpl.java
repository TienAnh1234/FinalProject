package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.BlogRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BlogDto> getAllBlogDto() {
        List<Blog> listBlogs = blogRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
        List<BlogDto> listBlogDto = new ArrayList<>();

        if (listBlogs.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < listBlogs.size(); i++){
//                System.out.println(listBlogs.get(i).getUser().getId() + "hanoi");
                BlogDto blogDto = new BlogDto(listBlogs.get(i));
                listBlogDto.add(blogDto);
            }
        }
        return listBlogDto;
    }

    @Override
    public String saveBlogDto(BlogDto blogDto, MultipartFile file) throws IOException {
        User existingUser = userRepository.findById(blogDto.getUserId()).orElse(null);

        if(file !=null){
            byte[] fileData = file.getBytes();
            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();
            blogRepository.save(new Blog(blogDto.getContent(),existingUser,blogDto.getStatus(),null,null,null,fileData,fileName,fileType));
        }else{
            blogRepository.save(new Blog(blogDto.getContent(),existingUser,blogDto.getStatus(),null,null,null,null,null,null));
        }
        return "Saved Blog Successfully";
    }

    @Override
    public void deleteBlogDto(Long id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String role = auth.getAuthorities().iterator().next().getAuthority();
//

        System.out.println(id + " deleted");
        blogRepository.deleteBlogById(id);
    }

    @Override
    public String updateBlogDto(Long id, BlogDto blogDto, MultipartFile file)  throws IOException{
        Blog existingBlog = blogRepository.findById(id).orElse(null);
        if(file == null && Objects.equals(blogDto.getFileName(), "")){
            existingBlog.setFileName(null);
            existingBlog.setFileData(null);
            existingBlog.setFileType(null);
            existingBlog.setContent(blogDto.getContent());
            existingBlog.setStatus(blogDto.getStatus());
            blogRepository.save(existingBlog);
            System.out.println("1");
        }

        if(file == null && !Objects.equals(blogDto.getFileName(), "")){
            existingBlog.setContent(blogDto.getContent());
            existingBlog.setStatus(blogDto.getStatus());
            blogRepository.save(existingBlog);
            System.out.println(blogDto.getFileName());
            System.out.println("2");

        }
        if(file != null && !Objects.equals(blogDto.getFileName(), "")){
            existingBlog.setFileName(file.getOriginalFilename());
            existingBlog.setFileData(file.getBytes());
            existingBlog.setFileType(file.getContentType());
            existingBlog.setContent(blogDto.getContent());
            existingBlog.setStatus(blogDto.getStatus());
            blogRepository.save(existingBlog);
            System.out.println("3");

        }

            return "Updated Blog Successfully";
    }


}
