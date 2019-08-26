package com.msa.social.repository.rest;

import com.msa.domain.Feed;
import com.msa.social.controller.dto.FeedRequestDto;
import com.msa.social.repository.rest.dto.FeedData;
import com.msa.social.repository.rest.dto.FeedResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FeedRestRepositoryImpl implements FeedRestRepository {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Feed> getFeedList(Long userId) {
        FeedResponseDto response = restTemplate
                .getForEntity("http://localhost:8080/feed?userId={userId}", FeedResponseDto.class, userId).getBody();

        List<FeedData> feedDataList = response.getData();
        List<Feed> feedList = new ArrayList<>();
        for (FeedData data : feedDataList) {
            feedList.add(new Feed(data.getUserId(), data.getFolloweeId(), data.getPostId()));
        }
        return feedList;
    }

    @Override
    public void addFeeds(Long userId, Long postId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        FeedRequestDto requestDto = new FeedRequestDto(userId, postId);
        HttpEntity<FeedRequestDto> entity = new HttpEntity<FeedRequestDto>(requestDto, headers);
        restTemplate.exchange("http://localhost:8080/feed", HttpMethod.POST, entity, String.class);
    }
}
