package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by adrianmaryniewski on 21/12/16.
 */

@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<Integer, User> implements MessageDao {
}
