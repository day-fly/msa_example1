package com.msa.social.controller;

import com.msa.controller.dto.ResultDto;
import com.msa.domain.Feed;
import com.msa.social.controller.dto.FeedRequestDto;
import com.msa.social.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedController {

	@Autowired
	FeedService feedService;

	@PostMapping("/feed")
	public ResultDto addFeeds(@RequestBody FeedRequestDto dto) {
		feedService.addFeeds(dto.getUserId(), dto.getPostId());
		return new ResultDto(200, "OK", "Success");
	}

	@GetMapping("/feed")
	public ResultDto getFeeds(@RequestParam Long userId) {
		List<Feed> feedList = feedService.getFeedList(userId);
		return new ResultDto(200, "OK", feedList);
	}
}