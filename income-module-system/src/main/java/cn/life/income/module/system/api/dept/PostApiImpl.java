package cn.life.income.module.system.api.dept;

import cn.life.income.framework.common.util.object.BeanUtils;
import cn.life.income.module.system.api.dept.dto.PostRespDTO;
import cn.life.income.module.system.dal.dataobject.dept.PostDO;
import cn.life.income.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author zengxuebin
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
