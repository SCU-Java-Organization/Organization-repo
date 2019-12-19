package org.litespring.service.version3;

import org.litespring.dao.version3.AccountDao;
import org.litespring.dao.version3.ItemDao;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/18
 */
public class PetStoreService {
    private AccountDao accountDao;

    private ItemDao itemDao;

    private String owner;

    private int version;

    public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, String owner) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.owner = owner;
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, String owner, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.owner = owner;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public String getOwner() {
        return owner;
    }

    public int getVersion() {
        return version;
    }
}
