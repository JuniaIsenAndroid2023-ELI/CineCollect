package com.example.cinecollect.listeners;

import com.example.cinecollect.pojo.CineCollectUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface GetUserListener {
    void onGetUser(Set<Map.Entry<String, CineCollectUser>> users);
}
