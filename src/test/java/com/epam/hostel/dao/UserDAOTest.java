/*
package com.epam.hostel.dao;

*/
/*import com.epam.hostel.config.DataConfig;*//*

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//@ComponentScan("com.epam.hostel")
*/
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DataConfig.class})*//*

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOTest {
    */
/*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDAO userCrud = (UserDAO) applicationContext.getBean(UserDAO.class);*//*

    private static Logger logger = Logger.getLogger(UserDAOTest.class);

    @Autowired
    UserCrud userCrud;

    @Test
    public void findAll() throws Exception {
        logger.info(userCrud.findAll());
    }

    @Test
    public void findOne() throws Exception {
        logger.info(userCrud.findOne((long) 1));
    }

    @Test
    public void findByName() throws Exception {
        logger.info(userCrud.findByName("Игорь"));
    }

    @Test
    public void countAllByBanned() throws Exception {
        logger.info(userCrud.countAllByBanned());
    }


}*/
