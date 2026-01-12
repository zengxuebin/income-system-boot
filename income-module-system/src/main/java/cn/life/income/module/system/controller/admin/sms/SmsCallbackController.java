package cn.life.income.module.system.controller.admin.sms;

import cn.life.income.framework.common.pojo.CommonResult;
import cn.life.income.framework.common.util.servlet.ServletUtils;
import cn.life.income.module.system.framework.sms.core.enums.SmsChannelEnum;
import cn.life.income.module.system.service.sms.SmsSendService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;

import static cn.life.income.framework.common.pojo.CommonResult.success;

/**
 * 短信回调 Controller，处理各大短信平台的回调请求
 */
@RestController
@RequestMapping("/system/sms/callback")
public class SmsCallbackController {

    @Resource
    private SmsSendService smsSendService;

    /**
     * 处理阿里云短信的回调
     * 参见 https://help.aliyun.com/document_detail/120998.html 文档
     * @param request 请求对象
     * @return 处理结果
     * @throws Throwable 异常
     */
    @PostMapping("/aliyun")
    @PermitAll
    public CommonResult<Boolean> receiveAliyunSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.ALIYUN.getCode(), text);
        return success(true);
    }

    /**
     * 处理腾讯云短信的回调
     * 参见 https://cloud.tencent.com/document/product/382/52077 文档
     * @param request 请求对象
     * @return 处理结果
     * @throws Throwable 异常
     */
    @PostMapping("/tencent")
    @PermitAll
    public CommonResult<Boolean> receiveTencentSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.TENCENT.getCode(), text);
        return success(true);
    }

    /**
     * 处理华为云短信的回调
     * 参见 https://support.huaweicloud.com/api-msgsms/sms_05_0003.html 文档
     * @param requestBody 请求体
     * @return 处理结果
     * @throws Throwable 异常
     */
    @PostMapping("/huawei")
    @PermitAll
    public CommonResult<Boolean> receiveHuaweiSmsStatus(@RequestBody String requestBody) throws Throwable {
        smsSendService.receiveSmsStatus(SmsChannelEnum.HUAWEI.getCode(), requestBody);
        return success(true);
    }

    /**
     * 处理七牛云短信的回调
     * 参见 https://developer.qiniu.com/sms/5910/message-push 文档
     * @param requestBody 请求体
     * @return 处理结果
     * @throws Throwable 异常
     */
    @PostMapping("/qiniu")
    @PermitAll
    public CommonResult<Boolean> receiveQiniuSmsStatus(@RequestBody String requestBody) throws Throwable {
        smsSendService.receiveSmsStatus(SmsChannelEnum.QINIU.getCode(), requestBody);
        return success(true);
    }
}
