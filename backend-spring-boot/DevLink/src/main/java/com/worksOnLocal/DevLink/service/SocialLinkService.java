package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.repository.SocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;
}
