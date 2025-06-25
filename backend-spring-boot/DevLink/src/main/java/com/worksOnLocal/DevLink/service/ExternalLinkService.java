package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.repository.ExternalLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ExternalLinkService {
private final ExternalLinkRepository externalLinkRepository;
}
