package com.msa.social.repository.rest;

import com.msa.domain.Follow;

import java.util.List;

public interface FollowRestRepository {
    public List<Follow> getFolloweeList(Long userId, List<Long> userIdList);
}
