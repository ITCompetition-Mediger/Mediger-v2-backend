package net.mediger.global.properties;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@ConfigurationProperties(prefix = "security")
public record SecurityProperties(List<String> whiteList) {

    public RequestMatcher[] getWhiteListMatchers() {
        log.info("🚀 whiteList: {}", whiteList);
        return whiteList.stream()
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }
}

