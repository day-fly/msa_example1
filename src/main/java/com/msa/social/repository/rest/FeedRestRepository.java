package com.msa.social.repository.rest;

import com.msa.domain.Feed;

import java.util.List;

public interface FeedRestRepository {
    public void addFeeds(Long userId, Long postId);
    public List<Feed> getFeedList(Long userId);
}


