package com.wqzhang.thingswapper.util.net.netConvert;

import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.util.net.model.UserDTO;

import java.util.Date;

/**
 * Created by wqzhang on 17-3-14.
 * 负责 网络传输的类 和 本地所用到的类  两者的相互解析
 */

public class UserParseTools {

    public static UserDTO user2UserDTO2(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setAccount(user.getAccount());
        userDTO.setPassword("#$%?");
        userDTO.setEmail(user.getEmail());
        userDTO.setCreateDate(user.getCreateDate());
        userDTO.setSynchronize(user.getSynchronize());
        userDTO.setVersion(user.getVersion());
        return userDTO;
    }

    public static User userDTO2User(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setAccount(userDTO.getAccount());
        user.setPassword("#$%?");
        user.setEmail(userDTO.getEmail());
        user.setCreateDate(userDTO.getCreateDate());
        user.setSynchronize(userDTO.isSynchronize());
        user.setVersion(userDTO.getVersion());
        user.setDefaultLoginAccount(false);
        return user;
    }

    public static User userDTO2UserWithPwd(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setAccount(userDTO.getAccount());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setCreateDate(userDTO.getCreateDate());
        user.setSynchronize(userDTO.isSynchronize());
        user.setVersion(userDTO.getVersion());
        user.setDefaultLoginAccount(false);
        return user;
    }
}
