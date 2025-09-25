package com.MiniTwitter.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.MiniTwitter.Entity.Tweet;
import com.MiniTwitter.Service.TwitterService;

@Controller
public class TweetController {
	
	@Autowired
	TwitterService service;

	@GetMapping("/")
	public String home() {
		return "tweet";
	}
	
	@GetMapping("/get/all")
	public ModelAndView getAllTweets(){
		//return service.getRecentTweets();
		List<Tweet> tweets = service.getRecentTweets();
		List<Map<String,String>> formattedTweets = tweets.stream().map(tweet-> {
			Map<String,String>map = new HashMap<>();
			map.put("name", tweet.getName());
			map.put("message", tweet.getMessage());
			map.put("createdAt",getTime(tweet.getCreatedAt()));
			return map;
		}).toList();
		return new ModelAndView("AllTweets","tweets",formattedTweets) ;
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Tweet tweet) {
		 service.save(tweet);
		 return "redirect:/get/all";
	}
	
	@PutMapping("/update/{id}")
	public Tweet Update(@PathVariable Long id,@RequestBody Map<String,String> body) {
		String message = body.get("message");
		return service.Update(id, message);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	public String getTime(LocalDateTime tweetTime) {
		Duration duration = Duration.between(tweetTime, LocalDateTime.now());
		long seconds = duration.getSeconds();
		if(seconds < 60) {
			return seconds +" seconds ago";
		}
		else if(seconds< 3600) {
			return (seconds/60) +" minutes ago";
		}
		else if(seconds <86400) {
			return (seconds/3600) +" hours ago";
		}
		else {
			return (seconds/86400)+ "days ago";
		}
	}
}
