package com.msa.social.repository.rest;

import com.msa.domain.Follow;
import com.msa.social.repository.rest.dto.FollowData;
import com.msa.social.repository.rest.dto.FollowResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowRestRepositoryImpl implements FollowRestRepository {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Follow> getFolloweeList(Long userId, List<Long> userIdList) {
        StringBuilder idStr = new StringBuilder();
        for(Long id : userIdList) {
            idStr = idStr.length() > 0 ? idStr.append(",").append(id) : idStr.append(id);
        }

        FollowResponseDto response = restTemplate.getForEntity("http://localhost:8080/followee?userId={userId}&userIds={userIds}",
                FollowResponseDto.class, userId, idStr.toString()).getBody();

        List<FollowData> followDataList = response.getData();
        List<Follow> followList = new ArrayList<>();
        for(FollowData data : followDataList) {
            followList.add(new Follow(data.getFolloweeId(), data.getFollowerId()));
        }
        return followList;
    }

}
