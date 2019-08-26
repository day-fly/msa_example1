package com.msa.social.controller;

import com.msa.domain.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msa.social.controller.dto.FollowDto;
import com.msa.controller.dto.ResultDto;
import com.msa.domain.AuthToken;
import com.msa.service.AuthService;
import com.msa.social.service.FollowService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FollowController {
	
	@Autowired
	FollowService followService;
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/follow")
	public ResultDto addFollow(@RequestBody FollowDto dto, @RequestHeader(value="accesstoken") String accesstoken) {
		AuthToken authToken = authService.getAuthToken(accesstoken);
		if(authToken == null) {
			return new ResultDto(4002, "OK", "Authentication Failed");
		}
		
		followService.addFollow(dto.getFolloweeId(), authToken.getUserId());
		
		return new ResultDto(200, "OK", "Success");
	}
	
	@DeleteMapping("/follow")
	public ResultDto deleteFollow(@RequestBody FollowDto dto, @RequestHeader(value="accesstoken") String accesstoken) {
		AuthToken authToken = authService.getAuthToken(accesstoken);
		if(authToken == null) {
			return new ResultDto(4002, "OK", "Authentication Failed");
		}
		
		followService.deleteFollow(dto.getFolloweeId(), authToken.getUserId());
		
		return new ResultDto(200, "OK", "Success");
	}

	@GetMapping("/followee")
	public ResultDto getFolloweeList(@RequestParam Long userId, @RequestParam String userIds) {
		String[] idArray = userIds.split(",");
		List<Long> userIdList = new ArrayList<>();
		for(String id : idArray) {
			userIdList.add(Long.valueOf(id));
		}
		List<Follow> followList = followService.getFolloweeList(userId, userIdList);
		return new ResultDto(200, "OK", followList);
	}
}
