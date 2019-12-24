package org.litespring.service.version4;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.dao.version4.*;
import org.litespring.stereotype.Component;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
@Component(value="petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}
