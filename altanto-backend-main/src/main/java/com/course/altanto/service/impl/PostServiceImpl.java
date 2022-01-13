package com.course.altanto.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.course.altanto.entity.Post;
import com.course.altanto.entity.dto.PostDTO;
import com.course.altanto.exception.ResourceNotFoundsException;
import com.course.altanto.repository.IPostRepository;
import com.course.altanto.service.IPostService;

@Service
@Transactional
public class PostServiceImpl implements IPostService {

	private IPostRepository postRepository;
	
	PostServiceImpl(IPostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@Override
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post createPost(PostDTO param) {
		
        Post post = new Post();
        post.setDescription(param.getDescription());
        post.setContent(param.getContent());
        post.setTitle(param.getTitle());
        post.setCategory(param.getCategory());
        post.setDateCreation(new Date());
        post.setUrlImage(param.getUrlImage());
        postRepository.save(post);
		
		return post;
	}

	@Override
	public Post updatePost(PostDTO param) {
		
		Post currentPost = postRepository.findById(param.getId()).orElseThrow(() -> new ResourceNotFoundsException("POST", "ID", param.getId().toString()));
		
		currentPost.setContent(param.getContent());
		currentPost.setDescription(param.getDescription());
		currentPost.setTitle(param.getTitle());
		currentPost.setCategory(param.getCategory());
		currentPost.setDateCreation(new Date());
		currentPost.setUrlImage(param.getUrlImage());
		postRepository.save(currentPost);
		
		return currentPost;
	}

	@Override
	public void deletePost(long id) {
		postRepository.deleteById(id);
	}

	@Override
	public Post getPostById(long id) {
		
		Post currentPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundsException("POST", "ID", "ID"));
		return currentPost;
	}
	
	

}
