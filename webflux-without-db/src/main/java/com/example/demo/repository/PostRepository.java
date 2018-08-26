package com.example.demo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.example.demo.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostRepository {

	private Map<Long, Post> data = new HashMap<>();
	private AtomicLong nextIdGenerator = new AtomicLong(1L);

	public PostRepository() {
		Stream.of("post one", "post two").forEach(title -> {
			Long id = this.nextId();
			data.put(id, new Post(String.valueOf(id), title, "content of " + title));// .builder().id(id).title(title).content("content
																						// of
																						// "
																						// +
																						// title).build()
		});
	}

	private Long nextId() {
		return nextIdGenerator.getAndIncrement();
	}

	public Flux<Post> findAll() {
		return Flux.fromIterable(data.values());
	}

	public Mono<Post> findById(Long id) {
		return Mono.just(data.get(id));
	}

	public Mono<Post> save(Post post) {
		Long id = nextId();
		Post saved = new Post(String.valueOf(id), post.getTitle(), "content of " + post.getTitle());
		// Post.builder().id(id).title(post.getTitle()).content(post.getContent()).build();
		data.put(id, saved);
		return Mono.just(saved);
	}

	public Mono<Post> update(Long id, Post post) {
		Post updated = data.get(id);
		updated.setTitle(post.getTitle());
		updated.setContent(post.getContent());
		data.put(id, updated);
		return Mono.just(updated);
	}

	public Mono<Post> delete(Long id) {
		Post deleted = data.get(id);
		data.remove(id);
		return Mono.just(deleted);
	}

}