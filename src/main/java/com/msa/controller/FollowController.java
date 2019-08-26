package com.msa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.msa.controller.dto.FollowDto;
import com.msa.controller.dto.ResultDto;
import com.msa.domain.AuthToken;
import com.msa.service.AuthService;
import com.msa.service.FollowService;

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
}
