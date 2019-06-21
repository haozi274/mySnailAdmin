package cn.haozi.spring_security.chat.service.impl;

import cn.haozi.spring_security.admin.mapper.SysUserMapper;
import cn.haozi.spring_security.chat.entity.ChatData;
import cn.haozi.spring_security.chat.entity.ChatUser;
import cn.haozi.spring_security.chat.entity.GroupName;
import cn.haozi.spring_security.chat.entity.SysFriend;
import cn.haozi.spring_security.chat.service.SysFriendService;
import com.xiaoleilu.hutool.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysFriendServiceImpl implements SysFriendService {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查找用户好友列表：
     *      查出用户的分组，根据分组查出分组下的好有信息
     * @param chatUser
     * @return
     */
    @Override
    public  ChatData<ChatUser> selectFriends(ChatUser chatUser) {
        List<GroupName> groupNameList = userMapper.selectGroupList(chatUser.getId());
        ChatData<ChatUser> data = new ChatData<>();

        ChatUser user = userMapper.selectChatUser(chatUser.getId());
        //用户个人信息
        data.setMine(user);
        SysFriend friend = new SysFriend();
        friend.setUserId(chatUser.getId());
        //分组列表用户
        List<GroupName> groupNames = new ArrayList<>();
        groupNameList.stream().forEach(v->{
            friend.setGroupId(v.getId());
            List<ChatUser> list = userMapper.selectFriends(friend);
            GroupName groupName = new GroupName();
            BeanUtil.copyProperties(v,groupName);
            groupName.setList(list);
            groupNames.add(groupName);

        });
        data.setFriend(groupNames);
        return data;
    }
}
