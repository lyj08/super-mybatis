package com.spring.mybatis.support;

import com.mybatis.session.SuperSqlSession;
import com.mybatis.session.SuperSqlSessionFactory;
import com.spring.mybatis.spring.SuperSqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;

import static org.springframework.util.Assert.notNull;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/22 0:36
 */
public class SuperSqlSessionDaoSupport implements InitializingBean
{
    private SuperSqlSessionTemplate SuperSqlSessionTemplate;


    public void setSqlSessionFactory(SuperSqlSessionFactory sqlSessionFactory) {
        if (this.SuperSqlSessionTemplate == null) {
            this.SuperSqlSessionTemplate = createSqlSessionTemplate(sqlSessionFactory);
        }
    }

    public SuperSqlSessionTemplate createSqlSessionTemplate(SuperSqlSessionFactory sqlSessionFactory) {
        return new SuperSqlSessionTemplate(sqlSessionFactory);
    }

    public void setSuperSqlSessionTemplate(SuperSqlSessionTemplate SuperSqlSessionTemplate) {
        this.SuperSqlSessionTemplate = SuperSqlSessionTemplate;
    }


    public SuperSqlSession getSqlSession() {
        return this.SuperSqlSessionTemplate;
    }


    public SuperSqlSessionTemplate getSuperSqlSessionTemplate() {
        return this.SuperSqlSessionTemplate;
    }

    protected void checkDaoConfig() {
        notNull(this.SuperSqlSessionTemplate, "Property 'sqlSessionFactory' or 'SuperSqlSessionTemplate' are required");
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        checkDaoConfig();
    }
}
