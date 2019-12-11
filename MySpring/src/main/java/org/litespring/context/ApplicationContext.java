package org.litespring.context;

import org.litespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * Description: A wrapper interface
 * This interface is designed to simplify operations for users,
 * which means users do not have to care about how the functions are achieved.
 *
 * @see org.litespring.context.support.ClassPathXmlApplicationContext
 * @author ShaoJiale
 * date 2019/12/11
 */
public interface ApplicationContext extends ConfigurableBeanFactory {

}
