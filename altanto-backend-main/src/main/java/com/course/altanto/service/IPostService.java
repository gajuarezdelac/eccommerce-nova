package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Post;
import com.course.altanto.entity.dto.PostDTO;

@Component
public interface IPostService {
	
	List<Post> getPosts();
	
	Post createPost(PostDTO param); 
	
	Post updatePost(PostDTO param);
	
	void deletePost(long id);
	
	Post getPostById(long id);;

}
