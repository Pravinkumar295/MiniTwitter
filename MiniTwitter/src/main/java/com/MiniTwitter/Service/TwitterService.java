package com.MiniTwitter.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiniTwitter.Entity.Tweet;
import com.MiniTwitter.Repository.TweetRepository;

@Service
public class TwitterService {
	
	@Autowired
	private TweetRepository repository;
	
	public Tweet save(Tweet tweet) {
		tweet.setCreatedAt(LocalDateTime.now());
		return repository.save(tweet);
	}
	
	public List<Tweet> getRecentTweets(){
		return repository.findTop10ByOrderByCreatedAtDesc();
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Tweet Update(Long id, String newMessage) {
		Tweet tweet = repository.findById(id).orElseThrow();
		tweet.setMessage(newMessage);
		return repository.save(tweet);
		
	}
	
}
