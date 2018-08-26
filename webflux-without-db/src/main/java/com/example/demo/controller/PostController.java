package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author hantsy
 */
@RestController
@RequestMapping(value = "/posts")
public class PostController {

	private final PostRepository posts;

	public PostController(PostRepository posts) {
        this.posts = posts;
    }

	@GetMapping(value = "")
	public Flux<Post> all() {
		return this.posts.findAll();
	}

	@GetMapping(value = "/{id}")
	public Mono<Post> get(@PathVariable(value = "id") Long id) {
		return this.posts.findById(id);
	}

	@PostMapping(value = "")
	public Mono<Post> create(@RequestBody Post post) {
		return this.posts.save(post);
	}

	@PutMapping(value = "/{id}")
	public Mono<Post> update(@PathVariable(value = "id") Long id, @RequestBody Post post) {
		return this.posts.update(id, post);
	}

	@DeleteMapping(value = "/{id}")
	public Mono<Post> delete(@PathVariable(value = "id") Long id) {
		return this.posts.delete(id);
	}

}