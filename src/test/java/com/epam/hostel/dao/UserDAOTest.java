package com.epam.hostel.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

//@ComponentScan("com.epam.hostel")


@RunWith(SpringJUnit4ClassRunner.class)
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DataConfig.class})*/

public class UserDAOTest {
/*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
    UserCrud userCrud = (UserCrud) applicationContext.getBean(UserCrud.class);*/


    private static Logger logger = Logger.getLogger(UserDAOTest.class);

    @Autowired
    private UserCrud userCrud;

    @Test
    public void findAll() throws Exception {
        logger.info(userCrud.findAll());
    }

    @Test
    public void findOne() throws Exception {
        logger.info(userCrud.findOne((long) 1));
    }

    /*@Test
    public void findByName() throws Exception {
        logger.info(userCrud.findByName("Игорь"));
    }

    @Test
    public void countAllByBanned() throws Exception {
        logger.info(userCrud.countAllByBanned());
    }*/


}
