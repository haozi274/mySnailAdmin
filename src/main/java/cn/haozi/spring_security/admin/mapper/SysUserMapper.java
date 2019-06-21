package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.vo.UserInfo;
import cn.haozi.spring_security.chat.entity.ChatUser;
import cn.haozi.spring_security.chat.entity.GroupName;
import cn.haozi.spring_security.chat.entity.SysFriend;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/6 10:46
 * @Description:
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert sys_user (username,login_name,telephone,mail,password,remark,status,create_by,create_time,update_by,update_time,del_flag,ip,remark)" +
            " values (#{user.username},#{user.loginName},#{user.telephone},#{user.mail},#{user.password}," +
            "#{user.remark},#{user.status},#{user.createBy},#{user.createTime},#{user.updateBy},#{user.updateTime},#{user.delFlag}" +
            ",#{user.ip},#{remark})")
    int saveUser(@Param("user") SysUser user);


    UserInfo findById(int id);

    List<ChatUser> selectFriends(SysFriend friend);

    List<GroupName> selectGroupList(Integer id);

    ChatUser selectChatUser(Integer id);

    List<ChatUser> selectChatUserList(String userName);
}
