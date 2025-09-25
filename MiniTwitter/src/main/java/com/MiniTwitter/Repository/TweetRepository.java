package com.MiniTwitter.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MiniTwitter.Entity.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
	List<Tweet> findTop10ByOrderByCreatedAtDesc();
	
}
