package com.epam.hostel;

import com.epam.hostel.dao.IUserDAO;
import com.epam.hostel.dao.UserCrud;
import com.epam.hostel.dao.impl.UserDAOImpl;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@ContextConfiguration(locations = {"classpath*:/applicationContext.groovy"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class GroovyContextTest {
   /* ApplicationContext context = new GenericGroovyApplicationContext("classpath*:/applicationContext.groovy");
    UserDAOImpl userDAO = context.getBean("UserDao",UserDAOImpl.class);*/

    private static Logger logger = Logger.getLogger(GroovyContextTest.class);


    @Test
    public void findAll() throws Exception {
        ApplicationContext context = new GenericGroovyApplicationContext("classpath*:/applicationContext.groovy");
        logger.error(context.getBeanDefinitionCount());
        UserDAOImpl userDAO = context.getBean("UserDao",UserDAOImpl.class);
        logger.error(context.getBeanDefinitionNames());
        System.out.println(context.getBeanDefinitionNames());
        logger.info(userDAO.findAll());
    }

}
