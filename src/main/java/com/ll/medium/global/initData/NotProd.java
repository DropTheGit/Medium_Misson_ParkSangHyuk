package com.ll.medium.global.initData;

import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if (memberService.findByUsername("user1").isPresent()) return;

                Member memberUser1 = memberService.join("user1", "1234", true);

                Member memberUser2 = memberService.join("user2", "1234", true);

                Member memberUser3 = memberService.join("user3", "1234");

                Member memberUser4 = memberService.join("user4", "1234");

                IntStream.rangeClosed(5, 100).forEach(i -> {
                    memberService.join("user " + i, "1234", true);
                });

                articleService.write("제목 1", "내용 1", true, true, memberUser1);
                articleService.write("제목 2", "내용 2", true, false, memberUser1);
                articleService.write("제목 3", "내용 3", true, false, memberUser1);
                articleService.write("제목 4", "내용 4", true, false, memberUser3);
                articleService.write("제목 5", "내용 5", true, false, memberUser4);

                IntStream.rangeClosed(6, 100).forEach(i -> {
                    articleService.write("제목 " + i, "내용 " + i, true, true, memberUser2);
                });

            }
        };
    }
}
