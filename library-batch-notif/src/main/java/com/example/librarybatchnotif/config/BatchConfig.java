package com.example.librarybatchnotif.config;

import com.example.librarybatchnotif.model.LoanBean;
import com.example.librarybatchnotif.model.BookingBean;
import com.example.librarybatchnotif.proxy.BookingProxy;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@EnableScheduling

public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemProcessor<BookingBean, BookingBean> bookingItemProcessor;

    @Autowired
    private ItemWriter<BookingBean> bookingBeanItemWriter;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    BookingProxy bookingProxy;


    @Bean
   @CacheEvict(value = {"booking"}, allEntries = true)
    public Job mailJob() throws IOException {


       System.out.println("mail job");
        Step step1 = stepBuilderFactory.get("step-send-email")
                .<BookingBean, BookingBean>chunk(100)
                .reader(bookingExpiredItemReader())
                .processor(bookingItemProcessor)
                .writer(bookingBeanItemWriter)
                .build();
        return jobBuilderFactory.get("job-send-email")
                .start(step1).build();

    }


    //liste de booking avec notif date expired
    @Bean
    public ListItemReader<BookingBean> bookingExpiredItemReader() throws IOException {
        System.out.println("item reader");
        LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
        Timestamp timestamp = Timestamp.valueOf(String.valueOf(now));

        return new ListItemReader<>(bookingProxy.listBookingByNotifDateExpired(timestamp));
    }


    // job launch every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void launchJob() throws Exception {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);
        jobLauncher.run(mailJob(), jobParameters);
    }


}
