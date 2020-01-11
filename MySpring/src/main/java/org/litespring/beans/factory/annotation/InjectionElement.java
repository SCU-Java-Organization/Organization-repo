package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * Description: A wrapper abstract class which is used to describe
 * a dependency waiting for injected. But we did not provide the
 * method #inject(Object), which means you must implement it in the
 * sub classes.
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
