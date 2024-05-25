package com.example.project.repository.jpa.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@RequiredArgsConstructor
public class TestHibernateJpaConfig {
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Statistics hibernateStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        return sessionFactory.getStatistics();
    }
}
