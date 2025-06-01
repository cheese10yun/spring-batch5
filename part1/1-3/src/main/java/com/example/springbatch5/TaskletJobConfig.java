package com.example.springbatch5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TaskletJobConfig {

    @Bean
    public Job taskletJob(
            JobRepository jobRepository,
            Step taskletStep
    ) {
        return new JobBuilder("taskletJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(taskletStep)
                .build();
    }

    @Bean
    public Step taskletStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet(sampleTasklet(), transactionManager)
                .build();
    }


    private Tasklet sampleTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("sampleTasklet");
            return RepeatStatus.FINISHED;
        };
    }
}