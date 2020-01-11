package org.litespring.context;

import org.litespring.beans.factory.BeanFactory;

/**
 * Description: A wrapper interface
 * This interface is designed to simplify operations for users,
 * which means users do not have to care about how the functions are achieved.
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see org.litespring.context.support.ClassPathXmlApplicationContext
 */
public interface ApplicationContext extends BeanFactory {

}
