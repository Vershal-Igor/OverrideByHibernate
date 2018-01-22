import com.epam.hostel.dao.impl.UserDAOImpl



beans {

    UserDao(UserDAOImpl)

    xmlns([ctx:'http://www.springframework.org/schema/context'])
    ctx.'component-scan'('base-package':"com.epam.hostel")
    ctx.'annotation-config'()
    importBeans("classpath*:applicationContext.xml")
}