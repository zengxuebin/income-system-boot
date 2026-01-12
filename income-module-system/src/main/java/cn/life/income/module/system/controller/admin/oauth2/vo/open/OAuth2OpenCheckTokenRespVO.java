package cn.life.income.module.system.controller.admin.oauth2.vo.open;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 管理后台 - 【开放接口】校验令牌 Response VO
 * 该类表示校验令牌接口返回的信息，包括用户编号、客户端信息、授权范围、访问令牌及过期时间等。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2OpenCheckTokenRespVO {

    /**
     * 用户编号
     * 唯一标识系统中的用户
     */
    @JsonProperty("user_id")
    private Long userId;

    /**
     * 用户类型
     * 参见 UserTypeEnum 枚举，表示用户所属的类型
     */
    @JsonProperty("user_type")
    private Integer userType;

    /**
     * 客户端编号
     * 用于标识请求的客户端
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 授权范围
     * 列表形式，表示当前令牌允许访问的资源范围
     */
    private List<String> scopes;

    /**
     * 访问令牌
     * 用户授权后，应用获得的访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 过期时间
     * 令牌的过期时间，单位为秒（时间戳/1000）
     */
    private Long exp;
}
