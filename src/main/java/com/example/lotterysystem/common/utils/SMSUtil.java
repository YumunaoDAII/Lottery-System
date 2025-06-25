package com.example.lotterysystem.common.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SMSUtil {
    private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    @Value(value = "${sms.secret-id}")
    private String secretId;

    @Value(value = "${sms.secret-key}")
    private String secretKey;

    @Value(value = "${sms.sms-sdk-app-id}")
    private String smsSdkAppId;

    @Value(value = "${sms.sign-name}")
    private String signName;

    @Value(value = "${sms.region}")
    private String region;

    /**
     * 发送短信
     *
     * @param templateCode   模板ID
     * @param phoneNumbers   手机号，多个号码用逗号分隔
     * @param templateParams 模板参数数组
     */
    public void sendMessage(String templateCode, String phoneNumbers, String[] templateParams) {
        try {
            SmsClient client = createClient();

            // 构建请求对象
            SendSmsRequest req = new SendSmsRequest();

            // 设置SDK应用ID
            req.setSmsSdkAppId(smsSdkAppId);

            // 设置签名信息
            req.setSignName(signName);

            // 设置模板ID
            req.setTemplateId(templateCode);

            // 设置模板参数
            req.setTemplateParamSet(templateParams);

            // 设置接收号码，需要添加国家码前缀
            String[] phoneNumberArray = phoneNumbers.split(",");
            String[] formattedPhoneNumbers = new String[phoneNumberArray.length];
            for (int i = 0; i < phoneNumberArray.length; i++) {
                formattedPhoneNumbers[i] = "86" + phoneNumberArray[i]; // 86为中国国家码
            }
            req.setPhoneNumberSet(formattedPhoneNumbers);

            // 发送短信
            SendSmsResponse resp = client.SendSms(req);

            // 处理响应
            SendStatus[] sendStatusSet = resp.getSendStatusSet();
            boolean allSuccess = true;

            for (SendStatus status : sendStatusSet) {
                if (!"Ok".equalsIgnoreCase(status.getCode())) {
                    allSuccess = false;
                    logger.error("向{}发送信息失败，错误码：{}，错误信息：{}",
                            status.getPhoneNumber(),
                            status.getCode(),
                            status.getMessage());
                }
            }

            if (allSuccess) {
                logger.info("向{}发送信息成功，templateCode={}", phoneNumbers, templateCode);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("向{}发送信息失败，templateCode={}", phoneNumbers, templateCode, e);
        } catch (Exception e) {
            logger.error("向{}发送信息失败，templateCode={}", phoneNumbers, templateCode, e);
        }
    }

    /**
     * 创建腾讯云SMS客户端
     */
    private SmsClient createClient() {
        // 实例化一个认证对象，入参需要传入腾讯云账户SecretId，SecretKey
        Credential cred = new Credential(secretId, secretKey);

        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 实例化要请求产品的client对象,clientProfile是可选的
        return new SmsClient(cred, region, clientProfile);
    }
}